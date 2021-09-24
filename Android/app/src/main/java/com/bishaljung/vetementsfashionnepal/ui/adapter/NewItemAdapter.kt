package com.bishaljung.vetementsfashionnepal.ui.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.RecyclerView
import com.bishaljung.vetementsfashionnepal.ui.ItemDisplayActivity
import com.bishaljung.vetementsfashionnepal.R
import com.bishaljung.vetementsfashionnepal.Repository.BuyerFavouriteItemRepository
import com.bishaljung.vetementsfashionnepal.ui.Notification.NotificationChannel
import com.bishaljung.vetementsfashionnepal.Entity.NewItemModel
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class NewItemAdapter (
    val newtItemList: ArrayList<NewItemModel>,
    val context: Context
): RecyclerView.Adapter<NewItemAdapter.NewItemsViewHolder>(){
    class NewItemsViewHolder(view: View): RecyclerView.ViewHolder(view){
        val newitemImg: ImageView
        val tvnewitemprice: TextView
//        val tvnewitemTypeName: TextView
        val tvnewitemname: TextView
        val imgLike: ImageView


        init {
            newitemImg = view.findViewById(R.id.recentitemImg)
            tvnewitemprice =  view.findViewById(R.id.tvprice)
//            tvnewitemTypeName = view.findViewById(R.id.tvTypeName)
            tvnewitemname = view.findViewById(R.id.tvItemName)
            imgLike = view.findViewById(R.id.imgLike)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewItemsViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.activity_recent_item_display, parent, false)
        return NewItemsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewItemsViewHolder, position: Int) {
        val newItem = newtItemList[position]
        Glide.with(context).load(newItem.newitemImage).into(holder.newitemImg)
//        holder.tvnewitemTypeName.text= newItem.newitemType
        holder.tvnewitemprice.text= newItem.newitemPrice
        holder.tvnewitemname.text= newItem.newItemName

        holder.newitemImg.setOnClickListener {
            Toast.makeText(context, "This item is selected", Toast.LENGTH_SHORT).show()
            val newItemintent = Intent(context, ItemDisplayActivity::class.java)
            newItemintent.putExtra("newitems", newItem)
            context.startActivity(newItemintent)
        }
        ////////////////-----------------Adding items to  favourite code---------------/////////////
        holder.imgLike.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val repository = BuyerFavouriteItemRepository()
                    val response = repository.insertBuyerFavouriteItem(newItem._id!!)
                    if (response.success == true) {

                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Item Added Successfully", Toast.LENGTH_SHORT)
                                .show()
                            val notificationManager = NotificationManagerCompat.from(context)
                            val notificationChannels = NotificationChannel(context)
                            notificationChannels.createNotificationChannels()

                            val notification =
                                NotificationCompat.Builder(context, notificationChannels.CHANNEL_1)
                                    .setSmallIcon(R.drawable.notification)
                                    .setContentTitle("Item Added")
                                    .setContentText("Item Added Successfully to Favourites")
                                    .setColor(Color.BLUE)
                                    .build()
                            notificationManager.notify(2, notification)
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Error Adding", Toast.LENGTH_SHORT).show()
//                                    Toast.makeText(context, response.success, Toast.LENGTH_SHORT)
//                                        .show()
                        }
                    }
                } catch (ex: IOException) {
                    withContext(Dispatchers.Main) {
//                            Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT).show()
//                            val notificationManager = NotificationManagerCompat.from(context)
//                            val notificationChannels = NotificationChannel(context)
//                            notificationChannels.createNotificationChannels()
//
//                            val notification = NotificationCompat.Builder(context,notificationChannels.CHANNEL_1)
//                                .setSmallIcon(R.drawable.notification)
//                                .setContentTitle("Item Added")
//                                .setContentText("Item Added Successfully to Favourites")
//                                .setColor(Color.BLUE)
//                                .build()
//                            notificationManager.notify(2,notification)
                    }
                }
            }

        }

    }

    override fun getItemCount(): Int {
        return newtItemList.size    }

}