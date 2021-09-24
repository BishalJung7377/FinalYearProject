package com.bishaljung.vetementsfashionnepal.ui.Fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.BaseColumns._ID
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.bishaljung.vetementsfashionnepal.Database.BuyerDb
import com.bishaljung.vetementsfashionnepal.Entity.Buyer
import com.bishaljung.vetementsfashionnepal.R
import com.bishaljung.vetementsfashionnepal.Repository.BuyerCartItemRepository
import com.bishaljung.vetementsfashionnepal.Repository.BuyerRepository
import com.bishaljung.vetementsfashionnepal.api.ServiceBuilder
import com.bishaljung.vetementsfashionnepal.ui.UpdatePasswordActivity
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class ProfileFragments : Fragment() {
    //    private lateinit var imgbackIcn: ImageView
    private lateinit var tvRegister: TextView
    private lateinit var userprofileimage: ImageView
    private lateinit var tvuserprofilename: TextView
    private lateinit var tvuserprofileemail: TextView
    private lateinit var totalitemcrdview: CardView
    var age:Int?=null
    var gender:String?=null
     var email: String? = null
    var photo: String?=null
    private lateinit var itemCardView: ImageView
    private lateinit var tvuseritems: TextView
    private lateinit var tvitemnumbers: TextView
    private lateinit var tveditFname: TextInputLayout
    private lateinit var eteditFname: TextInputEditText
    private lateinit var tveditEmail: TextInputLayout
    private lateinit var eteditEmail: TextInputEditText
    private lateinit var EditPassword: TextView
    private lateinit var tveditnumb: TextInputLayout
    private lateinit var eteditnumb: TextInputEditText
    private lateinit var btnupdate: Button


//    private lateinit var binding: ActivityFragmentProfileBinding
    private var REQUEST_GALLERY_CODE = 0
    private var REQUEST_CAMERA_CODE = 1
//    private lateinit var imageUrl : String
    private var imageUrl: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.activity_fragment_profile, container, false)
        tvRegister = view.findViewById(R.id.tvRegister)
        userprofileimage = view.findViewById(R.id.userprofileimage)
        tvuserprofilename = view.findViewById(R.id.tvuserprofilename)
        tvuserprofileemail = view.findViewById(R.id.tvuserprofileemail)
        totalitemcrdview = view.findViewById(R.id.totalitemcrdview)
        itemCardView = view.findViewById(R.id.itemCardView)
        tvuseritems = view.findViewById(R.id.tvuseritems)
        tvitemnumbers = view.findViewById(R.id.tvitemnumbers)
        tveditFname = view.findViewById(R.id.tveditFname)
        eteditFname = view.findViewById(R.id.eteditFname)
        tveditEmail = view.findViewById(R.id.tveditEmail)
        eteditEmail = view.findViewById(R.id.eteditEmail)
        EditPassword = view.findViewById(R.id.EditPassword)
        tveditnumb = view.findViewById(R.id.tveditnumb)
        eteditnumb = view.findViewById(R.id.eteditnumb)
        btnupdate = view.findViewById(R.id.btnupdate)


        getBuyerInfo()
        btnupdate()
        opencameraForImage()
        openEditPass()
        getCartCount()
        return view
    }

    fun openEditPass(){
        EditPassword.setOnClickListener {
            val intent = Intent(context, UpdatePasswordActivity::class.java)
            startActivity(intent)
        }
    }
    fun opencameraForImage() {
        userprofileimage.setOnClickListener {
            loadPopUpMenu()
        }
    }

    fun btnupdate() {
        btnupdate.setOnClickListener {
            updateinfo()
        }
    }
    ////////////////============function for updating user information -----------------////////////

    fun updateinfo() {
        val etFullname = eteditFname.text.toString()
        val etNumber = eteditnumb.text.toString()
        val buyer =
            Buyer(BuyerFullName = etFullname, BuyerPhone = etNumber, BuyerAge =age, BuyerGender = gender )

//        Buyer(BuyerFullName = etFullname, BuyerPassword = etPassword, BuyerPhone = etNumber, BuyerAge =age, BuyerGender = gender )
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository = BuyerRepository()
                val response = repository.updateBuyerData(buyer)
                if (response.success == true) {


                    ///////-=---------------Image upload------//////////
                    if (imageUrl != null){
                        uploadImage()
                    }else{
                    }
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Success Fully Updated User Data", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            context,
                            "Error Editing user",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (ex: IOException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Error uploading ", Toast.LENGTH_SHORT)
                        .show()
                }

            }
        }

    }

    ///////////////////////----------------function for getting cart items count in profile fragments-------------/////////////
    private fun getCartCount(){
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val catItemRepository = BuyerCartItemRepository()
                val response = catItemRepository.getBuyercartItem()
                if (response.success == true) {
                    val cartItemList = response.message
                    val cartItemSize  = cartItemList?.size

                    withContext(Dispatchers.Main) {

                        tvitemnumbers.text = cartItemSize.toString()
                    }
                } else {

                }
            }
        } catch (ex: Exception) {
        }
    }


    /////////////////////------------------------function for getting user data from API---------/////////////
    private fun getBuyerInfo() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val repository = BuyerRepository()
                val response = repository.getBuyerData()
                if (response.success == true) {
                    val buyerData = response.data

                    ////////////------------------codes for storing user data in room database locally----------//////////////
                    BuyerDb.getInstance(requireContext()).getBuyerDAO().deleteUser(buyerData!!)
                    BuyerDb.getInstance(requireContext()).getBuyerDAO()
                        .RegisterBuyer(buyerData!!)
                    withContext(Dispatchers.Main) {
                        tvuserprofilename.setText(response.data?.BuyerFullName)
                        tvuserprofileemail.setText(response.data?.BuyerEmail)
                        eteditFname.setText(response.data?.BuyerFullName)
                        eteditnumb.setText(response.data?.BuyerPhone)
                        eteditEmail.setText(response.data?.BuyerEmail)
                        age= response.data?.BuyerAge!!.toInt()
                        gender = response.data?.BuyerGender.toString()
                        val imagePath = ServiceBuilder.loadImagePath() + buyerData.BuyerPhoto
                        if (!userprofileimage.equals("no-photo.jpg")) {
                            Glide.with(this@ProfileFragments)
                                .load(imagePath)
                                .fitCenter()
                                .into(userprofileimage)
                        }
                    }
                }

            }

        } catch (ex: Exception) {

            Toast.makeText(
                context,
                "Error here man: ${ex.toString()}", Toast.LENGTH_SHORT
            ).show()
        }
    }

    //////////////////-----------defining mime type-------////////
    fun getMimeType(file: File): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(file.path)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
    }

    ////////////-------------------function for uploading image to server----------///////////
    private fun uploadImage() {
        if (imageUrl != null) {
            val file = File(imageUrl!!)
            val mimeType = getMimeType(file);
            val reqFile =
                RequestBody.create(MediaType.parse(mimeType!!), file)
            val body =
                MultipartBody.Part.createFormData("BuyerPhoto", file.name, reqFile)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val repository = BuyerRepository()
                    val response = repository.updateBuyerPhoto(body)
                    println(response.success)
                    if (response.success == true) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Successfully Uploaded Image", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                } catch (ex: Exception) {
                    withContext(Dispatchers.Main) {
                        Log.d("Mero Error ", ex.toString())
                        Toast.makeText(
                            context,
                            ex.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }



    //////////////////////---------------------function for opening gallery------------/////////
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_CODE)
    }


    ///////////////-------------function for opening camera---------------/////////
    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_CAMERA_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_GALLERY_CODE && data != null) {
                val selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val contentResolver = context?.contentResolver
                val cursor =
                    contentResolver?.query(selectedImage!!, filePathColumn, null, null, null)
                cursor!!.moveToFirst()
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                imageUrl = cursor.getString(columnIndex)
                userprofileimage.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
                cursor.close()
            } else if (requestCode == REQUEST_CAMERA_CODE && data != null) {
                val imageBitmap = data.extras?.get("data") as Bitmap
                val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val file = bitmapToFile(imageBitmap, "$timeStamp.jpg")
                imageUrl = file!!.absolutePath
                userprofileimage.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
            }

        }
    }

    private fun loadPopUpMenu() {
        val popupMenu = PopupMenu(context, userprofileimage)
        popupMenu.menuInflater.inflate(R.menu.gallery_camera_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menuCamera ->
                    openCamera()
                R.id.menuGallery ->
                    openGallery()
            }
            true
        }
        popupMenu.show()
    }


    ///code for accessing the image by giving url to the image that is captured because it is not stored in any physical state and which can be extracted by using url and bitmap to file
    private fun bitmapToFile(
        bitmap: Bitmap,
        fileNameToSave: String
    ): File? {
        var file: File? = null
        return try {
            file = File(
                context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    .toString() + File.separator + fileNameToSave
            )
            file.createNewFile()
            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
            val bitMapData = bos.toByteArray()
            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitMapData)
            fos.flush()
            fos.close()
            file
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            file // it will return null
        }
    }
}














