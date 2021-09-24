package com.bishaljung.vetementsfashionwearos.Entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ItemCartModel(
    @PrimaryKey(autoGenerate = false)
    val _id: String = "",
    val CartItemid: List<RecentItemsModel>? = null,
    val CartItemUser: List<Buyer>? = null,
)