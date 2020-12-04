package com.skillbox.github.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.skillbox.github.network.GitRepositories
import com.skillbox.github.network.Networking
import com.skillbox.github.network.RemoteUser
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class StarRepository {

    suspend fun checkStarByUser(
        ownerName: String,
        repository: String
    ): Boolean {
        return suspendCancellableCoroutine { cancellableContinuation ->
            Networking.githubApi.checkStarByUser(ownerName, repository).enqueue(
                object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful) {
                            Log.d(
                                "TAG",
                                "checkStarByUser response code = ${response.code()} bool = true"
                            )
                            cancellableContinuation.resume(true)
                        } else {
                            Log.d(
                                "TAG",
                                "checkStarByUser response code = ${response.code()} bool = false"
                            )
                            cancellableContinuation.resume(false)
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        cancellableContinuation.resumeWithException(t)
                    }
                })
        }
    }

    suspend fun deleteStarByUser(
        ownerName: String,
        repository: String
    ): Boolean {
        val response = Networking.githubApi.deleteStarByUser(ownerName, repository)
        return response.code() != 204
    }


    suspend fun setStarByUser(
        ownerName: String,
        repository: String
    ): Boolean {
        val response = Networking.githubApi.setStarByUser(ownerName, repository)
        return response.code() == 204
    }

    fun getStarredRepositories(
        onComplete: (List<GitRepositories>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        Networking.githubApi.getStarredRepositories().enqueue(
            object : Callback<List<GitRepositories>> {

                override fun onResponse(
                    call: Call<List<GitRepositories>>,
                    response: Response<List<GitRepositories>>
                ) {
                    if (response.isSuccessful) {
                        onComplete(response.body().orEmpty())
                    } else {
                        onError(RuntimeException("incorrect status code"))
                    }
                }

                override fun onFailure(call: Call<List<GitRepositories>>, t: Throwable) {
                    Log.d("TAG", "fail repository $t")
                    onError(t)
                }
            }
        )
    }
}
