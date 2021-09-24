package com.bishaljung.vetementsfashionnepal.Repository

import com.bishaljung.vetementsfashionnepal.Response.FavouriteItem.BuyerFavouriteItemInsertResponse
import com.bishaljung.vetementsfashionnepal.Response.FavouriteItem.BuyerFavouriteItemResponse
import com.bishaljung.vetementsfashionnepal.Response.FavouriteItem.DeleteFavouriteItemResponse
import com.bishaljung.vetementsfashionnepal.api.*

class BuyerFavouriteItemRepository:
    MyAPIRequest(){
    private  val BuyerFavouriteAPI = ServiceBuilder.buildService(BuyerFavouriteAPI::class.java)

    suspend fun getBuyerFavouriteItem(): BuyerFavouriteItemResponse {
        return apiRequest {
            BuyerFavouriteAPI.getBuyerFavouriteItem(ServiceBuilder.token!!)
        }
    }

    suspend fun insertBuyerFavouriteItem(id: String): BuyerFavouriteItemInsertResponse{
        return  apiRequest {
            BuyerFavouriteAPI.insertFavoutiteItem(ServiceBuilder.token!!,id)
        }
    }
    suspend fun deleteBuyerFavouriteItem(id: String): DeleteFavouriteItemResponse{
        return apiRequest {
            BuyerFavouriteAPI.deleteFavouriteItem(ServiceBuilder.token!!,id)
        }
    }
    }

