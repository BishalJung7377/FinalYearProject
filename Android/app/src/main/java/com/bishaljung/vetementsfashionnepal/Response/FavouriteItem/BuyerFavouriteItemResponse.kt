package com.bishaljung.vetementsfashionnepal.Response.FavouriteItem

import com.bishaljung.vetementsfashionnepal.Entity.Buyer
import com.bishaljung.vetementsfashionnepal.Entity.FavouriteItemModel

class BuyerFavouriteItemResponse (
    val success: Boolean? = null,
    val  message: List<FavouriteItemModel>? = null
)