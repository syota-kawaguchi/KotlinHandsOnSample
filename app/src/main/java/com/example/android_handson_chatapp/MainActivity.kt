package com.example.android_handson_chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.android_handson_chatapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

private lateinit var binding: ActivityMainBinding

private val tag = "MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        val view = binding.root

        setContentView(view)

        binding.registerButtonRegister.setOnClickListener {
            val email = binding.emailEdittextRegister.text.toString();
            val password = binding.passwordEdittextRegister.text.toString();

            Log.d(tag, "Email is: ${email}")
            Log.d(tag, "password is: ${password}")

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener{
                    if (it.isCanceled){
                        Log.d(tag, "Canceled")
                    }
                    if (!it.isSuccessful) {
                        Log.d(tag, "Failed to create user ${it.exception}")
                        return@addOnCompleteListener
                    }

                    //else if successful
                    Log.d(tag, "Successfully created user with uid: ${it.result.user?.uid}")
                }
        }

        binding.haveAccountTextRegister.setOnClickListener {
            Log.d(tag, "Try show login Activity")

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}