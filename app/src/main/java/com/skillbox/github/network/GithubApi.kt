package com.skillbox.github.network


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface GithubApi {

    @GET("user")
    suspend fun getUser(): RemoteUser

    @GET("repositories")
    fun getRepositories(): Call<List<GitRepositories>>


    @GET("/user/starred/{owner}/{repo}")
    fun checkStarByUser(
        @Path("owner") ownerName: String,
        @Path("repo") repository: String
    ): Call<ResponseBody>

    //    PUT /user/starred/{owner}/{repo}
    @PUT("/user/starred/{owner}/{repo}")
    suspend fun setStarByUser(
        @Path("owner") ownerName: String,
        @Path("repo") repository: String
    ): Response<ResponseBody>

     @DELETE("/user/starred/{owner}/{repo}")
    suspend fun deleteStarByUser(
        @Path("owner") ownerName: String,
        @Path("repo") repository: String
    ): Response<ResponseBody>


    @GET("/user/starred")
    fun getStarredRepositories(): Call<List<GitRepositories>>

    @PATCH("/user")
    fun setName(
        @Body body: NewName
    ): Call<RemoteUser>

    // GET/user/following
    @GET("/user/following")
    suspend fun getFollowingUsers(): List<Owner>

}