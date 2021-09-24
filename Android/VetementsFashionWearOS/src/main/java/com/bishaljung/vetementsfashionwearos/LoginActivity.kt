package com.bishaljung.vetementsfashionwearos

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bishaljung.vetementsfashionwearos.Repository.BuyerRepository
import com.bishaljung.vetementsfashionwearos.api.ServiceBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.bishaljung.vetementsfashionnepal.ui.Notification.NotificationChannel

class LoginActivity : WearableActivity() {
    private lateinit var etemail: EditText
    private lateinit var etpassword: EditText
    private lateinit var tvtitlelogin: TextView
    private lateinit var btnIdlogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        etemail = findViewById(R.id.etEmail)
        etpassword = findViewById(R.id.etPass)
        tvtitlelogin = findViewById(R.id.tvtitlelogin)
        btnIdlogin = findViewById(R.id.btnIdlogin)


        btnLogIn()

        setAmbientEnabled()
    }


    /////function is created
    private fun btnLogIn() {
        btnIdlogin.setOnClickListener {
            if (inputValidation()) {
                buyerloginCompare()
            }
        }
    }


    fun buyerloginCompare() {
        val BuyerEmail = etemail.text.toString().trim()
        val BuyerPassword = etpassword.text.toString().trim()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                    ////API called
                val repository = BuyerRepository()
                val response = repository.checkBuyer(BuyerEmail, BuyerPassword)
                if (response.success == true) {

                    ServiceBuilder.token = "Bearer ${response.buyerToken}"
                    startActivity(
                        Intent(
                            this@LoginActivity,
                            UserProfileActivity::class.java
                        )
                    )


                    /////Notification Implemented
                    val notificationManager = NotificationManagerCompat.from(this@LoginActivity)
                    val notificationChannels = NotificationChannel(this@LoginActivity)
                    notificationChannels.createNotificationChannels()

                    val notification = NotificationCompat.Builder(this@LoginActivity,notificationChannels.CHANNEL_1)
                        .setSmallIcon(R.drawable.notification)
                        .setContentTitle("Login Successful")
                        .setContentText("Successfully Login as $BuyerEmail")
                        .setColor(Color.BLUE)
                        .build()
                    notificationManager.notify(2,notification)
                    finish()
                } else {
                    withContext(Dispatchers.Main) {
                        ///////toast implemented
                        Toast.makeText(this@LoginActivity, "Incorrect Credentials", Toast.LENGTH_SHORT).show()
                        }
                    }

            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@LoginActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /////form input validation
    private fun inputValidation(): Boolean {
        var valid = true
        when {
            TextUtils.isEmpty(etemail.text.toString()) -> {
                etemail.error = "Email Must Be Fulfilled "
                etemail.requestFocus()
                valid = false
            }
            TextUtils.isEmpty(etpassword.text.toString()) -> {
                etpassword.error = "Password Must Be Fulfilled"
                etpassword.requestFocus()
                valid = false
            }
        }
        return valid
    }
}