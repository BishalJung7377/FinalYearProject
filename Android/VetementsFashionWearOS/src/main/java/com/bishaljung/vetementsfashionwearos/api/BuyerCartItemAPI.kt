package com.bishaljung.vetementsfashionwearos.api

import com.bishaljung.vetementsfashionwearos.Response.CartItemResponse
import retrofit2.Response
import retrofit2.http.*

interface BuyerCartItemAPI {

    @GET("cartitem/showall")
    suspend fun getBuyerCartItem(
        @Header("Authorization") token: String,
    ): Response<CartItemResponse>

}