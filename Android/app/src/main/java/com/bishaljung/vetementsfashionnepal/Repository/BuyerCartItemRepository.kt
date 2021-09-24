package com.bishaljung.vetementsfashionnepal.Repository

import com.bishaljung.vetementsfashionnepal.Response.CartItem.CartItemInsertResponse
import com.bishaljung.vetementsfashionnepal.Response.CartItem.CartItemResponse
import com.bishaljung.vetementsfashionnepal.Response.CartItem.DeleteCartItemResponse
import com.bishaljung.vetementsfashionnepal.Response.FavouriteItem.BuyerFavouriteItemInsertResponse
import com.bishaljung.vetementsfashionnepal.api.*

class BuyerCartItemRepository:
    MyAPIRequest(){
    val BuyerCartItemAPI = ServiceBuilder.buildService(BuyerCartItemAPI::class.java)

    
    suspend fun  getBuyercartItem(): CartItemResponse{
        return apiRequest {
            BuyerCartItemAPI.getBuyerCartItem(ServiceBuilder.token!!)
        }
    }
    suspend fun insertBuyercartItem(id: String): CartItemInsertResponse {
        return  apiRequest {
            BuyerCartItemAPI.insertCartItem(ServiceBuilder.token!!,id)
        }
    }
    suspend fun deleteBuyercartItem(id: String): DeleteCartItemResponse {
        return apiRequest {
            BuyerCartItemAPI.deleteCartItem(ServiceBuilder.token!!,id)
        }
    }
}