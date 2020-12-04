    package com.skillbox.github.ui.current_user

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.skillbox.github.R
import com.skillbox.github.adapter.RepListAdapter
import com.skillbox.github.adapter.followers.FollowerAdapter
import com.skillbox.github.network.NewName
import com.skillbox.github.utils.autoCleared
import kotlinx.android.synthetic.main.fragment_auth.*
import kotlinx.android.synthetic.main.fragment_repository_list.*
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.coroutines.*

class CurrentUserFragment : Fragment(R.layout.fragment_user) {

    private val viewModel: UserViewModel by viewModels()

    private var followerAdapter: FollowerAdapter by autoCleared()

    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e("TAG", "error coroutine ", throwable)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        asyncInit()

        changeNameBtn.setOnClickListener {
            viewModel.loadingLiveData.observe(viewLifecycleOwner, ::updateIsLoading)
            val newName = NewName(changeNameEditText.text.toString())
            viewModel.setNewNAmeUser(newName)
            viewModel.newName.observe(viewLifecycleOwner) { user ->
                userNameTextView.text = user.name
                changeNameEditText.setText("")
            }
        }
    }

    private fun asyncInit(){
        val scope = CoroutineScope(Dispatchers.Main + errorHandler)

        scope.launch {
            val userInfo = async {
                bindViewModel()
            }
            val followerInfo = async {
                initFollower()
            }
            followerInfo.await()
            userInfo.await()
        }
    }

    private fun initFollower() {
        viewModel.getFollowers()
        viewModel.followers.observe(viewLifecycleOwner) { list ->
            followersList.apply {
                adapter = FollowerAdapter(list)
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }
    }

    private fun bindViewModel() {
        viewModel.loadingLiveData.observe(viewLifecycleOwner, ::updateIsLoading)
        viewModel.getUser()
        viewModel.user.observe(viewLifecycleOwner) { user ->
            userTextView.text = user.username
            userNameTextView.text = user.name
            Glide.with(avatarImageView)
                .load(user.avatar.toString())
                .placeholder(R.drawable.ic_account)
                .into(avatarImageView)
        }
    }

    private fun updateIsLoading(isLoading: Boolean) {
        avatarImageView.isVisible = !isLoading
        userTextView.isVisible = !isLoading
        userNameTextView.isVisible = !isLoading
        changeNameBtn.isVisible = !isLoading
        changeNameEditText.isVisible = !isLoading
        myFollowing.isVisible = !isLoading
        followersList.isVisible = !isLoading
        progressBarCurrentUser.isVisible = isLoading
    }
}