package com.skillbox.github.ui.repository_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.github.data.ListRepository
import com.skillbox.github.data.UserRepository
import com.skillbox.github.network.GitRepositories
import kotlinx.coroutines.*

class RepositoryViewModel: ViewModel() {

    private val repository = ListRepository()

    private val gitRepositoriesLiveData = MutableLiveData<List<GitRepositories>>()

    val gitRepository: LiveData<List<GitRepositories>>
        get() = gitRepositoriesLiveData


    private val loadingMutableLiveData = MutableLiveData(false)
    val loadingLiveData: LiveData<Boolean>
        get() = loadingMutableLiveData

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    fun getRepositories() {
        scope.launch {
            loadingMutableLiveData.postValue(true)
            try{
                val list = repository.getRepositories()
                gitRepositoriesLiveData.postValue(list)
            }catch (t:Throwable){
                gitRepositoriesLiveData.postValue(emptyList())
            }finally {
                loadingMutableLiveData.postValue(false)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}