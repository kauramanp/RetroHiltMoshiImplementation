package com.aman.retrofitmoshihiltapp

import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers(): ApiResponse<List<User>>
}