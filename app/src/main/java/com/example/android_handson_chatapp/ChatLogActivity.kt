package com.example.android_handson_chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_handson_chatapp.databinding.ActivityChatLogBinding

class ChatLogActivity : AppCompatActivity() {

    companion object {
        var recyclerView : RecyclerView? =null
    }

    private lateinit var binding : ActivityChatLogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatLogBinding.inflate(layoutInflater)

        val view = binding.root

        setContentView(view)

        supportActionBar?.title = "Chat Log Activity"

        recyclerView = binding.recyclerviewChatLog

        val chatLogs = mutableListOf<ChatLogItem>()
        chatLogs.add(ChatLogItem("username", "this is sample message", "", true))
        chatLogs.add(ChatLogItem("username", "this is sample message", "", false))
        chatLogs.add(ChatLogItem("username", "this is sample message", "", true))
        chatLogs.add(ChatLogItem("username", "this is sample message", "", false))

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