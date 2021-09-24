package com.bishaljung.vetementsfashionnepal.api

import com.bishaljung.vetementsfashionnepal.Entity.Buyer
import com.bishaljung.vetementsfashionnepal.Response.Buyer.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface BuyerDataAPI {

    ///Add Client / user// buyer
    @POST("register/buyer")
    suspend fun insertBuyer(
        @Body buyer: Buyer
    ): Response<BuyerRegisterResponse>

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

    @PUT("buyer/update")
    suspend fun updateBuyer(
        @Header("Authorization") token: String,
        @Body buyer: Buyer
    ): Response<BuyerUpdateResponse>

    @PUT ("/buyer/update/password/")
    suspend fun updateBuyerPassword(
        @Header("Authorization") token: String,
        @Body buyer: Buyer
    ): Response<BuyerPasswordUpdateResponse>

    @Multipart

    @PUT("/buyer/image/upload")
    suspend fun  uploadImage(
        @Header("Authorization") token: String,
//        @Path("id") id:String,
        @Part BuyerPhoto: MultipartBody.Part
    ): Response<BuyerImageResponse>

}