package com.example.healthapp

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.viewModels
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.example.healthapp.data.model.LoggedInUser
import com.example.healthapp.databinding.ActivityNavBinding
import com.example.healthapp.databinding.FragmentChatBinding
import com.example.healthapp.databinding.FragmentMeasureBinding
import com.example.healthapp.databinding.NavHeaderNavBinding
import com.example.healthapp.ui.chat.ChatFragmentFragment
import com.example.healthapp.ui.gonio.GonioFragment
import com.example.healthapp.ui.measure.MeasureFragment

class NavActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityNavBinding
    private val dataModel: DataModel by viewModels()
    //private val historyDataModel: HistoryDataModel by viewModels()

    private val CHANNEL_ID = "channelID"
    private val NOTIFICATION_ID = 101

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityNavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarNav.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val viewHeader = navView.getHeaderView(0)
        val navViewHeaderBinding: NavHeaderNavBinding = NavHeaderNavBinding.bind(viewHeader)
        val navController = findNavController(R.id.nav_host_fragment_content_nav)
        var userID = "1"
        //val userFName = intent.getStringExtra("userFName").toString()
        //val cardNumber = intent.getStringExtra("cardNumber").toString()
        val userFName = "Фамилия Имя"
        val cardNumber = "000001"

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_about, R.id.nav_gonio, R.id.nav_start
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navViewHeaderBinding.userName = LoggedInUser(userID, userFName, cardNumber)

        createNotificationChannel()

        val notBtn: Button = findViewById(R.id.notifyBtn)

        notBtn.setOnClickListener {
            sendNotification()
        }
    }


    @SuppressLint("MissingPermission")
    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notify"
            val descriptionText = "Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel =  NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
    }
    @SuppressLint("MissingPermission")
    private fun sendNotification(){
        val intent = Intent(this, NavActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Напоминание")
            .setContentText("Необходимо провести измерение движения сустава")
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(this)){
            notify(NOTIFICATION_ID, builder.build())
        }
    }


    private fun openFrag(f: Fragment, idHolder: Int){
        supportFragmentManager
            .beginTransaction()
            .replace(idHolder, f).commit()
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.nav, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_nav)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}

