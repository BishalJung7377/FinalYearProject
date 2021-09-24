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
import com.bishaljung.vetementsfashionnepal.Entity.RecentItemsModel
import com.bishaljung.vetementsfashionnepal.Repository.BuyerFavouriteItemRepository
import com.bishaljung.vetementsfashionnepal.api.ServiceBuilder
import com.bishaljung.vetementsfashionnepal.ui.Notification.NotificationChannel
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException


class RecentItemsAdapter(
    val recentitemlist: ArrayList<RecentItemsModel>,
    val context: Context
) : RecyclerView.Adapter<RecentItemsAdapter.RecentItemsViewHolder>() {
    class RecentItemsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recentitemImg: ImageView
        val tvprice: TextView
//        val tvTypeName: TextView
        val tvrecentitemname: TextView
//        val tvRecentItemColor: TextView
        val imgLike: ImageView
//        val tvtypes : TextView
//        val tvDescript: TextView

        init {
            recentitemImg = view.findViewById(R.id.recentitemImg)
            tvprice = view.findViewById(R.id.tvprice)
//            tvTypeName = view.findViewById(R.id.tvTypeName)
            tvrecentitemname = view.findViewById(R.id.tvItemName)
//            tvRecentItemColor = view.findViewById(R.id.tvRecentItemColor)
            imgLike = view.findViewById(R.id.imgLike)
//            tvtypes = view.findViewById(R.id.tvtypes)
//            tvDescript = view.findViewById(R.id.tvDescript)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentItemsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_recent_item_display, parent, false)
        return RecentItemsViewHolder(view)

    }

    override fun onBindViewHolder(holder: RecentItemsViewHolder, position: Int) {
        val recentitem = recentitemlist[position]
        val imagePath = ServiceBuilder.loadImagePath() + recentitem.ProductImage
        if (!recentitem.ProductImage.equals(null)) {
            Glide.with(context)
                .load(imagePath)
                .fitCenter()
                .into(holder.recentitemImg)
        }
//        holder.tvTypeName.text = recentitem.ProductType
        holder.tvprice.text = recentitem.ProductPrice
        holder.tvrecentitemname.text = recentitem.ProductName
//        holder.tvRecentItemColor.text = recentitem.ProductColor
        //   holder.tvDescript.text= recentitem.ProductDescription

////////////==========codes for parcel of infromation to another activity===============////////////
        holder.recentitemImg.setOnClickListener {
            Toast.makeText(context, "${recentitem.ProductName}  item is selected", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, ItemDisplayActivity::class.java)
            intent.putExtra("items", recentitem)
            context.startActivity(intent)
        }
        ////////////////----------------- adding items to favourite item code---------------/////////////
        holder.imgLike.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val repository = BuyerFavouriteItemRepository()
                    val response = repository.insertBuyerFavouriteItem(recentitem._id!!)
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

                    }
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return recentitemlist.size
    }
}