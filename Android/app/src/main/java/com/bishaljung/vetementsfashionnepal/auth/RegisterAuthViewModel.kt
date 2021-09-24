package com.bishaljung.vetementsfashionnepal.auth

import androidx.lifecycle.ViewModel
import com.bishaljung.vetementsfashionnepal.Entity.Buyer
import com.bishaljung.vetementsfashionnepal.Repository.BuyerRepository
import com.bishaljung.vetementsfashionnepal.Response.Buyer.BuyerRegisterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RegisterAuthViewModel (
    private val repository: BuyerRepository
) : ViewModel() {

    suspend fun registerBuyer(buyer: Buyer):BuyerRegisterResponse =
        withContext(Dispatchers.IO) {
            repository.registerBuyer(buyer)
        }
}