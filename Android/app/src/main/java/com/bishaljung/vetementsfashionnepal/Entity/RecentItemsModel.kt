package com.bishaljung.vetementsfashionnepal.Entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity


class RecentItemsModel(
    @PrimaryKey(autoGenerate = false)
    val _id: String = "",
    val ProductName: String?= null,
    val ProductImage: String? = null,
    val ProductPrice: String? = null,
    val ProductType: String? = null,
    val ProductColor: String? = null,
    val ProductSize: String? = null,
    val CreatedBy: String? = null,
    val ProductDescription: String? = null,

    ) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(ProductName)
        parcel.writeString(ProductImage)
        parcel.writeString(ProductPrice)
        parcel.writeString(ProductType)
        parcel.writeString(ProductColor)
        parcel.writeString(ProductSize)
        parcel.writeString(CreatedBy)
        parcel.writeString(ProductDescription)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RecentItemsModel> {
        override fun createFromParcel(parcel: Parcel): RecentItemsModel {
            return RecentItemsModel(parcel)
        }

        override fun newArray(size: Int): Array<RecentItemsModel?> {
            return arrayOfNulls(size)
        }
    }
}