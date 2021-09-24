package com.bishaljung.vetementsfashionwearos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.view.Window

import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bishaljung.vetementsfashionwearos.Repository.BuyerCartItemRepository
import com.bishaljung.vetementsfashionwearos.Repository.BuyerRepository
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserProfileActivity : WearableActivity() {

    private lateinit var tvuserprofilename: TextView
    private lateinit var tvuserprofileemail: TextView
    private lateinit var tvcartnumber: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        tvuserprofilename = findViewById(R.id.tvuserprofilename)
        tvuserprofileemail = findViewById(R.id.tvuserprofileemail)
        tvcartnumber = findViewById(R.id.tvcartnumber)
        getBuyerInfo()
        getCartCount()
    }
    private fun getBuyerInfo() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val repository = BuyerRepository()
                val response = repository.getBuyerData()
                if (response.success == true) {
                    withContext(Dispatchers.Main) {
                        tvuserprofilename.setText(response.data?.BuyerFullName)
                        tvuserprofileemail.setText(response.data?.BuyerEmail)


                    }
                }

            }

        } catch (ex: Exception) {

            Toast.makeText(
                this,
                "Error : ${ex.toString()}", Toast.LENGTH_SHORT
            ).show()
        }
    }


    private fun getCartCount(){
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val catItemRepository = BuyerCartItemRepository()
                val response = catItemRepository.getBuyercartItem()
                if (response.success == true) {
                    val cartItemList = response.message
                 val cartItemSize  = cartItemList?.size

                          withContext(Dispatchers.Main) {
//                              tvcartnumber.setText(cartItemList!!.size)
                              tvcartnumber.text = "Total Item in Cart is ${cartItemSize.toString()}"
                    }
                } else {

                }
            }
        } catch (ex: Exception) {
            Toast.makeText(
                this,
                "Error : ${ex.toString()}", Toast.LENGTH_SHORT
            ).show()
        }
    }



}





