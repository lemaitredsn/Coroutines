package com.skillbox.github.adapter.followers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skillbox.github.R
import com.skillbox.github.network.Owner
import com.skillbox.github.utils.inflate
import kotlinx.android.synthetic.main.item_repository.*


class FollowerAdapter(
    private val followers: List<Owner>
):RecyclerView.Adapter<FollowerAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerAdapter.Holder {
        return Holder(parent.inflate(R.layout.item_followers))
    }

    override fun getItemCount(): Int = followers.size

    override fun onBindViewHolder(holder: FollowerAdapter.Holder, position: Int) {
        val follower = followers[position]
        holder.bind(follower)
    }

    class Holder(view: View): RecyclerView.ViewHolder(view) {
        val avatar: ImageView  = view.findViewById<ImageView>(R.id.followerAvatar)
        val name: TextView = view.findViewById(R.id.followerName)

        fun bind(follower: Owner){
            name.text = follower.login

            Glide.with(itemView)
                .load(follower.avatar)
                .placeholder(R.drawable.ic_account)
                .into(avatar)
        }
    }
}