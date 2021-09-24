package com.bishaljung.vetementsfashionnepal.ui


import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.PowerManager
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.bishaljung.vetementsfashionnepal.Database.BuyerDb
import com.bishaljung.vetementsfashionnepal.R
import com.bishaljung.vetementsfashionnepal.Repository.BuyerRepository
import com.bishaljung.vetementsfashionnepal.api.ServiceBuilder
import com.bishaljung.vetementsfashionnepal.ui.Fragments.CartFragments
import com.bishaljung.vetementsfashionnepal.ui.Fragments.FavouritesFragment
import com.bishaljung.vetementsfashionnepal.ui.Fragments.HomeFragments
import com.bishaljung.vetementsfashionnepal.ui.Fragments.ProfileFragments
import com.bishaljung.vetementsfashionnepal.ui.adapter.ViewAllItemAdapter
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.*
import java.util.*


class DiscoverItemsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var fragmentCont: FrameLayout
    private lateinit var botommenu: BottomNavigationView

    private lateinit var sidenavphoto: ImageView
    private lateinit var sidenavname: TextView
    private lateinit var sidenavnumber: TextView

    private var navHeader: View? = null
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var sensorManager: SensorManager
    private var sensorAccelerometer: Sensor? = null
    private var sensorProximity: Sensor? = null
    private var sensorGyroscope: Sensor? = null
    private var acclValue = 0f
    private var lastAcclValue: Float = 0f
    private var shake: Float = 0f
    private var powerManager: PowerManager? = null
    private var wakeLock: PowerManager.WakeLock? = null
    private var field = 0x00000020
    //var session: SessionManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {

//        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
//        getSupportActionBar()!!.hide()
//        this.getWindow().setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN
//        )

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discover_items)


        getBuyerInfo()
        ////----------------------------sensors codes-----------------------------/////////////
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        if (!checkSensor()) {
            Toast.makeText(this, "Sensor Not Available", Toast.LENGTH_SHORT).show()
        } else {
            sensorAccelerometer =
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            sensorProximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
            sensorGyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        }
        try {

            field = PowerManager::class.java.javaClass.getField("PROXIMITY_SCREEN_OFF_WAKE_LOCK")
                .getInt(null)
        } catch (ignored: Throwable) {
            powerManager = getSystemService(POWER_SERVICE) as PowerManager;
            wakeLock = powerManager!!.newWakeLock(field, getLocalClassName());
        }



       //////-------------------------side nav bar for displaying user info------------------------////////////
        fragmentCont = findViewById(R.id.fragmentcontainer)
        botommenu = findViewById(R.id.bottomnavigation)
        drawerLayout = findViewById(R.id.drawerLayout)
        navView = findViewById(R.id.navView)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomnavigation)
        bottomNav.setOnNavigationItemSelectedListener(navListener)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentcontainer, HomeFragments())
            .commit()

        navView.setNavigationItemSelectedListener(this)


        //----------------------------- Navigation view header for showing user info----------------------//////////
        navHeader = navView.getHeaderView(0)
        sidenavname = navHeader!!.findViewById<View>(R.id.sidenavname) as TextView
        sidenavnumber = navHeader!!.findViewById<View>(R.id.sidenavnumber) as TextView
        sidenavphoto = navHeader!!.findViewById<View>(R.id.sidenavphoto) as ImageView

    }


    private val navListener =
