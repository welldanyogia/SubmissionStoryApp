package com.example.submissionstoryapp.data.repo

import com.example.submissionstoryapp.data.api.ApiService
import com.example.submissionstoryapp.data.response.ListStoryItem
import com.example.submissionstoryapp.data.response.Story

class StoryRepository( private val apiService: ApiService) {

    suspend fun getStories(token: String, onSuccess: (List<ListStoryItem>) -> Unit, onError: (String) -> Unit) {
        try {
            val response = apiService.getStories("Bearer $token")
            if (response.error) {
                onError(response.message)
            } else {
                onSuccess(response.listStory)
            }
        } catch (e: Exception) {
            onError(e.message ?: "Unknown error")
        }
    }
    suspend fun getStoryDetail(token: String, storyId: String): Story {
        val response = apiService.getStoryDetail("Bearer $token", storyId)
        if (response.error) {
            throw Exception(response.message)
        }
        return response.story
    }
    companion object {
        @Volatile
        private var instance: StoryRepository? = null

        fun getInstance(apiService: ApiService): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(apiService).also { instance = it }
            }
    }
}