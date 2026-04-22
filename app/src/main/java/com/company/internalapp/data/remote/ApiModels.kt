package com.company.internalapp.data.remote

data class LoginRequest(val mobile: String, val password: String)
data class LoginResponse(val accessToken: String, val refreshToken: String, val user: ApiUser)
data class RefreshRequest(val refreshToken: String)
data class RefreshResponse(val accessToken: String)

data class ApiUser(val id: String, val name: String, val role: String)

data class ApiLead(
    val id: String,
    val name: String,
    val phone: String,
    val status: String,
    val assignedTo: String,
    val updatedAt: String
)

data class PaginatedResponse<T>(val items: List<T>, val page: Int, val pageSize: Int, val total: Long)
