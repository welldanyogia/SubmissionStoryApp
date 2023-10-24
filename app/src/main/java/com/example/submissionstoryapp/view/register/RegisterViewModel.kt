package com.example.submissionstoryapp.view.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionstoryapp.data.api.ApiConfig
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel : ViewModel() {
    private val apiService = ApiConfig

    @OptIn(DelicateCoroutinesApi::class)
    fun registerUser(name: String,email: String, password: String): LiveData<Boolean> {
        val registerStatus = MutableLiveData<Boolean>()

        // Make API call to login user
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val apiConfigService = apiService.getApiService()
                val response = apiConfigService.register(name, email, password)
                Log.d("MESSAGE",response.message)
                Log.d("Error", response.error.toString())
                withContext(Dispatchers.Main) {
                    registerStatus.value = response.error
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    registerStatus.value = true
                }
            }
        }

        return registerStatus
    }
}