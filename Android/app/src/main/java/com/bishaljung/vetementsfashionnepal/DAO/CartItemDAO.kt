package com.bishaljung.vetementsfashionnepal.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bishaljung.vetementsfashionnepal.Entity.ItemCartModel

@Dao
interface CartItemDAO {
    @Query("Delete from ItemCartModel")
    suspend fun deleteCartItem()
    @Insert
    suspend fun  insertCartItem(itemCartModel: List<ItemCartModel>)
}