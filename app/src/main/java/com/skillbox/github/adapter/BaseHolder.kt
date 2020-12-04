package com.skillbox.github.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skillbox.github.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_repository.*

abstract class BaseHolder(
    view: View,
    onItemClick: (position: Int) -> Unit
): RecyclerView.ViewHolder(view), LayoutContainer{

    init {
        view.setOnClickListener {
            onItemClick(adapterPosition)
        }
    }

    protected fun bindMainInfo(
        login:String,
        name:String,
        avatar:String
    ){
        loginRepTextView.text = login
        nameRepositoryTextView.text = name
        Glide.with(itemView)
            .load(avatar)
            .placeholder(R.drawable.ic_account)
            .into(avatarImageView)
    }
}