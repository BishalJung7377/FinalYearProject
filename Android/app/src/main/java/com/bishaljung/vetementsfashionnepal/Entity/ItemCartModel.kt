package com.bishaljung.vetementsfashionnepal.Entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
class ItemCartModel(
        @PrimaryKey(autoGenerate = false)
        val _id: String ="",
        val CartItemid: List<RecentItemsModel>? = null,
        val CartItemUser: List<Buyer>? = null,
        ):Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readString()!!,
                parcel.createTypedArrayList(RecentItemsModel),
                parcel.createTypedArrayList(Buyer)
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(_id)
                parcel.writeTypedList(CartItemid)
                parcel.writeTypedList(CartItemUser)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<ItemCartModel> {
                override fun createFromParcel(parcel: Parcel): ItemCartModel {
                        return ItemCartModel(parcel)
                }

                override fun newArray(size: Int): Array<ItemCartModel?> {
                        return arrayOfNulls(size)
                }
        }
}
