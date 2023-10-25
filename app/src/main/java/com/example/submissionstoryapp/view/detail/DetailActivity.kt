package com.example.submissionstoryapp.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.example.submissionstoryapp.databinding.ActivityDetailBinding
import com.example.submissionstoryapp.view.ViewModelFactory
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel> { ViewModelFactory.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val progressBar = binding.progressBar


        val token = intent.getStringExtra("token")
        val storyId = intent.getStringExtra("storyId")
        if (token != null) {
            progressBar.visibility = View.VISIBLE
            if (storyId != null) {
                viewModel.fetchStoryDetail(token, storyId)
            }
        }

        viewModel.story.observe(this) { story ->
            progressBar.visibility = View.GONE
            binding.titleTvDetail.text = story.name
            binding.descTvDetail.text = story.description
            Picasso.get().load(story.photoUrl).into(binding.detailImageView)
        }
    }
}