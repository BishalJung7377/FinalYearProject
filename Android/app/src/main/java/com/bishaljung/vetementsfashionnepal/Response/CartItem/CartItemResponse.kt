package com.bishaljung.vetementsfashionnepal.Response.CartItem

import com.bishaljung.vetementsfashionnepal.Entity.Buyer
import com.bishaljung.vetementsfashionnepal.Entity.FavouriteItemModel
import com.bishaljung.vetementsfashionnepal.Entity.ItemCartModel

class CartItemResponse (
    val success: Boolean? = null,
    val  message: List<ItemCartModel>? = null,
    val buyerToken : String?=null
)