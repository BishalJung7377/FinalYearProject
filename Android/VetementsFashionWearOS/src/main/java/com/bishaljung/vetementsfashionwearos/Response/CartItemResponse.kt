package com.bishaljung.vetementsfashionwearos.Response

import com.bishaljung.vetementsfashionwearos.Entity.ItemCartModel


class CartItemResponse (
    val success: Boolean? = null,
    val  message: List<ItemCartModel>? = null,
    val buyerToken : String?=null
)