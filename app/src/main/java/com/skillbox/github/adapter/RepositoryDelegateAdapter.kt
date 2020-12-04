package com.skillbox.github.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.github.R
import com.skillbox.github.network.GitRepositories

class RepositoryDelegateAdapter(
    private val onItemClick: (position: Int) -> Unit
) :
    AbsListItemAdapterDelegate<GitRepositories, GitRepositories, RepositoryDelegateAdapter.GitRepHolder>() {

    override fun isForViewType(
        item: GitRepositories,
        items: MutableList<GitRepositories>,
        position: Int
    ): Boolean {
        return item is GitRepositories
    }

    override fun onCreateViewHolder(parent: ViewGroup): GitRepHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_repository, parent, false)
        return GitRepHolder(view, onItemClick)
    }

    override fun onBindViewHolder(
        item: GitRepositories,
        holder: GitRepHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class GitRepHolder
        (
        override val containerView: View,
        onItemClick: (position: Int) -> Unit
    ) : BaseHolder(containerView, onItemClick) {
        fun bind(repositories: GitRepositories) {
            bindMainInfo(repositories.owner.login, repositories.name, repositories.owner.avatar)
        }
    }
}