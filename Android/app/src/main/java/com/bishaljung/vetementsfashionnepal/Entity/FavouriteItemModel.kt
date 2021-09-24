package com.bishaljung.vetementsfashionnepal.Entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class FavouriteItemModel(
    @PrimaryKey(autoGenerate = false)
    val _id: String ="",
    val FavouriteItemid: List<RecentItemsModel>? = null,
    val FavouriteItemUser: List<Buyer>? = null,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.createTypedArrayList(RecentItemsModel),
        parcel.createTypedArrayList(Buyer)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeTypedList(FavouriteItemid)
        parcel.writeTypedList(FavouriteItemUser)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FavouriteItemModel> {
        override fun createFromParcel(parcel: Parcel): FavouriteItemModel {
            return FavouriteItemModel(parcel)
        }

        override fun newArray(size: Int): Array<FavouriteItemModel?> {
            return arrayOfNulls(size)
        }
    }
}