////////--------------------------------Bottom navigationview for custom fragment items-----------------------------////////////////
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.nav_home -> selectedFragment = HomeFragments()
                R.id.nav_favourites -> selectedFragment = FavouritesFragment()
                R.id.nav_cart -> selectedFragment = CartFragments()
                R.id.nav_user -> selectedFragment = ProfileFragments()
            }
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(
                    R.id.fragmentcontainer,
                    selectedFragment
                ).commit()
            }
            true
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
///////////--------------------------------------------------Side nav bar items--------------------------------------////////////////////////////
        when (item.itemId) {
            R.id.nav_map ->
                startActivity(Intent(applicationContext, GoogleMapActivity::class.java))
            R.id.nav_about -> startActivity(
                Intent(
                    applicationContext,
                    AboutPageActivity::class.java
                )
            )
            R.id.nav_logout -> logout()
            R.id.nav_profile -> Toast.makeText(
                applicationContext,
                "Meaasge is clicked",
                Toast.LENGTH_SHORT
            ).show()

            R.id.nav_share -> Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show()
            R.id.nav_send -> Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show()
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

//////////////-----------------------------------fetching user data for displaying in side nav view--------------------------------------///////////
    private fun getBuyerInfo() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val repository = BuyerRepository()
                val response = repository.getBuyerData()
                if (response.success == true) {
                    val buyerData = response.data
                    BuyerDb.getInstance(this@DiscoverItemsActivity).getBuyerDAO().deleteUser(
                        buyerData!!
                    )
                    BuyerDb.getInstance(this@DiscoverItemsActivity).getBuyerDAO()
                        .RegisterBuyer(buyerData!!)
                    withContext(Dispatchers.Main) {
                        sidenavname.setText(response.data?.BuyerEmail)
                        sidenavnumber.setText(response.data?.BuyerPhone)

                        val imagePath = ServiceBuilder.loadImagePath() + buyerData.BuyerPhoto
                        if (!sidenavphoto.equals("no-photo.jpg")) {
                            Glide.with(this@DiscoverItemsActivity)
                                .load(imagePath)
                                .fitCenter()
                                .into(sidenavphoto)
                        }
                    }
                }

            }

        } catch (ex: Exception) {

            Toast.makeText(
                this@DiscoverItemsActivity,
                "Error getting Info: ${ex.toString()}", Toast.LENGTH_SHORT
            ).show()
        }
    }


    ////---------------------sensors codes-------------------------------------/////////
    private fun checkSensor(): Boolean {
        var flag = true
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) == null) {
            flag = false
        } else if (sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) == null) {
            flag = false
        }
        return flag
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
            accelerometerEventListener,
            sensorAccelerometer,
            SensorManager.SENSOR_DELAY_NORMAL
        )
        sensorManager.registerListener(
            proximityEventListener,
            sensorProximity,
            SensorManager.SENSOR_DELAY_NORMAL
        )
        sensorManager.registerListener(
            gyroEventListener,
            sensorGyroscope,
            SensorManager.SENSOR_DELAY_NORMAL
        )

    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(accelerometerEventListener)
        sensorManager.unregisterListener(proximityEventListener)
        sensorManager.unregisterListener(gyroEventListener)

    }


    /////------------------------gyro sensor code----------------------/////////////
    private val gyroEventListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            val values = event!!.values[1]
            if (values < 0)
                drawerLayout.closeDrawer(GravityCompat.START)
            else if (values > 0)
                drawerLayout.openDrawer(GravityCompat.START)
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }



    //////---------------------------accelerometer sensor code-------------------//////////
    private val accelerometerEventListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            lastAcclValue = acclValue
            acclValue = Math.sqrt((x * x + y * y + z * z).toDouble()).toFloat()
            val delta = acclValue - lastAcclValue
            shake = shake * 0.9f + delta
            if (shake > 12) {
                logout()
                Toast.makeText(
                    this@DiscoverItemsActivity,
                    "Successfully Logged Out",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }

    ////////////--------------------------proximity sensor code----------------------/////////////
    private val proximityEventListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            val values = event.values[0]
            if (values <= 4) {
                if (!wakeLock?.isHeld!!) {
                    wakeLock?.acquire();
                } else {
                    if (wakeLock!!.isHeld) {
                        wakeLock!!.release();
                    }
                }
            } else {
                if (wakeLock!!.isHeld) {
                    wakeLock!!.release()
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }


    //////---------------------------logout function----------------//////////////
    private fun logout() {
        val sharedPref = getSharedPreferences("MyPref", AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPref?.edit()
        editor?.putString("username", "")
        editor?.putString("password", "")
        editor?.apply()
        Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }


//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.search_navbar, menu)
//        menu.on
////        val menuItem = menu!!.findItem(R.id.action_search)
//        return super.onCreateOptionsMenu(menu)
//    }

}

