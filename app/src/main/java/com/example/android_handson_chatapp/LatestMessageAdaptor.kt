package com.example.android_handson_chatapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android_handson_chatapp.databinding.LatestMessageRowBinding
import com.squareup.picasso.Picasso

class LatestMessageAdaptor(private val items: List<LatestMessageItem>, private val listener: ListListener): RecyclerView.Adapter<LatestMessageViewHolder>() {

    interface ListListener {
        fun onClickItem(tappedView: View, latestMessageItem: LatestMessageItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestMessageViewHolder {
        val itemBinding = LatestMessageRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LatestMessageViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: LatestMessageViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    override fun getItemCount(): Int = items.size
}

class LatestMessageViewHolder(private val itemBinding: LatestMessageRowBinding) : RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(item: LatestMessageItem, listener: LatestMessageAdaptor.ListListener){
        itemBinding.usernameTextviewLatestmessage.text = item.message
        itemBinding.messageTextviewLatestmessage.text = item.user.username
        val userImage = itemBinding.imageImageviewLatestmessage
        Picasso.get().load(item.user.profileImageUrl).into(userImage)
        itemBinding.root.setOnClickListener {
            listener.onClickItem(it, item)
        }
    }
}

class LatestMessageItem(val user: User, val message: String) {
    constructor() : this(User(), "")
}