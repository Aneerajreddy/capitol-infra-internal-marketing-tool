package com.company.internalapp.data.remote

import com.company.internalapp.core.TokenManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenManager: TokenManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        tokenManager.accessToken()?.let { requestBuilder.addHeader("Authorization", "Bearer $it") }
        return chain.proceed(requestBuilder.build())
    }
}
