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
import com.bishaljung.vetementsfashionnepal.Entity.ItemCartModel
import com.bishaljung.vetementsfashionnepal.Repository.BuyerCartItemRepository
import com.bishaljung.vetementsfashionnepal.Repository.BuyerFavouriteItemRepository
import com.bishaljung.vetementsfashionnepal.api.ServiceBuilder
import com.bishaljung.vetementsfashionnepal.ui.Notification.NotificationChannel
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.w3c.dom.Text
import java.io.IOException

class ItemCartAdapter(
    val cartItemlist: ArrayList<ItemCartModel>,
    val context: Context
) : RecyclerView.Adapter<ItemCartAdapter.ItemCartViewHolder>() {
    class ItemCartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgitemimage: ImageView
//        val tvRsPrice: TextView
        val tvitemname: TextView
         val tvCartColor : TextView
        val tvitemprice: TextView
       val imgitemcancel: ImageView
        val tvCheckOut : ImageView
       val tvColorName : TextView
       val tvCheckout: TextView

        init {
            imgitemimage = view?.findViewById(R.id.imgitemimage)
//            tvRsPrice = view?.findViewById(R.id.tvRsPrice)
            tvitemname = view?.findViewById(R.id.tvitemname)
            tvitemprice = view?.findViewById(R.id.tvitemprice)
            tvCartColor = view?. findViewById(R.id.tvCartColor)
            tvColorName = view?.findViewById(R.id.tvColorName)
            imgitemcancel = view?.findViewById(R.id.imgitemcancel)
            tvCheckOut = view?.findViewById(R.id.tvCheckOut)
            tvCheckout = view?.findViewById(R.id.tvCheckout)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCartViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_cart_item, parent, false)
        return ItemCartViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemCartViewHolder, position: Int) {

        val cartitem = cartItemlist[position]
        val imagePath = ServiceBuilder.loadImagePath() + cartitem.CartItemid!![0].ProductImage

        holder.tvitemname.text = cartitem.CartItemid!![0].ProductName
        holder.tvitemprice.text = cartitem.CartItemid!![0].ProductPrice
        holder.tvCartColor.text = cartitem.CartItemid!![0].ProductColor
        if (!cartitem.CartItemid!![0].ProductImage.equals(null)) {
            Glide.with(context)
                .load(imagePath)
                .fitCenter()
                .into(holder.imgitemimage)
        }
        ////////////////----------------- cart item delete code---------------/////////////
        holder.imgitemcancel.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setCancelable(false)
            builder.setTitle("Delete Favourite Item")
            builder.setMessage("Are you sure to delete this Item?")
            builder.setIcon(R.drawable.ic_close)
            builder.setPositiveButton("Yes",
                DialogInterface.OnClickListener { _, _ ->
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val cartItemdeleteRepository = BuyerCartItemRepository()
                            val response = cartItemdeleteRepository.deleteBuyercartItem(cartitem._id!!)
                            if (response.success == true) {
                                withContext(Dispatchers.Main) {

                                    cartItemlist.remove(cartitem)
                                    notifyDataSetChanged()
                                    val notificationManager = NotificationManagerCompat.from(context)
                                    val notificationChannels = NotificationChannel(context)
                                    notificationChannels.createNotificationChannels()

                                    val notification = NotificationCompat.Builder(context,notificationChannels.CHANNEL_2)
                                        .setSmallIcon(R.drawable.notification)
                                        .setContentTitle("Item Deleted")
                                        .setContentText("Item Deleted Successfully from cart")
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
                    cartItemlist.removeAt(position)
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

        ////////////////-----------------  item check out  code---------------/////////////
        holder.tvCheckOut.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setCancelable(false)
            builder.setTitle("Proceed To Check Out?")
            builder.setMessage("Are you sure to check out this Item?")
            builder.setIcon(R.drawable.ic_checkout)
            builder.setPositiveButton("Yes",
                DialogInterface.OnClickListener { _, _ ->
                                    val notificationManager = NotificationManagerCompat.from(context)
                                    val notificationChannels = NotificationChannel(context)
                                    notificationChannels.createNotificationChannels()

                                    val notification = NotificationCompat.Builder(context,notificationChannels.CHANNEL_2)
                                        .setSmallIcon(R.drawable.notification)
                                        .setContentTitle("Checked Out")
                                        .setContentText("Checked Out total price is ${cartitem.CartItemid!![0].ProductPrice} with Rs50 Delivery Charge ")
                                        .setColor(Color.BLUE)
                                        .build()
                                    notificationManager.notify(2,notification)

                    Toast.makeText(context,"Total Price is ${cartitem.CartItemid!![0].ProductPrice} with Rs50 Delivery Charge  ",Toast.LENGTH_SHORT).show()


                })
            builder.setNegativeButton("No",
                DialogInterface.OnClickListener { dialog, _ -> //if user select "No", just cancel this dialog and continue with app
                    dialog.cancel()
                })
            val alert: AlertDialog = builder.create()
            alert.setCancelable(false)
            alert.show()
        }

    }

    override fun getItemCount(): Int {
        return cartItemlist.size
    }
}