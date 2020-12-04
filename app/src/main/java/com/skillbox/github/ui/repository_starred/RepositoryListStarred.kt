package com.skillbox.github.ui.repository_starred

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.skillbox.github.R
import com.skillbox.github.adapter.RepListAdapter
import com.skillbox.github.ui.repository_detail.StartViewModel
import com.skillbox.github.ui.repository_list.RepositoryListFragmentDirections
import com.skillbox.github.ui.repository_list.RepositoryViewModel
import com.skillbox.github.utils.autoCleared
import kotlinx.android.synthetic.main.fragment_repository_list.*

class RepositoryListStarred: Fragment(R.layout.fragment_repository_list) {

    private val viewModel: StartViewModel by viewModels()

    private var repositoryAdapter: RepListAdapter by autoCleared()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        bindListRepository()
    }

    private fun bindListRepository(){
        viewModel.loadingLiveData.observe(viewLifecycleOwner, ::updateIsLoading)
        viewModel.getStarredRepositories()
        viewModel.starredRepository.observe(viewLifecycleOwner){
                list ->
            repositoryAdapter.items = list
        }
    }

    private fun initList(){
        repositoryAdapter = RepListAdapter{
                position ->
            val gitRepositories = repositoryAdapter.items[position]

            val action = RepositoryListFragmentDirections
                .actionRepositoryListFragmentToDetailRepositoryFragment(
                    gitRepositories.owner.avatar,
                    gitRepositories.id.toInt(),
                    gitRepositories.name,
                    gitRepositories.owner.login
                )
//Copypaste
//            findNavController().navigate(action)
        }
        repositoryList.apply {
            adapter = repositoryAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun updateIsLoading(isLoading: Boolean) {
        repositoryList.isVisible = !isLoading
        progressBarRepList.isVisible = isLoading

    }

}