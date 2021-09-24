package com.bishaljung.vetementsfashionnepal.api

import androidx.room.Delete
import com.bishaljung.vetementsfashionnepal.Response.FavouriteItem.BuyerFavouriteItemInsertResponse
import com.bishaljung.vetementsfashionnepal.Response.FavouriteItem.BuyerFavouriteItemResponse
import com.bishaljung.vetementsfashionnepal.Response.FavouriteItem.DeleteFavouriteItemResponse
import retrofit2.Response
import retrofit2.http.*

interface BuyerFavouriteAPI {
    @POST("favouriteitem/insert/{id}")
    suspend fun insertFavoutiteItem(
        @Header("Authorization") token: String,
        @Path("id") id:String
    ): Response<BuyerFavouriteItemInsertResponse>

    @GET("favouriteitem/showall")
    suspend fun getBuyerFavouriteItem(
        @Header("Authorization") token: String,
    ): Response<BuyerFavouriteItemResponse>

    @DELETE("favouriteitem/delete/{id}")
    suspend fun deleteFavouriteItem(
        @Header("Authorization") token:String,
        @Path("id") id:String
    ):Response<DeleteFavouriteItemResponse>
}