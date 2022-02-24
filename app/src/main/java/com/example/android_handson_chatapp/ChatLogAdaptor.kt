package com.example.android_handson_chatapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatLogAdaptor(
    private val list: List<ChatLogItem>,
    private val listener: ListListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface ListListener {
        fun onClickItem(tappedView: View, chatLogItem: ChatLogItem)
    }

    //From Message = 0, To Message = 1
    override fun getItemViewType(position: Int): Int {
        if (list[position].isFrom) {
            return 0
        }
        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layout : Int = if (viewType == 0) R.layout.chat_from_row else R.layout.chat_to_row
        val itemView: View = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ChatLogViewHolder(itemView, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.textview_chat_log_row).text = list[position].message
        holder.itemView.setOnClickListener {
            listener.onClickItem(it, list[position])
        }
    }

    override fun getItemCount(): Int = list.size
}

class ChatLogViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {
    val chatLog : TextView = itemView.findViewById<TextView>(R.id.textview_chat_log_row)
    val profileImage : ImageView = itemView.findViewById<ImageView>(R.id.imageView_chat_log_row)
}

class ChatLogItem(val username: String, val message: String, val profileImageUrl: String, val isFrom: Boolean) {
    constructor() : this("", "", "", true)
}