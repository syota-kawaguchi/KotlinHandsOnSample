package com.example.android_handson_chatapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.example.android_handson_chatapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val tag = "RegisterActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        val view = binding.root

        setContentView(view)

        binding.registerButtonRegister.setOnClickListener {
            performRegister()
        }

        binding.haveAccountTextRegister.setOnClickListener {
            Log.d(tag, "Try show login Activity")

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.selectPhotoButtonRegister.setOnClickListener {
            Log.d(tag, "Try to show photo selector")

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
    }

    var selectPhotoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            Log.d(tag, "Photo was selected")

            selectPhotoUri = data.data

            //APIレベルによってbitmapの取得方法の推奨が違う
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectPhotoUri)
            binding.circleViewRegister.setImageBitmap(bitmap)
            binding.selectPhotoButtonRegister.alpha = 0f
        }
    }

    //method抽出はどんなときにする？
    private fun performRegister() {
        val email = binding.emailEdittextRegister.text.toString();
        val password = binding.passwordEdittextRegister.text.toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter text in email or password", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d(tag, "Email is: ${email}")
        Log.d(tag, "password is: ${password}")

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (!it.isSuccessful) {
                    Toast.makeText(this, "Failed to create user", Toast.LENGTH_SHORT).show()
                    return@addOnCompleteListener
                }

                //else if successful
                Log.d(tag, "Successfully created user with uid: ${it.result.user?.uid}")

                //Firebaseに登録する処理
                uploadImageToFirebaseStorage()
            }
            .addOnFailureListener{
                //emailのformatが違ったら実行
                Log.d(tag, "failed to create user message ${it.message}")
                Toast.makeText(this, "Failed to create user", Toast.LENGTH_SHORT).show()
            }
    }

    private fun uploadImageToFirebaseStorage() {
        if (selectPhotoUri == null){
            Toast.makeText(this, "please choose Image", Toast.LENGTH_SHORT).show()
            return
        }

        //要調査
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectPhotoUri!!)
            .addOnSuccessListener {
                Log.d(tag, "Successfuly uploaded image:${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    Log.d(tag, "File Location :$it")
                }
            }
            .addOnFailureListener {}
    }
}