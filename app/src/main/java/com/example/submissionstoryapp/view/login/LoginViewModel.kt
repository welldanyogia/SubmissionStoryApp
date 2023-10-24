package com.example.submissionstoryapp.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissionstoryapp.data.repo.UserRepository
import com.example.submissionstoryapp.data.pref.UserModel
import com.example.submissionstoryapp.data.response.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> get() = _loginResponse

//    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
//    fun loginUser(email: String, password: String) {
//        viewModelScope.launch {
//            val response = repository.loginUser(email, password)
//            if (response.isSuccessful) {
//                _loginResponse.value = response.body()
//            } else {
//                // Handle error
//            }
//        }
//    }
    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            val response = repository.saveSession(user)
//            if (response.isSuccessful) {
//                // Session saved successfully
//            } else {
//                // Handle error
//            }
        }
    }
}