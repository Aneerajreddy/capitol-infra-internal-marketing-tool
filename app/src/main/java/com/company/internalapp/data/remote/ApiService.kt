package com.company.internalapp.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body body: LoginRequest): LoginResponse

    @POST("auth/refresh")
    suspend fun refresh(@Body body: RefreshRequest): RefreshResponse

    @GET("leads")
    suspend fun getLeads(@Query("page") page: Int, @Query("pageSize") pageSize: Int): PaginatedResponse<ApiLead>
}
