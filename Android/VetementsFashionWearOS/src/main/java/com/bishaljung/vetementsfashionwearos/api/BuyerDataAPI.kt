package com.bishaljung.vetementsfashionwearos.api

import com.bishaljung.vetementsfashionwearos.Response.BuyerDataResponse
import com.bishaljung.vetementsfashionwearos.Response.BuyerLoginResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface BuyerDataAPI {

    //Login User
    @FormUrlEncoded
    @POST("buyer/login")
    suspend fun checkUser(
        @Field("BuyerEmail") BuyerEmail :String,
        @Field("BuyerPassword") BuyerPassword : String
    ) : Response<BuyerLoginResponse>


    @GET("buyer/buyerdetail")
    suspend fun getBuyerInfo(
        @Header("Authorization") token: String,
    ):Response<BuyerDataResponse>



}