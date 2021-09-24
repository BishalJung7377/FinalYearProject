package com.bishaljung.vetementsfashionnepal.Repository

import com.bishaljung.vetementsfashionnepal.Entity.Buyer
import com.bishaljung.vetementsfashionnepal.Response.Buyer.*
import com.bishaljung.vetementsfashionnepal.api.BuyerDataAPI
import com.bishaljung.vetementsfashionnepal.api.MyAPIRequest
import com.bishaljung.vetementsfashionnepal.api.ServiceBuilder
import okhttp3.MultipartBody


class BuyerRepository :
    MyAPIRequest() {
    val myApi = ServiceBuilder.buildService(BuyerDataAPI::class.java)


    suspend fun registerBuyer(buyer: Buyer): BuyerRegisterResponse {
        return apiRequest {
            myApi.insertBuyer(buyer)
        }
    }

    suspend fun checkBuyer(BuyerEmail: String, BuyerPassword: String): BuyerLoginResponse {
        return apiRequest {
            myApi.checkUser(BuyerEmail, BuyerPassword)
        }
    }

     suspend fun  getBuyerData(): BuyerDataResponse{
         return apiRequest {
             myApi.getBuyerInfo(ServiceBuilder.token!!)
         }
     }


    suspend fun  updateBuyerData(buyer: Buyer): BuyerUpdateResponse{
        return apiRequest {
            myApi.updateBuyer(ServiceBuilder.token!!,buyer)
        }
    }

    suspend fun  updateBuyerPassword(buyer: Buyer): BuyerPasswordUpdateResponse{
        return  apiRequest {
            myApi.updateBuyerPassword(ServiceBuilder.token!!,buyer)
        }
    }

    suspend fun updateBuyerPhoto( body: MultipartBody.Part)
            : BuyerImageResponse {
        return apiRequest {
            myApi.uploadImage(ServiceBuilder.token!!, body)
        }
    }


    //  suspend fun  saveBuyertoDB(buyer: Buyer) = database.getBuyerDAO().checkBuyer(buyer)
}