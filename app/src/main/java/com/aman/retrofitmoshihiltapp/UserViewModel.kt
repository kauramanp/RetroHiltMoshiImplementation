package com.aman.retrofitmoshihiltapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        /*viewModelScope.launch {
            _users.value = repository.getUsers()
        }*/

        Log.e("TAG", "in fetch users")
        viewModelScope.launch {
            val result = handleApiCall { repository.getUsers() }

            when (result) {
                is UiState.Loading -> { /* show loader */ }
                is UiState.Success -> { /* update UI with result.data */ }
                is UiState.Error -> { /* show error message */ }
                else -> { /* no-op */ }
            }
        }
    }
}
