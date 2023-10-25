package com.example.submissionstoryapp.view.main

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import com.example.submissionstoryapp.data.api.ApiConfig
import com.example.submissionstoryapp.data.repo.StoryRepository
import com.example.submissionstoryapp.data.response.ListStoryItem
import com.example.submissionstoryapp.databinding.ActivityMainBinding
import com.example.submissionstoryapp.view.ViewModelFactory
import com.example.submissionstoryapp.view.addstory.AddStoryActivity
import com.example.submissionstoryapp.view.detail.DetailActivity
import com.example.submissionstoryapp.view.welcome.WelcomeActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), StoryClickListener {
    private lateinit var binding: ActivityMainBinding
    private val apiService = ApiConfig.getApiService()
    private val storyRepository = StoryRepository(apiService)
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var storyAdapter: StoryAdapter
    var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this){user ->
            if (!user.isLogin){
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }
        setupView()
        setupAction()
        fetchStories()
    }
    private fun setupView() {
        storyAdapter = StoryAdapter()
        storyAdapter.setStoryClickListener(this)
        binding.rv.adapter = storyAdapter
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
//        supportActionBar?.hide()
    }
    private fun updateStoryList(storyList: List<ListStoryItem>) {
        storyAdapter.submitList(storyList)
    }

    private fun setupAction() {
        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }
        addStory()
    }
    private fun fetchStories() {
        viewModel.getSession().observe(this) { user ->
            if (user.isLogin) {
//                val intent = Intent(this, DetailActivity::class.java)
                token = user.token
//                intent.putExtra("token",token)

                viewModel.viewModelScope.launch {
                    try {
                        storyRepository.getStories(
                            token!!,
                            onSuccess = { storyList ->
                                updateStoryList(storyList)
                            },
                            onError = {
                            }
                        )
                    } catch (e: Exception) {
                        Log.d("Error", e.message.toString())
                    }
                }
            }
        }
    }
    private fun addStory(){
        binding.floatingActionButton.setOnClickListener{
            val intent = Intent(this, AddStoryActivity::class.java)
            intent.putExtra("token",token)
            startActivity(intent)
        }
    }

    override fun onStoryClick(story: ListStoryItem) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("storyId", story.id.toString())
        intent.putExtra("token",token)
        startActivity(intent)
    }
}