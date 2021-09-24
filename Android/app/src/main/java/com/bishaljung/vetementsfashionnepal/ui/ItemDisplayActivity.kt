package com.bishaljung.vetementsfashionnepal.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bishaljung.vetementsfashionnepal.R
import com.bishaljung.vetementsfashionnepal.Entity.NewItemModel
import com.bishaljung.vetementsfashionnepal.Entity.RecentItemsModel
import com.bishaljung.vetementsfashionnepal.Entity.ViewAllItemModel
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



class ItemDisplayActivity : AppCompatActivity() {

    ///////////----------permision for read, write in device----------///////
    private val permissions = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

private var itemId: String = ""
    var rbLeft: AppCompatRadioButton? = null
    var rbRight: AppCompatRadioButton? = null
    var rbfarRight: AppCompatRadioButton? = null
    private lateinit var tvsizeAvailable: TextView
    private lateinit var tvAvailibility: TextView
    private lateinit var backIcn: ImageView
    private lateinit var imageView: ImageView
    private lateinit var tvColor: TextView
    private lateinit var tvitemColor: TextView
    private lateinit var tvdisplayItemName: TextView
    private lateinit var tvType: TextView
    private lateinit var tvDescription: TextView
    private lateinit var tvAvailableYes: TextView
    private lateinit var tvDesc: TextView
    private lateinit var tvitemtype: TextView
    private lateinit var tvItmpriceRs: TextView
    private lateinit var tvItmprice: TextView
    private lateinit var tvSize: TextView
    private lateinit var tvPosterName: TextView
    private lateinit var tvPostedby: TextView
    private lateinit var btnAddtoCart: Button
    private lateinit var imgFavourite: ImageView
    val recentitemlist = ArrayList<RecentItemsModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar()!!.hide();
        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_display)

        tvsizeAvailable = findViewById(R.id.tvsizeAvailable)
        tvAvailibility = findViewById(R.id.tvAvailibility)
        tvAvailableYes = findViewById(R.id.tvAvailableYes)
        backIcn = findViewById(R.id.backIcn)
        imageView = findViewById(R.id.imageView)
        tvColor = findViewById(R.id.tvColor)
        tvdisplayItemName = findViewById(R.id.tvdisplayItemName)
        tvitemColor = findViewById(R.id.tvitemColor)
        tvType = findViewById(R.id.tvType)
        tvDescription = findViewById(R.id.tvDescription)
        tvDesc = findViewById(R.id.tvDesc)
        tvitemtype = findViewById(R.id.tvitemtype)
        tvItmpriceRs = findViewById(R.id.tvItmpriceRs)
        tvItmprice = findViewById(R.id.tvItmprice)
        tvPosterName = findViewById(R.id.tvPosterName)
        tvSize = findViewById(R.id.tvSize)
        tvPostedby = findViewById(R.id.tvPostedby)
        btnAddtoCart = findViewById(R.id.btnAddtoCart)
        imgFavourite = findViewById(R.id.imgFavourite)


        if (!hasPermission()) {
            requestPermission()
        }

        val intent = intent.getParcelableExtra<NewItemModel>("newitems")

        if (intent != null) {
             itemId = intent._id
        }

        viewallItemData()
        newItemData()
        recentItemData()
        backIcn.setOnClickListener {
            val intent = Intent(this, DiscoverItemsActivity::class.java)
            startActivity(intent)
        }
        imgFavourite.setOnClickListener {
            addtFavourite()
        }

        btnAddtoCart.setOnClickListener {
            addtoCart()
        }
    }


    /////////////============getting data--------------------////////////////
    fun newItemData() {
        val intent = intent.getParcelableExtra<NewItemModel>("newitems")

        if (intent != null) {
             itemId = intent._id
            val itemName = intent.newItemName
            val itemColor = intent.newitemColor
            val itemPrice = intent.newitemPrice
            val itemType = intent.newitemType
            val newPoductImage = intent.newitemImage
            val newItemDesc = intent.newitemDescription

            tvdisplayItemName.text = itemName.toString()
            tvColor.text = itemColor.toString()
            tvItmprice.text = itemPrice.toString()
            tvitemtype.text = itemType.toString()
            tvDescription.text = newItemDesc.toString()

            Glide.with(this@ItemDisplayActivity).load(newPoductImage)
                .into(imageView)
        } else {
//            Toast.makeText(this, "No value Retrived",Toast.LENGTH_SHORT).show()

        }
    }

    //////////--------------getting recent items dataa---------------/////////////////
    fun recentItemData() {
        val intent = intent.getParcelableExtra<RecentItemsModel>("items")

        if (intent != null) {
            itemId = intent._id
            val recentitemName = intent.ProductName
            val recentitemColor = intent.ProductColor
            val itemPrice = intent.ProductPrice
            val itemType = intent.ProductType
            val productimageview = intent.ProductImage
            val recentItemDesc = intent.ProductDescription
            val Size = intent.ProductSize
            val Poster = intent.CreatedBy

            tvdisplayItemName.text = recentitemName.toString()
            tvColor.text = recentitemColor.toString()
            tvItmprice.text = itemPrice.toString()
            tvitemtype.text = itemType.toString()
            tvDescription.text = recentItemDesc.toString()
            tvSize.text = Size.toString()
            tvPosterName.text = Poster.toString()

            Glide.with(this@ItemDisplayActivity)
                .load(ServiceBuilder.loadImagePath()+ productimageview)
                .into(imageView)

        } else {
        }
    }


    ////=====================function for viewing all dataa=======////////////
    fun viewallItemData() {
        val intent = intent.getParcelableExtra<ViewAllItemModel>("allitems")

        if (intent != null) {
            itemId = intent._id
            val recentitemName = intent.ProductName
            val recentitemColor = intent.ProductColor
            val itemPrice = intent.ProductPrice
            val itemType = intent.ProductType
            val imageview = intent.ProductImage
            val recentItemDesc = intent.ProductDescription
            val Size = intent.ProductSize
            val Poster = intent.CreatedBy

            tvdisplayItemName.text = recentitemName.toString()
            tvColor.text = recentitemColor.toString()
            tvItmprice.text = itemPrice.toString()
            tvitemtype.text = itemType.toString()
            tvDescription.text = recentItemDesc.toString()
            tvSize.text = Size.toString()
            tvPosterName.text = Poster.toString()

            Glide.with(this@ItemDisplayActivity)
                .load(ServiceBuilder.loadImagePath()+ imageview)
                .fitCenter()
                .into(imageView)
        } else {
        }
    }


    ////////////////////----------------------function for addig items to cart-------------//////////
    fun addtoCart() {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository = BuyerCartItemRepository()
                val response = repository.insertBuyercartItem(itemId!!)
                if (response.success == true) {

                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@ItemDisplayActivity,
                            "Item Added Successfully To Cart",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        val notificationManager =
                            NotificationManagerCompat.from(this@ItemDisplayActivity)
                        val notificationChannels = NotificationChannel(this@ItemDisplayActivity)
                        notificationChannels.createNotificationChannels()


                        /////////////=================codes for notification=======/////////
                        val notification =
                            NotificationCompat.Builder(
                                this@ItemDisplayActivity,
                                notificationChannels.CHANNEL_1
                            )
                                .setSmallIcon(R.drawable.notification)
                                .setContentTitle("Item Added")
                                .setContentText("Item Added Successfully to Cart")
                                .setColor(Color.BLUE)
                                .build()
                        notificationManager.notify(2, notification)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@ItemDisplayActivity, "Error Adding", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } catch (ex: IOException) {
                withContext(Dispatchers.Main) {
                }
            }

        }

    }

    ///==================codes for adding items to favourite==============////////////
    fun addtFavourite() {
         CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository = BuyerFavouriteItemRepository()
                val response = repository.insertBuyerFavouriteItem(itemId!!)
                if (response.success == true) {

                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@ItemDisplayActivity,
                            "Item Added Successfully",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        val notificationManager =
                            NotificationManagerCompat.from(this@ItemDisplayActivity)
                        val notificationChannels = NotificationChannel(this@ItemDisplayActivity)
                        notificationChannels.createNotificationChannels()

                        val notification =
                            NotificationCompat.Builder(
                                this@ItemDisplayActivity,
                                notificationChannels.CHANNEL_1
                            )
                                .setSmallIcon(R.drawable.notification)
                                .setContentTitle("Item Added")
                                .setContentText("Item Added Successfully to Favourites")
                                .setColor(Color.BLUE)
                                .build()
                        notificationManager.notify(2, notification)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@ItemDisplayActivity, "Error Adding", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } catch (ex: IOException) {
                withContext(Dispatchers.Main) {
                }
            }

        }

    }


    ////////////////------------function for request of permission--------------///////
    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            permissions, 1
        )
    }

    private fun hasPermission(): Boolean {
        var hasPermission = true
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                hasPermission = false
            }
        }
        return hasPermission
    }

}