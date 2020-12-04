package com.skillbox.github.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GitRepositories(
    val id:Long,
    val name: String,
    val owner: Owner
)

@JsonClass(generateAdapter = true)
data class Owner(
    val login:String,
    @Json(name = "avatar_url")
    val avatar: String
)