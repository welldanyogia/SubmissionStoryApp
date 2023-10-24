package com.example.submissionstoryapp.view.addstory

import androidx.lifecycle.ViewModel
import com.example.submissionstoryapp.data.repo.UploadRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class AddStoryViewModel(private val repository: UploadRepository) : ViewModel() {
    fun uploadImage(token: String, description: String, file: File) = repository.uploadImage(token, description,file)
//    val uploadResponse: MutableLiveData<String> = MutableLiveData()
//    fun uploadImage(
//        token: String,
//        description: RequestBody,
//        imageUri: MultipartBody.Part,
//    ) {
//        apiService.addStory(token, description, imageUri)
//            .enqueue(object : Callback<FileUploadResponse> {
//                override fun onResponse(
//                    call: Call<FileUploadResponse>,
//                    response: Response<FileUploadResponse>
//                ) {
//                    uploadResponse.value = response.body()?.message
//                }
//
//                override fun onFailure(call: Call<FileUploadResponse>, t: Throwable) {
//                    // Handle error response
//                }
//            })
//    }

//    fun uriToFile(imageUri: Uri, context: Context): File {
//        val myFile = createCustomTempFile(context)
//        val inputStream = context.contentResolver.openInputStream(imageUri) as InputStream
//        val outputStream = FileOutputStream(myFile)
//        val buffer = ByteArray(1024)
//        var length: Int
//        while (inputStream.read(buffer).also { length = it } > 0) outputStream.write(buffer, 0, length)
//        outputStream.close()
//        inputStream.close()
//        return myFile
//    }
}