package com.example.submissionstoryapp.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissionstoryapp.data.repo.StoryRepository
import com.example.submissionstoryapp.data.response.Story
import kotlinx.coroutines.launch

class DetailViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    private val _story = MutableLiveData<Story>()
    val story: LiveData<Story> get() = _story

    fun fetchStoryDetail(token: String, storyId: String) {
        viewModelScope.launch {
            try {
                val storyDetail = storyRepository.getStoryDetail(token, storyId)
                _story.value = storyDetail
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}