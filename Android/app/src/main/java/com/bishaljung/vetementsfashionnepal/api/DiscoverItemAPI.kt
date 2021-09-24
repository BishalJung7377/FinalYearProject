package com.bishaljung.vetementsfashionnepal.api

import com.bishaljung.vetementsfashionnepal.Response.DiscoverItem.DiscoverItemResponse
import com.bishaljung.vetementsfashionnepal.Response.DiscoverItem.ViewAllItemResponse
import com.bishaljung.vetementsfashionnepal.Response.FavouriteItem.DeleteFavouriteItemResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface DiscoverItemAPI {
//
//    @GET("product/showall")
//    suspend fun  getAllDiscoverItems(
//        @Header("Authorization") token: String
//    ):Response<DiscoverItemResponse>

    @GET("product/showall")
    suspend fun getAllDiscoverItems(
        @Header("Authorization") token: String
    ): Response<DiscoverItemResponse>

    @GET("product/showall")
    suspend fun allitems(
        @Header("Authorization") token: String
    ): Response<ViewAllItemResponse>

}