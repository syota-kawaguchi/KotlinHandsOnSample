package com.example.android_handson_chatapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android_handson_chatapp.databinding.LatestMessageRowBinding
import com.squareup.picasso.Picasso

class LatestMessageAdaptor(private val items: List<LatestMessageItem>, private val listener: ListListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface ListListener {
        fun onClickItem(tappedView: View, latestMessageItem: LatestMessageItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding = LatestMessageRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LatestMessageViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.message_textview_latestmessage).text = items[position].message
        holder.itemView.findViewById<TextView>(R.id.username_textview_latestmessage).text = items[position].user.username
        val userImage = holder.itemView.findViewById<ImageView>(R.id.image_imageview_latestmessage)
        Picasso.get().load(items[position].user.profileImageUrl).into(userImage)
        holder.itemView.setOnClickListener {
            listener.onClickItem(it, items[position])
        }
    }

    override fun getItemCount(): Int = items.size
}

class LatestMessageViewHolder(itemBinding: LatestMessageRowBinding) : RecyclerView.ViewHolder(itemBinding.root) {
    val username: TextView = itemBinding.usernameTextviewLatestmessage
    val userImage: ImageView = itemBinding.imageImageviewLatestmessage
    val message: TextView = itemBinding.messageTextviewLatestmessage
}

class LatestMessageItem(val user: User, val message: String) {
    constructor() : this(User(), "")
}