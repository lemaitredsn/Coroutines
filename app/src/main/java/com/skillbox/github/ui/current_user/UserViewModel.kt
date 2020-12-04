package com.skillbox.github.ui.current_user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.github.data.UserRepository
import com.skillbox.github.network.GitRepositories
import com.skillbox.github.network.NewName
import com.skillbox.github.network.Owner
import com.skillbox.github.network.RemoteUser
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val repository = UserRepository()
    private val loadingMutableLiveData = MutableLiveData(false)

    private val userLiveData = MutableLiveData<RemoteUser>()

    val user: LiveData<RemoteUser>
        get() = userLiveData

    val loadingLiveData: LiveData<Boolean>
        get() = loadingMutableLiveData

    private val newNameLiveData = MutableLiveData<RemoteUser>()

    val newName: LiveData<RemoteUser>
        get() = newNameLiveData

    fun setNewNAmeUser(name: NewName) {
        loadingMutableLiveData.postValue(true)
        repository.setName(
            name,
            onComplete = { user ->
                loadingMutableLiveData.postValue(false)
                newNameLiveData.postValue(user)
            },
            onError = {throwable ->  loadingMutableLiveData.postValue(false)
            newNameLiveData.postValue(RemoteUser("", "", -1, ""))
            }
        )
    }

/*    fun getUser() {
        loadingMutableLiveData.postValue(true)
        repository.getUser(

            onComplete = { users ->
                loadingMutableLiveData.postValue(false)
                userLiveData.postValue(users)
            },
            onError = { throwable ->
                loadingMutableLiveData.postValue(false)
                userLiveData.postValue(RemoteUser("", "", 0, ""))
            }
        )
    }*/

    fun getUser() {
        viewModelScope.launch {
            loadingMutableLiveData.postValue(true)
            try {
                val user = repository.getUser()
                userLiveData.postValue(user)

            }catch (t:Throwable){
                val user = RemoteUser("", "", 0, "")
                userLiveData.postValue(user)
            }finally {
                loadingMutableLiveData.postValue(false)
            }
        }
    }

    private val followersLiveData = MutableLiveData<List<Owner>>()

    val followers: LiveData<List<Owner>>
        get() = followersLiveData

    fun getFollowers(){
        viewModelScope.launch {
            try {
                val followers = repository.getFollowers()
                followersLiveData.postValue(followers)
            }catch (t:Throwable){
                Log.d("TAG", t.message)
            }
        }
    }
}