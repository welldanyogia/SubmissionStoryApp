package com.example.submissionstoryapp.data.api

import com.example.submissionstoryapp.data.response.DetailResponse
import com.example.submissionstoryapp.data.response.FileUploadResponse
import com.example.submissionstoryapp.data.response.ListStoryItem
import com.example.submissionstoryapp.data.response.LoginResponse
import com.example.submissionstoryapp.data.response.RegisterResponse
import com.example.submissionstoryapp.data.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("stories")
    suspend fun getStories(
        @Header("Authorization") token: String,
    ): StoryResponse

    @GET("stories/{id}")
    suspend fun getStoryDetail(
        @Header("Authorization") token: String,
        @Path("id") storyId: String
    ): DetailResponse

    @Multipart
    @POST("/stories")
    fun addStory(
        @Header("Authorization") token: String,
        @Part("description") description: RequestBody,
        @Part photo: MultipartBody.Part,
    ): Call<FileUploadResponse>
}