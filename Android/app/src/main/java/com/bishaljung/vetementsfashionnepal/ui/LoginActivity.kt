package com.bishaljung.vetementsfashionnepal.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bishaljung.vetementsfashionnepal.Database.BuyerDb
import com.bishaljung.vetementsfashionnepal.Entity.Buyer
import com.bishaljung.vetementsfashionnepal.R
import com.bishaljung.vetementsfashionnepal.Repository.BuyerRepository
import com.bishaljung.vetementsfashionnepal.Util.toast
import com.bishaljung.vetementsfashionnepal.api.ServiceBuilder
import com.bishaljung.vetementsfashionnepal.auth.AuthViewModel
import com.bishaljung.vetementsfashionnepal.auth.AuthViewModelFactory
import com.bishaljung.vetementsfashionnepal.databinding.ActivityLoginBinding
import com.bishaljung.vetementsfashionnepal.ui.Fragments.HomeFragments
import com.bishaljung.vetementsfashionnepal.ui.Notification.NotificationChannel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import okhttp3.internal.Internal.instance
import java.io.IOException

class LoginActivity : AppCompatActivity() {

    //    private val factory: AuthViewModelFactory by instance()
    private lateinit var backIc: ImageView
    private lateinit var logIntxt: TextView
    private lateinit var vetementTitle: TextView
    private lateinit var vIcn: ImageView
    private lateinit var etemail: EditText
    private lateinit var etpassword: EditText
    private lateinit var forgetPass: TextView
    private lateinit var btnLogIn: Button
    private lateinit var linearLayout: LinearLayout

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar()!!.hide();
        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        backIc = binding.backIcn
        logIntxt = binding.tvlogIntxt
        vetementTitle = binding.tvtitlelogin
        vIcn = binding.imageV
        etemail = binding.etEmail
        etpassword = binding.etPass
        forgetPass = binding.tvforrgetpass
        btnLogIn = binding.btnIdlogin
        linearLayout = binding.lenearlayout


        btnLogIn()
        backIc()
    }


    ///////////=------------------Login Function Code---------------------//////////
    private fun btnLogIn() {
        btnLogIn.setOnClickListener {
            if (inputValidation()) {
                buyerloginCompare()
                saveSharedPref()
                // passingUserLoginField()
            }
        }
    }

    ///////////=------------------Back button image Function Code---------------------//////////
    private fun backIc() {
        backIc.setOnClickListener {
            val intent = Intent(this, StartPageActivity::class.java)
            startActivity(intent)
        }
    }


    ////////---------clear text field of input field in login page--------------///////////
    fun clearLoginTextfields() {
        etemail.setText("")
        etpassword.setText("")
    }


    ////////////---------------Shared Preference code-----------------------//////////
    private fun saveSharedPref() {
        val username = etemail.text.toString().trim()
        val password = etpassword.text.toString().trim()

        val sharedPref = getSharedPreferences("MyPref", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("username", username)
        editor.putString("password", password)
        editor.apply()
        toast("username and password saved")
    }


    //////////////////////===============Buyer login function and codes==============/////////
    fun buyerloginCompare() {
        val BuyerEmail = etemail.text.toString().trim()
        val BuyerPassword = etpassword.text.toString().trim()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository = BuyerRepository()
                val response = repository.checkBuyer(BuyerEmail, BuyerPassword)
                if (response.success == true) {

                    ServiceBuilder.token = "Bearer ${response.buyerToken}"
                    startActivity(
                        Intent(
                            this@LoginActivity,
                            DiscoverItemsActivity::class.java
                        )
                    )
                    val notificationManager = NotificationManagerCompat.from(this@LoginActivity)
                    val notificationChannels = NotificationChannel(this@LoginActivity)
                    notificationChannels.createNotificationChannels()

                    val notification = NotificationCompat.Builder(
                        this@LoginActivity,
                        notificationChannels.CHANNEL_1
                    )
                        .setSmallIcon(R.drawable.notification)
                        .setContentTitle("Login Successful")
                        .setContentText("Successfully Login as $BuyerEmail")
                        .setColor(Color.BLUE)
                        .build()
                    notificationManager.notify(2, notification)
                    finish()
                } else {
                    withContext(Dispatchers.Main) {
                        val snack =
                            Snackbar.make(
                                linearLayout,
                                "Invalid credentials",
                                Snackbar.LENGTH_LONG
                            )
                        snack.setAction("OK", View.OnClickListener {
                            snack.dismiss()
                        })
                        snack.show()
                    }
                }

            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    toast("Invalid Credentials")
                }
            }
            clearLoginTextfields()
        }

    }

/////////////////================Signin form validation codes===================////////////////
    private fun inputValidation(): Boolean {
        var valid = true
        when {
            TextUtils.isEmpty(etemail.text.toString()) -> {
                etemail.error = "Email Must Contain @ "
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
