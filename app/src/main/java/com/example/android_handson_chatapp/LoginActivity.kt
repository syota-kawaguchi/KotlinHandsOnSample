package com.example.android_handson_chatapp

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.android_handson_chatapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         binding = ActivityLoginBinding.inflate(layoutInflater)
         setContentView(binding.root)

        binding.backToRegisterTextLogin.setOnClickListener {
            finish()
        }
    }
}