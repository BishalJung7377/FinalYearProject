package com.bishaljung.vetementsfashionnepal.Entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity

class NewItemModel (
    @PrimaryKey(autoGenerate = false)
    val _id: String = "",
    val newItemName: String?= null,
    val newitemImage: String? = null,
    val newitemPrice: String? = null,
    val newitemType: String? = null,
    val newitemColor: String? = null,
    val newitemDescription: String? = null,
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
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
        parcel.writeString(newItemName)
        parcel.writeString(newitemImage)
        parcel.writeString(newitemPrice)
        parcel.writeString(newitemType)
        parcel.writeString(newitemColor)
        parcel.writeString(newitemDescription)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewItemModel> {
        override fun createFromParcel(parcel: Parcel): NewItemModel {
            return NewItemModel(parcel)
        }

        override fun newArray(size: Int): Array<NewItemModel?> {
            return arrayOfNulls(size)
        }
    }
}