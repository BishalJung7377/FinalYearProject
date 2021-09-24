package com.bishaljung.vetementsfashionnepal.api

import com.bishaljung.vetementsfashionnepal.Response.CartItem.CartItemInsertResponse
import com.bishaljung.vetementsfashionnepal.Response.CartItem.CartItemResponse
import com.bishaljung.vetementsfashionnepal.Response.CartItem.DeleteCartItemResponse
import com.bishaljung.vetementsfashionnepal.Response.FavouriteItem.BuyerFavouriteItemInsertResponse
import com.bishaljung.vetementsfashionnepal.Response.FavouriteItem.BuyerFavouriteItemResponse
import com.bishaljung.vetementsfashionnepal.Response.FavouriteItem.DeleteFavouriteItemResponse
import retrofit2.Response
import retrofit2.http.*

interface BuyerCartItemAPI {

    @POST("cartitem/insert/{id}")
    suspend fun insertCartItem(
        @Header("Authorization") token: String,
        @Path("id") id:String
    ): Response<CartItemInsertResponse>

    @GET("cartitem/showall")
    suspend fun getBuyerCartItem(
        @Header("Authorization") token: String,
    ): Response<CartItemResponse>

    @DELETE("cartitem/delete/{id}")
    suspend fun deleteCartItem(
        @Header("Authorization") token:String,
        @Path("id") id:String
    ): Response<DeleteCartItemResponse>
}