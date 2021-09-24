package com.bishaljung.vetementsfashionwearos.Repository

import com.bishaljung.vetementsfashionwearos.Response.BuyerDataResponse
import com.bishaljung.vetementsfashionwearos.Response.BuyerLoginResponse
import com.bishaljung.vetementsfashionwearos.api.BuyerDataAPI
import com.bishaljung.vetementsfashionwearos.api.MyAPIRequest
import com.bishaljung.vetementsfashionwearos.api.ServiceBuilder


class BuyerRepository :
    MyAPIRequest() {
    val myApi = ServiceBuilder.buildService(BuyerDataAPI::class.java)



    suspend fun checkBuyer(BuyerEmail: String, BuyerPassword: String): BuyerLoginResponse {
        return apiRequest {
            myApi.checkUser(BuyerEmail, BuyerPassword)
        }
    }

     suspend fun  getBuyerData(): BuyerDataResponse {
         return apiRequest {
             myApi.getBuyerInfo(ServiceBuilder.token!!)
         }
     }


}