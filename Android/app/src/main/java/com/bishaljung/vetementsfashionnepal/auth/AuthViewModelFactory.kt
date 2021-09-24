package com.bishaljung.vetementsfashionnepal.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bishaljung.vetementsfashionnepal.Repository.BuyerRepository

@Suppress("UNCHECKED_CAST")
class AuthViewModelFactory(
    private val repository: BuyerRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(repository) as T
    }
}