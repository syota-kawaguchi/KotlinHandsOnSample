package com.example.android_handson_chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_handson_chatapp.databinding.ActivityLatestMessageBinding
import com.example.android_handson_chatapp.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LatestMessageActivity : AppCompatActivity() {

    companion object {
        var currentuser: User? = null
        val USER_KEY = "USER_KEY"
        val TAG = "LatestMessagesActivity"
        var recyclerView: RecyclerView? =null
    }

    private lateinit var binding : ActivityLatestMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLatestMessageBinding.inflate(layoutInflater)

        val view = binding.root

        setContentView(view)

        recyclerView = binding.RecyclerViewLatestMessage

        //ログインされているかの判定
        verifyUserIsLoggedIn()

        //ログイン情報の取得
        fetchCurrentUser()

        //データの取得
        listenForLatestMessages()
    }

    private fun verifyUserIsLoggedIn() {
        val uid = FirebaseAuth.getInstance().uid
        if (uid == null) {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    private fun fetchCurrentUser() {
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("users/$uid")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                currentuser = snapshot.getValue(User::class.java)
                Log.d(TAG, "Current user ${currentuser?.profileImageUrl}")
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun listenForLatestMessages() {
        val ref = FirebaseDatabase.getInstance().getReference("/users/")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                var items = mutableListOf<LatestMessageItem>()

                snapshot.children.forEach {
                    Log.d(TAG, "snapshot children : ${it.toString()}")
                    val user = it.getValue(User::class.java)
                    if (user != null) {
                        items.add(LatestMessageItem(user, "hello world"))
                    }
                }

                recyclerView?.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(context)
                    adapter = LatestMessageAdaptor(
                        items,
                        object : LatestMessageAdaptor.ListListener {
                            override fun onClickItem(tappedView: View, latestMessageItem: LatestMessageItem) {
                                val intent = Intent(tappedView.context, ChatLogActivity::class.java)
                                intent.putExtra(USER_KEY, latestMessageItem.user)
                                startActivity(intent)
                            }
                        }
                    )
                }

            }

            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item?.itemId == R.id.menu_sign_out){
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}