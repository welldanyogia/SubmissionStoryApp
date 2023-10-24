package com.example.submissionstoryapp.data.repo

import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.submissionstoryapp.data.api.ApiService
import com.example.submissionstoryapp.data.pref.UserModel
import com.example.submissionstoryapp.data.pref.UserPreference
import com.example.submissionstoryapp.data.response.LoginResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Call

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
){
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    suspend fun loginUser(email: String, password: String): Call<LoginResponse> {
        return apiService.login(email, password)
    }
    suspend fun saveSession(userModel: UserModel){
        userPreference.saveSession(userModel)
    }

    fun getSession(): Flow<UserModel>{
        return userPreference.getSession()
    }

    suspend fun logout(){
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }
}