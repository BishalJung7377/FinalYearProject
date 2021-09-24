package com.bishaljung.vetementsfashionnepal.ui

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bishaljung.vetementsfashionnepal.R
import com.bishaljung.vetementsfashionnepal.Repository.BuyerRepository
import com.bishaljung.vetementsfashionnepal.Util.toast
import com.bishaljung.vetementsfashionnepal.api.ServiceBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var imgVetements:  ImageView
    private lateinit var txtvetements: TextView
   // var session: SessionManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar()!!.hide();
        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        imgVetements= findViewById(R.id.imgVetements)
        txtvetements =   findViewById(R.id.txtvetements)

        imgVetements.alpha =  0f
        imgVetements.animate().setDuration(2500).alpha(
            1f
        ).withEndAction{
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()

        }
       getSharedPref()
    }





    /////////////////////////shared preferences codes
    private fun getSharedPref(){
        val sharedPref = getSharedPreferences("MyPref", MODE_PRIVATE)
        if (sharedPref.getString("username","")!!.isNotEmpty() && sharedPref.getString("password","")!!.isNotEmpty()){
            val username = sharedPref.getString("username","")
            val password = sharedPref.getString("password","")
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val repository = BuyerRepository()
                    val response = repository.checkBuyer(username!!, password!!)
                    if (response.success == true) {
                        ServiceBuilder.token="Bearer ${response.buyerToken}"
                        startActivity(Intent(this@SplashScreenActivity, DiscoverItemsActivity::class.java))
                        finish()
                    }
                    else {
                        reLogin()
                        val intent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
                catch (ex:Exception){
                    reLogin()
                    val intent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }else{
            CoroutineScope(Dispatchers.IO).launch {
                reLogin()
                val intent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    /////////shared preference relogin codes
    private fun reLogin(){
        val sharedPref = getSharedPreferences("MyPref", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("username","")
        editor.putString("password","")
        editor.apply()
    }
}