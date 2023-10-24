package com.example.submissionstoryapp.data.di

import android.content.Context
import com.example.submissionstoryapp.data.repo.UserRepository
import com.example.submissionstoryapp.data.api.ApiConfig
import com.example.submissionstoryapp.data.pref.UserPreference
import com.example.submissionstoryapp.data.pref.dataStore
import com.example.submissionstoryapp.data.repo.StoryRepository
import com.example.submissionstoryapp.data.repo.UploadRepository

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(pref, apiService)
    }

    fun provideStoryRepository(): StoryRepository {
        val apiService = ApiConfig.getApiService()
        return StoryRepository.getInstance(apiService)
    }
    fun provideUploadRepository(): UploadRepository {
        val apiService = ApiConfig.getApiService()
        return UploadRepository.getInstance(apiService)
    }
}