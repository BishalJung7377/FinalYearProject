package com.bishaljung.vetementsfashionnepal.Response.DiscoverItem

import com.bishaljung.vetementsfashionnepal.Entity.RecentItemsModel
import com.bishaljung.vetementsfashionnepal.Entity.ViewAllItemModel

data class DiscoverItemResponse (
    val success: Boolean? = null,
    val message: MutableList<RecentItemsModel>? = null
        )