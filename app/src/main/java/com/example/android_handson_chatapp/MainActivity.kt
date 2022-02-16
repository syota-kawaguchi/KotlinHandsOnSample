package com.example.android_handson_chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android_handson_chatapp.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        val view = binding.root

        setContentView(view)
    }
}