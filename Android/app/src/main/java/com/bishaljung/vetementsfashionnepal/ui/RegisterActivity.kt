package com.bishaljung.vetementsfashionnepal.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import com.bishaljung.vetementsfashionnepal.Database.BuyerDb
//import com.bishaljung.vetementsfashionnepal.Database.BuyerDb
import com.bishaljung.vetementsfashionnepal.Entity.Buyer
import com.bishaljung.vetementsfashionnepal.Entity.ItemCartModel
import com.bishaljung.vetementsfashionnepal.R
import com.bishaljung.vetementsfashionnepal.Repository.BuyerRepository
import com.bishaljung.vetementsfashionnepal.Util.toast
import com.bishaljung.vetementsfashionnepal.api.ServiceBuilder
import com.bishaljung.vetementsfashionnepal.auth.AuthViewModel
import com.bishaljung.vetementsfashionnepal.auth.RegisterAuthViewModel
import com.bishaljung.vetementsfashionnepal.databinding.ActivityLoginBinding
import com.bishaljung.vetementsfashionnepal.databinding.ActivityRegisterBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity() {

    var rbMale: AppCompatRadioButton? = null
    var rbFemale: AppCompatRadioButton? = null
    var rbOthers: AppCompatRadioButton?= null
    private lateinit var regText: TextView
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var number: EditText
    private lateinit var password: EditText
    private lateinit var etAge: EditText
    private lateinit var regBtn: Button
    private lateinit var tvgender: TextView
    private lateinit var radioGroup: RadioGroup
    var  gender = ""

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterAuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar()!!.hide();
        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_register)

        regText = binding.tvRegister
        name =binding.etName
        email = binding.etEmail
        number = binding.etNumber
        password = binding.etPass
        etAge = binding.etAge
        regBtn = binding.btnregister
        tvgender = binding.tvgender
        radioGroup = binding.radioGroup
        rbMale = binding.rbMale
        rbFemale =  binding.rbFemale
        rbOthers = binding.rbOthers


        regBtn.setOnClickListener {
            if(SignupValidation()) {
                buyerSignup()
            }
        }

    }

    ///////////////////buyer signup function
    fun buyerSignup() {
        try{
            val BuyerFullName  = name.text.toString().trim()
            val BuyerEmail  = email.text.toString().trim()
            val BuyerPhone = number.text.toString().trim()
            val  BuyerPassword = password.text.toString().trim()
            val BuyerAge = etAge.text.toString().toInt()
            val BuyerGender = gender.trim()
            val buyerRegister = Buyer( BuyerFullName =BuyerFullName, BuyerAge =BuyerAge, BuyerEmail =BuyerEmail, BuyerPassword =BuyerPassword, BuyerPhone =BuyerPhone, BuyerGender =BuyerGender)
            CoroutineScope(Dispatchers.IO).launch {
                val repository = BuyerRepository()
                val response = repository.registerBuyer(buyerRegister)


                ////////-----------------Saving Data to Database----------------====================
                BuyerDb.getInstance(this@RegisterActivity).getBuyerDAO().RegisterBuyer(buyerRegister)
//                BuyerDb.getInstance(this@RegisterActivity).getBuyerDAO().insertCartItem(response)

                if(response.success == true){
                    withContext(Main){
                        toast("Successfully Registered As Buyer")
                        startActivity(
                            Intent(
                                this@RegisterActivity,
                                LoginActivity::class.java
                            )
                        )
                        clearTextfields()

                    }
                }else{
                    withContext(Main){
                        toast("Error registering user")
                    }
                }
            }
        }
        catch (e: IOException){
            Toast.makeText(this,"Error ${e.localizedMessage}",Toast.LENGTH_SHORT).show()
        }
    }


    //-----------------------------------Clearing text input textfields------------------------
    fun clearTextfields() {
        name.setText("")
        email.setText("")
        number.setText("")
        password.setText("")
        etAge.setText("")
        rbMale?.isChecked=false
        rbFemale?.isChecked=false
        rbOthers?.isChecked=false
    }

    /////-----------------------------Radio button codes-----------------------///////
    fun onRadioButtonClicked(view: View) {
        val isSelected = (view as AppCompatRadioButton).isChecked
        when (view.getId()) {
            R.id.rbMale -> if (isSelected) {
                rbMale!!.setTextColor(Color.BLACK)
                rbFemale!!.setTextColor(Color.BLACK)
                rbOthers!!.setTextColor(Color.BLACK)
                gender = "Male"

            }
            R.id.rbFemale -> if (isSelected) {
                rbMale!!.setTextColor(Color.BLACK)
                rbFemale!!.setTextColor(Color.BLACK)
                rbOthers!!.setTextColor(Color.BLACK)
                gender = "Female"
            }
            R.id.rbOthers -> if (isSelected) {
                rbMale!!.setTextColor(Color.BLACK)
                rbFemale!!.setTextColor(Color.BLACK)
                rbOthers!!.setTextColor(Color.BLACK)
                gender = "Other"
            }
        }
    }
//    name.setText("")
//    email.setText("")
//    number.setText("")
//    password.setText("")
//    etAge.setText("")
//    rbMale?.isChecked=false
//    rbFemale?.isChecked=false
//    rbOthers?.isChecked=false
    /////////////////================Sign up form validation codes===================////////////////
    private fun SignupValidation(): Boolean{
        var valid =  true
        when {
            TextUtils.isEmpty(name.text.toString())->{
                name.error = "Full Name Must not be empty"
                name.requestFocus()
                valid =false
            }
            TextUtils.isEmpty(etAge.text.toString())->{
                etAge.error = "Age Must be fulfilled"
                etAge.requestFocus()
                valid =false
            }
            TextUtils.isEmpty(email.text.toString())->{
                email.error = "Email must contain @"
                email.requestFocus()
                valid =false
            }
            TextUtils.isEmpty(number.text.toString())->{
                number.error = "Number must be entered"
                number.requestFocus()
                valid =false
            }
            TextUtils.isEmpty(password.text.toString())->{
                password.error = "Password Must not be empty"
                password.requestFocus()
                valid =false
            }

//            TextUtils.isEmpty(gender.toString())->{
//                gender.error = "Full Name Must not be empty"
//                gender.requestFocus()
//                valid =false
//            }
        }
        return valid
    }

}