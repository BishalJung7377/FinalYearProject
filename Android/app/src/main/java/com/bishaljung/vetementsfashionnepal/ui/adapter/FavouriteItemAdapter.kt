package com.bishaljung.vetementsfashionnepal.ui.adapter

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.RecyclerView
import com.bishaljung.vetementsfashionnepal.R
import com.bishaljung.vetementsfashionnepal.Entity.FavouriteItemModel
import com.bishaljung.vetementsfashionnepal.Repository.BuyerCartItemRepository
import com.bishaljung.vetementsfashionnepal.Repository.BuyerFavouriteItemRepository
import com.bishaljung.vetementsfashionnepal.api.ServiceBuilder
import com.bishaljung.vetementsfashionnepal.ui.Notification.NotificationChannel
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class  FavouriteItemAdapter(
    val favouriteItemList: ArrayList<FavouriteItemModel>,
    val context: Context
) : RecyclerView.Adapter<FavouriteItemAdapter.FavouriteItemViewHolder>() {
    class FavouriteItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgfavouriteitem: ImageView
        val tvitemname: TextView
        val tvfavouritePrice: TextView
        val imgfavouritecart: ImageView
        val imgfavouritedelete: ImageView

        init {
            imgfavouriteitem = view.findViewById(R.id.imgfavouriteitem)
            imgfavouritecart = view.findViewById(R.id.imgfavouritecart)
            imgfavouritedelete = view.findViewById(R.id.imgfavouritedelete)
            tvfavouritePrice = view.findViewById(R.id.tvfavouritePrice)
            tvitemname = view.findViewById(R.id.tvitemname)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_favourite_items, parent, false)
        return FavouriteItemViewHolder(view)
    }


    override fun onBindViewHolder(holder: FavouriteItemViewHolder, position: Int) {
        val favItems = favouriteItemList[position]
        val imagePath = ServiceBuilder.loadImagePath() + favItems.FavouriteItemid!![0].ProductImage
        holder.tvitemname.text = favItems.FavouriteItemid!![0].ProductName
        holder.tvfavouritePrice.text = favItems.FavouriteItemid!![0].ProductPrice
        if (!favItems.FavouriteItemid!![0].ProductImage.equals(null)) {
            Glide.with(context)
                .load(imagePath)
                .fitCenter()
//            .load(favItems.imgfavouriteitem)
                .into(holder.imgfavouriteitem)



            ////////////////----------------- favourite item delete code---------------/////////////
            holder.imgfavouritedelete.setOnClickListener {
                val builder = AlertDialog.Builder(context)
                builder.setCancelable(false)
                builder.setTitle("Delete Favourite Item")
                builder.setMessage("Are you sure to delete this Item?")
                builder.setIcon(R.drawable.ic_close)
                builder.setPositiveButton("Yes",
                    DialogInterface.OnClickListener { _, _ ->
                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                val favouriteItemdeleteRepository = BuyerFavouriteItemRepository()
                                val response = favouriteItemdeleteRepository.deleteBuyerFavouriteItem(favItems._id!!)
                                if (response.success == true) {
                                    withContext(Dispatchers.Main) {

                                        favouriteItemList.remove(favItems)
                                        notifyDataSetChanged()
                                        val notificationManager = NotificationManagerCompat.from(context)
                                        val notificationChannels = NotificationChannel(context)
                                        notificationChannels.createNotificationChannels()

                                        val notification = NotificationCompat.Builder(context,notificationChannels.CHANNEL_2)
                                            .setSmallIcon(R.drawable.notification)
                                            .setContentTitle("Item Deleted")
                                            .setContentText("Item Deleted Successfully from Favourites")
                                            .setColor(Color.BLUE)
                                            .build()
                                        notificationManager.notify(2,notification)
                                    }
                                } else {
                                    withContext(Dispatchers.Main) {
                                        Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show()

                                    }
                                }
                            } catch (ex: IOException) {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                        notifyItemRangeChanged(position, itemCount)
                        favouriteItemList.removeAt(position)
                    })
                builder.setNegativeButton("No",
                    DialogInterface.OnClickListener { dialog, _ -> //if user select "No", just cancel this dialog and continue with app
                        dialog.cancel()
                    })
                val alert: AlertDialog = builder.create()
                alert.setCancelable(false)
                alert.show()
//            Toast.makeText(context,"$position",Toast.LENGTH_SHORT).show()
            }



            ///////////////////////------------codes for adding items from favourite to cart ----------------------//////////////////
            holder.imgfavouritecart.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val repository = BuyerCartItemRepository()
                        val response = repository.insertBuyercartItem(favItems.FavouriteItemid[0]._id)

                        if (response.success == true) {

                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, "Item Added", Toast.LENGTH_SHORT).show()
                                try {
                                    val favouriteItemdeleteRepository = BuyerFavouriteItemRepository()
                                    val response = favouriteItemdeleteRepository.deleteBuyerFavouriteItem(favItems._id!!)
                                    if (response.success == true) {
                                        withContext(Dispatchers.Main) {

                                            favouriteItemList.remove(favItems)
                                            notifyDataSetChanged()
                                        }
                                    } else {
                                        withContext(Dispatchers.Main) {
                                            Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show()

                                        }
                                    }
                                } catch (ex: IOException) {
                                    withContext(Dispatchers.Main) {
                                        Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT).show()
                                    }
                                }
                                val notificationManager = NotificationManagerCompat.from(context)
                                val notificationChannels = NotificationChannel(context)
                                notificationChannels.createNotificationChannels()

                                val notification =
                                    NotificationCompat.Builder(context, notificationChannels.CHANNEL_1)
                                        .setSmallIcon(R.drawable.notification)
                                        .setContentTitle("Item Added")
                                        .setContentText("Item Added Successfully to Cart")
                                        .setColor(Color.BLUE)
                                        .build()
                                notificationManager.notify(2, notification)
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, "Error Adding", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } catch (ex: IOException) {
                        withContext(Dispatchers.Main) {

                        }
                    }
                }

            }
        }
    }
        override fun getItemCount(): Int {
            return favouriteItemList.size
    }
}