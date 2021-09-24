package com.bishaljung.vetementsfashionnepal.Database.convertors

import androidx.room.TypeConverter
import com.bishaljung.vetementsfashionnepal.Entity.ItemCartModel
import com.bishaljung.vetementsfashionnepal.Entity.RecentItemsModel
import com.google.gson.Gson

class ItemConvertor {
    @TypeConverter
    fun listToJson(value:List<RecentItemsModel>?) = Gson().toJson(value)
    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value,Array<RecentItemsModel>::class.java).toList()
}