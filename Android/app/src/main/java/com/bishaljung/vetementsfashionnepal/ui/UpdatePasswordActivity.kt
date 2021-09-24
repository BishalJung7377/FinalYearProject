package com.bishaljung.vetementsfashionnepal.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.bishaljung.vetementsfashionnepal.Entity.Buyer
import com.bishaljung.vetementsfashionnepal.R
import com.bishaljung.vetementsfashionnepal.Repository.BuyerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class UpdatePasswordActivity : AppCompatActivity() {
    private lateinit var backIcon: ImageView
    private lateinit var tvupdatetxt: TextView
    private lateinit var tvVetements: TextView
    private lateinit var imageVetments: ImageView
    private lateinit var etupdatePass: EditText
    private lateinit var btnUpdatePass: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_password)

        backIcon = findViewById(R.id.backIcon)
        tvupdatetxt = findViewById(R.id.tvupdatetxt)
        tvVetements= findViewById(R.id.tvVetements)
        imageVetments= findViewById(R.id.imageVetments)
        etupdatePass= findViewById(R.id.etupdatePass)
        btnUpdatePass= findViewById(R.id.btnUpdatePass)

        btnPasswordupdate()
        backImageBtn()
    }

    fun btnPasswordupdate() {
        btnUpdatePass.setOnClickListener {
            updateinfo()
        }
    }

    ////function for back
fun backImageBtn() {
    backIcon.setOnClickListener {
        val intent = Intent(this, DiscoverItemsActivity::class.java)
        startActivity(intent)
    }
}



    /////////user password update info
    fun updateinfo() {
        val etPassword = etupdatePass.text.toString()
        val buyer =
        Buyer(BuyerPassword = etPassword,  )
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository = BuyerRepository()
                val response = repository.updateBuyerPassword(buyer)
                if (response.success == true) {


                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@UpdatePasswordActivity, "Successfully Updated Password", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@UpdatePasswordActivity,
                            "Error Editing user",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (ex: IOException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@UpdatePasswordActivity, "Error updating ", Toast.LENGTH_SHORT)
                        .show()
                }

            }
        }

    }


}