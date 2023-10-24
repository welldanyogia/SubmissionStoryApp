package com.example.submissionstoryapp.data.repo

import android.util.Log
import com.example.submissionstoryapp.data.api.ApiService
import com.example.submissionstoryapp.data.state.ResultState
import androidx.lifecycle.liveData
import com.example.submissionstoryapp.data.response.FileUploadResponse
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

class UploadRepository private constructor(
    private val apiService: ApiService
){
    fun uploadImage(token: String?, description: String, imageFile: File,) = liveData {
        emit(ResultState.Loading)
        val tokenBody = token
        Log.d("tokenrepo", tokenBody.toString())
        val requestBody = description.toRequestBody("text/plain".toMediaType())
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "photo",
            imageFile.name,
            requestImageFile
        )
        try {
            val successResponse = tokenBody?.let { apiService.addStory(it,requestBody, multipartBody) }
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, FileUploadResponse::class.java)
            Log.e("error", errorResponse.message)
            emit(ResultState.Error(errorResponse.message))
        }

    }
    companion object {
        @Volatile
        private var instance: UploadRepository? = null
        fun getInstance(apiService: ApiService) =
            instance ?: synchronized(this) {
                instance ?: UploadRepository(apiService)
            }.also { instance = it }
    }
}