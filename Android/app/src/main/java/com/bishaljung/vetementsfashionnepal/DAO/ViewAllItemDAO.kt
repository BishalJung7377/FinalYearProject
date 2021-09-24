package com.bishaljung.vetementsfashionnepal.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bishaljung.vetementsfashionnepal.Entity.RecentItemsModel
import com.bishaljung.vetementsfashionnepal.Entity.ViewAllItemModel

@Dao
interface ViewAllItemDAO {
    @Query("Delete from ViewAllItemModel")
    suspend fun deleteAllItem()
    @Insert
    suspend fun  insertAlItemData(viewAllItemModel: MutableList<ViewAllItemModel>?)
}