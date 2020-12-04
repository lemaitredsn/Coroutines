package com.skillbox.github.network

import com.skillbox.github.data.AuthConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthorizationInterceptor(
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val modifiedRequest: Request
        val url = originalRequest.url.newBuilder()
            .addQueryParameter("scope", "user repo")
            .build()
        if(AuthConfig.accessToken != "")
            //test
//            modifiedRequest = originalRequest.newBuilder().url(url).
//                header("Authorization", "token ${AuthConfig.accessToken}").build()
            //test
//            modifiedRequest = originalRequest.newBuilder().header("Authorization", "token ${AuthConfig.accessToken}").url(url).build()
            //original
            modifiedRequest = originalRequest.newBuilder()
                .addHeader("Authorization", "token ${AuthConfig.accessToken}")
//                .addHeader("scope", "user repo")
                .build()

        else
            modifiedRequest = originalRequest

        return chain.proceed(modifiedRequest)
    }
}