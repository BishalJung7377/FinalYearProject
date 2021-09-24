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
import com.bishaljung.vetementsfashionnepal.Entity.ViewAllItemModel
import com.bishaljung.vetementsfashionnepal.R
import com.bishaljung.vetementsfashionnepal.Repository.BuyerFavouriteItemRepository
import com.bishaljung.vetementsfashionnepal.api.ServiceBuilder
import com.bishaljung.vetementsfashionnepal.ui.ItemDisplayActivity
import com.bishaljung.vetementsfashionnepal.ui.Notification.NotificationChannel
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class ViewAllItemAdapter(
    val viewAllItemList: ArrayList<ViewAllItemModel>,
    val context: Context
) : RecyclerView.Adapter<ViewAllItemAdapter.ViewAllItemViewHolder>() {
    class ViewAllItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val viewallitemimage: ImageView
        val imgLike: ImageView
        val tvviewallRs: TextView
        val tvviewallprice: TextView
        val tvViewAllItemName: TextView
       // val tvviewallRecentItemColor: TextView
      //  val tvDescript: TextView

        init {
            viewallitemimage = view.findViewById(R.id.viewallitemimage)
            tvviewallRs = view.findViewById(R.id.tvviewallRs)
            tvViewAllItemName = view.findViewById(R.id.tvViewAllItemName)
            tvviewallprice = view.findViewById(R.id.tvviewallprice)
            imgLike = view.findViewById(R.id.imgLike)
        //    tvviewallRecentItemColor = view.findViewById(R.id.tvRecentItemColor)
          //  tvDescript = view.findViewById(R.id.tvDescript)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewAllItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.viewall_items, parent, false)

        return ViewAllItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewAllItemViewHolder, position: Int) {

        val viewallItem = viewAllItemList[position]
        val imagePath = ServiceBuilder.loadImagePath() + viewallItem.ProductImage
        println(imagePath)
        if (!viewallItem.ProductImage.equals(null)) {
            Glide.with(context)
                .load(imagePath)
                .fitCenter()
                .into(holder.viewallitemimage)
        }

        holder.tvviewallprice.text = viewallItem.ProductPrice
        holder.tvViewAllItemName.text = viewallItem.ProductName



        holder.viewallitemimage.setOnClickListener {
            Toast.makeText(context, "This item is being selected", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, ItemDisplayActivity::class.java)
            intent.putExtra("allitems", viewallItem)
            context.startActivity(intent)
        }

        ////////////////----------------- favourite item adding code---------------/////////////
        holder.imgLike.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val repository = BuyerFavouriteItemRepository()
                    val response = repository.insertBuyerFavouriteItem(viewallItem._id!!)
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
        return viewAllItemList.size
    }

}