package com.bishaljung.vetementsfashionnepal.Entity

import android.os.Parcel
import android.os.Parcelable
import android.security.identity.AccessControlProfileId
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Buyer(
    @PrimaryKey(autoGenerate = false)
    val _id: String ="",
    val BuyerFullName: String? = null,
    val BuyerAge: Int? = null,
    val BuyerEmail: String? = null,
    val BuyerPassword: String? = null,
    val BuyerPhone: String? = null,
    val BuyerGender: String? = null,
    val BuyerPhoto: String? =null,
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(BuyerFullName)
        parcel.writeValue(BuyerAge)
        parcel.writeString(BuyerEmail)
        parcel.writeString(BuyerPassword)
        parcel.writeString(BuyerPhone)
        parcel.writeString(BuyerGender)
        parcel.writeString(BuyerPhoto)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Buyer> {
        override fun createFromParcel(parcel: Parcel): Buyer {
            return Buyer(parcel)
        }

        override fun newArray(size: Int): Array<Buyer?> {
            return arrayOfNulls(size)
        }
    }
}