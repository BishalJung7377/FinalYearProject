package com.bishaljung.vetementsfashionwearos.Repository


import com.bishaljung.vetementsfashionwearos.Response.CartItemResponse
import com.bishaljung.vetementsfashionwearos.api.MyAPIRequest
import com.bishaljung.vetementsfashionwearos.api.ServiceBuilder

class BuyerCartItemRepository:
    MyAPIRequest(){
    val BuyerCartItemAPI = ServiceBuilder.buildService(com.bishaljung.vetementsfashionwearos.api.BuyerCartItemAPI::class.java)


    suspend fun  getBuyercartItem(): CartItemResponse{
        return apiRequest {
            BuyerCartItemAPI.getBuyerCartItem(ServiceBuilder.token!!)
        }
    }

}