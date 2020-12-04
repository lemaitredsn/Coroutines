package com.skillbox.github.data

import android.util.Log
import com.skillbox.github.network.GitRepositories
import com.skillbox.github.network.Networking
import com.skillbox.github.network.RemoteUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ListRepository {
    suspend fun getRepositories(): List<GitRepositories> {
        return withContext(Dispatchers.IO){
            var response = Networking.githubApi.getRepositories().execute()
            if(response.isSuccessful){
                response.body().orEmpty()
            }else{
                emptyList()
            }
        }
    }
    //    suspend fun getRepositories(): List<GitRepositories> {
//        return suspendCoroutine { continuation ->
//            Networking.githubApi.getRepositories().enqueue(
//                object : Callback<List<GitRepositories>> {
//                    override fun onResponse(
//                        call: Call<List<GitRepositories>>,
//                        response: Response<List<GitRepositories>>
//                    ) {
//
//                        if (response.isSuccessful) {
//                            continuation.resume(response.body().orEmpty())
//                        } else {
//                            continuation.resumeWithException(RuntimeException("fail status code"))
//                        }
//                    }
//
//                    override fun onFailure(call: Call<List<GitRepositories>>, t: Throwable) {
//                        continuation.resumeWithException(t)
//                    }
//                })
//        }
//    }


}