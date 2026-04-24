package com.company.internalapp.data.repository

import com.company.internalapp.core.TokenManager
import com.company.internalapp.data.remote.ApiService
import com.company.internalapp.data.remote.LoginRequest
import com.company.internalapp.data.remote.RefreshRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val api: ApiService,
    private val tokenManager: TokenManager
) {
    suspend fun login(mobile: String, password: String): Result<Unit> = runCatching {
        val response = api.login(LoginRequest(mobile, password))
        tokenManager.saveTokens(response.accessToken, response.refreshToken)
    }

    suspend fun refreshToken(): Result<Unit> = runCatching {
        val refresh = tokenManager.refreshToken() ?: error("No refresh token")
        val result = api.refresh(RefreshRequest(refresh))
        tokenManager.saveTokens(result.accessToken, refresh)
    }

    fun isLoggedIn(): Boolean = tokenManager.accessToken() != null

    fun logout() = tokenManager.clear()
}
