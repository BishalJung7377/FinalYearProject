package com.bishaljung.vetementsfashionnepal.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bishaljung.vetementsfashionnepal.DAO.*
import com.bishaljung.vetementsfashionnepal.Database.convertors.CartItemConverter
import com.bishaljung.vetementsfashionnepal.Database.convertors.CartItemUserConvertor
import com.bishaljung.vetementsfashionnepal.Database.convertors.ItemConvertor
import com.bishaljung.vetementsfashionnepal.Database.convertors.UserConvertor
import com.bishaljung.vetementsfashionnepal.Entity.*


@Database(
    entities = [(Buyer::class), (RecentItemsModel::class), (FavouriteItemModel::class), (ItemCartModel::class), (ViewAllItemModel::class)],
    version = 1
)

@TypeConverters(
    ItemConvertor::class,
    UserConvertor::class
)


abstract class BuyerDb : RoomDatabase() {
    abstract fun getBuyerDAO(): BuyerDAO
    abstract fun getItemListDAO(): ItemDAO
    abstract fun getFavouriteItemDAO(): FavouriteItemDAO
    abstract fun getCartItemDAO(): CartItemDAO
    abstract fun getAllItemDAO(): ViewAllItemDAO

    companion object {
        @Volatile

        private var instance: BuyerDb? = null
        fun getInstance(context: Context): BuyerDb {
            if (instance == null) {
                synchronized(BuyerDb::class) {
                    instance = buildDatabase(context)
                }
            }
            return instance!!
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                BuyerDb::class.java,
                "BuyerDb"
            ).build()

    }
}