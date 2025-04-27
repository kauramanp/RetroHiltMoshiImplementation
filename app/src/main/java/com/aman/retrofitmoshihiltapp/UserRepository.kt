package com.aman.retrofitmoshihiltapp

import javax.inject.Inject
/*

interface UserRepository {
    suspend fun getUsers(): List<User>
}

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : UserRepository {
    override suspend fun getUsers(): List<User> {
        return apiService.getUsers()
    }
}*/

class UserRepository @Inject constructor(
    private val apiService: ApiService // or whatever you injected inside
) {
    suspend fun getUsers(): ApiResponse<List<User>> {
        return apiService.getUsers()
    }

   /* suspend fun getUsers() = flow {
        emit(UiState.Loading)
        emit(
            handleApiCall {
                apiService.getUsers()
            }
        )
    }.flowOn(Dispatchers.IO)*/
}
