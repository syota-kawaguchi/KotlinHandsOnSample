package com.example.android_handson_chatapp

import android.content.Context
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.parcel.Parcelize

class LatestMessageAdaptor(context: Context, var UserList: List<User>): ArrayAdapter<User>(context, 0, UserList) {
    private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val user: User = UserList[position]

        var view = convertView
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.latest_message_row, parent, false)
        }

        val userImage = view?.findViewById<ImageView>(R.id.image_imageview_latestmessage)
        //picasso

        val username = view?.findViewById<TextView>(R.id.username_textview_latestmessage)
        username?.text = user.username

        val message = view?.findViewById<TextView>(R.id.message_textview_latestmessage)
        message?.text = ""

        return view!!
    }
}

@Parcelize
class User(val uid: String, val username: String, val profileImageUrl: String): Parcelable {
    constructor() : this("", "", "")
}