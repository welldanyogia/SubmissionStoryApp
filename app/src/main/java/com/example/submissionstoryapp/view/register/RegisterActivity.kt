package com.example.submissionstoryapp.view.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.submissionstoryapp.databinding.ActivityRegisterBinding
import com.example.submissionstoryapp.view.login.LoginActivity
import com.example.submissionstoryapp.view.main.MainActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().length < 8) {
                    showPasswordError()
                } else {
                    binding.passwordEditText.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
//                TODO("Not yet implemented")
            }

        })

        binding.buttonRegister.setOnClickListener {
            val name = binding.namaEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            val registrationStatus = viewModel.registerUser(name, email, password)
            registrationStatus.observe(this) { isSuccessful ->
                if (isSuccessful) {
                    // Registration success
                    Log.d("Sukses", "SUkses")
                    AlertDialog.Builder(this).apply {
                        setTitle("Yeah!")
                        setMessage("Anda berhasil daftar. Sudah tidak sabar untuk belajar ya?")
                        setPositiveButton("Lanjut") { _, _ ->
                            val intent = Intent(context, LoginActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        }
                        create()
                        show()
                    }
                } else {
                    // Registration failed
                    Log.d("Gagal", "Gagal")
                }
            }
        }
    }
    private fun showPasswordError() {
        binding.passwordEditText.error = "Password must be at least 8 characters long"
    }

}