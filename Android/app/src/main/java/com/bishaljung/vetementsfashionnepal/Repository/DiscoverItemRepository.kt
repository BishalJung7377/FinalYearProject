package com.bishaljung.vetementsfashionnepal.Repository

import android.content.Context
import com.bishaljung.vetementsfashionnepal.Database.BuyerDb
import com.bishaljung.vetementsfashionnepal.Response.DiscoverItem.DiscoverItemResponse
import com.bishaljung.vetementsfashionnepal.Response.DiscoverItem.ViewAllItemResponse
import com.bishaljung.vetementsfashionnepal.api.DiscoverItemAPI
import com.bishaljung.vetementsfashionnepal.api.MyAPIRequest
import com.bishaljung.vetementsfashionnepal.api.ServiceBuilder

class DiscoverItemRepository : MyAPIRequest() {

    private val DiscoverItemAPI = ServiceBuilder.buildService(DiscoverItemAPI::class.java)


//    suspend fun insertDatatoDatabase(context: Context){
//       val data= getAllRecentItems()
//        BuyerDb.getInstance(context).getBuyerDAO().insertItemData(data.message)
//    }

    suspend fun  getAllRecentItems(): DiscoverItemResponse{
        return  apiRequest {
            DiscoverItemAPI.getAllDiscoverItems(ServiceBuilder.token!!)
        }
    }
    suspend fun  ViewAllItems(): ViewAllItemResponse {
        return apiRequest {
            DiscoverItemAPI.allitems(ServiceBuilder.token!!)
        }
    }


}