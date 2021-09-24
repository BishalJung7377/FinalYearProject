package com.bishaljung.vetementsfashionnepal.DAO

import androidx.room.*
import com.bishaljung.vetementsfashionnepal.Entity.Buyer
import com.bishaljung.vetementsfashionnepal.Entity.RecentItemsModel


@Dao
interface BuyerDAO {
    @Insert
    suspend fun RegisterBuyer(buyer: Buyer)

    @Query("Select * From Buyer where  buyerEmail=(:buyeremail) and buyerPassword=(:buyerpassword)")
    suspend fun checkBuyer(buyeremail:String, buyerpassword:String): Buyer


    @Update
    suspend fun updateBuyerInfo(buyer: Buyer)

    @Query("Select * from Buyer")
    suspend fun getBuyerData():List<Buyer>

    @Delete
    suspend fun  deleteUser(buyer: Buyer)

}