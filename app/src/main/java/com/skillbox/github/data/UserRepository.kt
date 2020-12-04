package com.skillbox.github.data

import android.util.Log
import com.skillbox.github.network.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Path
import java.lang.RuntimeException

class UserRepository {

    fun setName(
        name: NewName,
        onComplete: (RemoteUser) -> Unit,
        onError: (Throwable) -> Unit
    ){
        Networking.githubApi.setName(name).enqueue(
            object : Callback<RemoteUser>{
                override fun onResponse(call: Call<RemoteUser>, response: Response<RemoteUser>) {
                    if(response.isSuccessful){
                        onComplete(response.body()!!)
                    }else{
                        onError(RuntimeException("setName incorrect"))
                    }
                }

                override fun onFailure(call: Call<RemoteUser>, t: Throwable) {
                    onError(t)
                }
            }
        )
    }

    suspend fun getUser(): RemoteUser {
        return Networking.githubApi.getUser()
    }

    suspend fun getFollowers(): List<Owner>{
        return Networking.githubApi.getFollowingUsers()
    }

}