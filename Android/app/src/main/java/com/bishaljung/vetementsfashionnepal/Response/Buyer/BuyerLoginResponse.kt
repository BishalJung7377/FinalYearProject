package com.bishaljung.vetementsfashionnepal.Response.Buyer

import com.bishaljung.vetementsfashionnepal.Entity.Buyer

data class BuyerLoginResponse (
    val success : Boolean? =null,
    val buyerToken : String?=null
    )