package com.bishaljung.vetementsfashionnepal.auth

import androidx.lifecycle.ViewModel
import com.bishaljung.vetementsfashionnepal.Repository.BuyerRepository
import com.bishaljung.vetementsfashionnepal.Response.Buyer.BuyerLoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthViewModel(
    private val repository: BuyerRepository
) : ViewModel() {


    suspend fun checkBuyer(BuyerEmail: String, BuyerPassword: String): BuyerLoginResponse =
        withContext(Dispatchers.IO) {
            repository.checkBuyer(BuyerEmail, BuyerPassword)

        }


}