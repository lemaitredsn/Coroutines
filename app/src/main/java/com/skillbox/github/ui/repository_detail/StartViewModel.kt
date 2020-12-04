package com.skillbox.github.ui.repository_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.github.data.StarRepository
import com.skillbox.github.network.GitRepositories
import com.skillbox.github.network.RemoteUser
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class StartViewModel : ViewModel() {

    private val repository = StarRepository()

    private val checkStarLiveData = MutableLiveData(false)
    val checkStarLD: LiveData<Boolean>
        get() = checkStarLiveData

    private val loadingMutableLiveData = MutableLiveData(false)
    val loadingLiveData: LiveData<Boolean>
        get() = loadingMutableLiveData

    fun checkStar(owner: String, repositoryName: String) {
        viewModelScope.launch {
            try {
                val check = repository.checkStarByUser(owner, repositoryName)
                checkStarLiveData.postValue(check)
            } catch (t: Throwable) {
                Log.d("TAG", "${t.message}")
            }
        }
    }

    fun deleteStar(owner: String, repositoryName: String) {
        viewModelScope.launch {
            try{
                val deleteRes = repository.deleteStarByUser(owner, repositoryName)
                checkStarLiveData.postValue(deleteRes)
            }catch (t: Throwable){
                Log.d("TAG", "${t.message}")
            }
        }
    }

    fun setStar(owner: String, repositoryName: String) {
        viewModelScope.launch {
            try {
                val setRes = repository.setStarByUser(owner, repositoryName)
                checkStarLiveData.postValue(setRes)
            }catch (t:Throwable){
                Log.d("Tag", "onError ${t.message} in checkStar")
            }
        }
    }

    private val starredRepositoriesLiveData = MutableLiveData<List<GitRepositories>>()
    val starredRepository: LiveData<List<GitRepositories>>
        get() = starredRepositoriesLiveData

    fun getStarredRepositories() {
        loadingMutableLiveData.postValue(true)
        repository.getStarredRepositories(
            onComplete = { list ->
                loadingMutableLiveData.postValue(false)
                Log.d("TAG", "succes")
                starredRepositoriesLiveData.postValue(list)
            },
            onError = { throwable ->
                loadingMutableLiveData.postValue(false)
                Log.d("TAG", "fail")
                starredRepositoriesLiveData.postValue(emptyList())
            }
        )
    }
}