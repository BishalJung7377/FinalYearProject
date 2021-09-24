package com.bishaljung.vetementsfashionnepal.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bishaljung.vetementsfashionnepal.Entity.FavouriteItemModel


@Dao
interface FavouriteItemDAO {
    @Query("Delete from favouriteitemmodel")
    suspend fun deleteFavouriteItem()
    @Insert
    suspend fun  insertFavouriteItem(FavouriteItemModel: kotlin.collections.List<com.bishaljung.vetementsfashionnepal.Entity.FavouriteItemModel>)
}