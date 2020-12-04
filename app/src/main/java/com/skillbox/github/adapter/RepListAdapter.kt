package com.skillbox.github.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.github.network.GitRepositories
import java.text.FieldPosition

class RepListAdapter(
    private val onItemClick: (position: Int) -> Unit
):
AsyncListDifferDelegationAdapter<GitRepositories>(
    RepDiffUtilCallback()
){

    init {
        delegatesManager.addDelegate(RepositoryDelegateAdapter(onItemClick))
    }

    class RepDiffUtilCallback:DiffUtil.ItemCallback<GitRepositories>(){
        override fun areItemsTheSame(oldItem: GitRepositories, newItem: GitRepositories): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GitRepositories,
            newItem: GitRepositories
        ): Boolean {
            return oldItem == newItem
        }

    }
}