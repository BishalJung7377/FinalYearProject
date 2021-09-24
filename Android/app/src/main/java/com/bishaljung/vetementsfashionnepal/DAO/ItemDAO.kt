package com.bishaljung.vetementsfashionnepal.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bishaljung.vetementsfashionnepal.Entity.RecentItemsModel
@Dao
interface ItemDAO {
    @Query("Delete from recentitemsmodel")
    suspend fun deleteItem()
    @Insert
    suspend fun  insertItemData(recentItemsModel: MutableList<RecentItemsModel>?)
}