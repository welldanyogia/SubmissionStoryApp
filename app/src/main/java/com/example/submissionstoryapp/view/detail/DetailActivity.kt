package com.example.submissionstoryapp.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.submissionstoryapp.R
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

        val token = intent.getStringExtra("token")
        val storyId = intent.getStringExtra("storyId")
        Log.d("token",token.toString())
        Log.d("storyId",storyId.toString())
        if (token != null) {
            if (storyId != null) {
                viewModel.fetchStoryDetail(token, storyId)
            }
        }

        viewModel.story.observe(this) { story ->
            binding.titleTvDetail.text = story.name
            binding.descTvDetail.text = story.description
            Picasso.get().load(story.photoUrl).into(binding.detailImageView)
            Log.d("nama",story.photoUrl)
        }
    }
}