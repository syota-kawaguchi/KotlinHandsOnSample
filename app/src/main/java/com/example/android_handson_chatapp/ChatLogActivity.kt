package com.example.android_handson_chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_handson_chatapp.databinding.ActivityChatLogBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class ChatLogActivity : AppCompatActivity() {

    companion object {
        var recyclerView : RecyclerView? =null
        val TAG = "ChatLogActivity"
    }

    val chatLogs = mutableListOf<ChatLogItem>()

    private lateinit var binding : ActivityChatLogBinding

    var toUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatLogBinding.inflate(layoutInflater)

        val view = binding.root

        setContentView(view)

        toUser = intent.getParcelableExtra<User>(LatestMessageActivity.USER_KEY)
        supportActionBar?.title =toUser?.username

        recyclerView = binding.recyclerviewChatLog

        listenfForMessage()

        binding.sendButtonChatLog.setOnClickListener {
            Log.d(TAG, "Attempt to send message...")
            performSendMessage()
        }
    }

    private fun listenfForMessage() {
        val ref = FirebaseDatabase.getInstance().getReference("/messages")

        ref.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(ChatMessage::class.java)
                val myId = FirebaseAuth.getInstance().uid
                val user = if (chatMessage?.fromId == myId) LatestMessageActivity.currentuser else toUser

                if (user == null) return

                if (chatMessage != null) {
                    Log.d(TAG, chatMessage.text)
                    chatLogs.add(ChatLogItem(user.username, chatMessage.text, user.profileImageUrl,  chatMessage.fromId == myId))
                }
                refreshAdaptor()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
//                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
//                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun performSendMessage() {

        val user = intent.getParcelableExtra<User>(LatestMessageActivity.USER_KEY)

        val text = binding.edittextChatLog.text.toString()
        val fromId = FirebaseAuth.getInstance().uid
        val toId = user?.uid

        if (fromId == null || toId == null) return

        val ref = FirebaseDatabase.getInstance().getReference("/messages").push()

        val chatMessage = ChatMessage(ref.key!!, text, fromId, toId, System.currentTimeMillis() / 1000)
        ref.setValue(chatMessage)
            .addOnSuccessListener {
                Log.d(TAG, "Saved our chat message: ${ref.key}")
            }
    }

    private fun SetupDummyData() {
        chatLogs.add(ChatLogItem("username", "this is sample message", "", true))
        chatLogs.add(ChatLogItem("username", "this is sample message", "", false))
        chatLogs.add(ChatLogItem("username", "this is sample message", "", true))
        chatLogs.add(ChatLogItem("username", "this is sample message", "", false))

        refreshAdaptor()
    }

    private fun refreshAdaptor() {
        val adaptor = ChatLogAdaptor(
            chatLogs,
            object : ChatLogAdaptor.ListListener {
                override fun onClickItem(tappedView: View, chatLogItem: ChatLogItem) {
                }
            }
        )

        recyclerView?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = adaptor
        }
    }
}