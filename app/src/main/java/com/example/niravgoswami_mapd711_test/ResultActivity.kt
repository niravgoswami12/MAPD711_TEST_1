package com.example.niravgoswami_mapd711_test

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.EditText
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        title = getString(R.string.app_title)
        sharedPreferences = this.getSharedPreferences("com.example.niravgoswami_mapd711_test", Context.MODE_PRIVATE)
        setData()

    }
    private fun setData(){
        val vehicleSizeVal = sharedPreferences.getString(VEHICLE_SIZE,"")
        val vehicleSizeTV = findViewById<TextView>(R.id.vehicle_size_val)
        vehicleSizeTV.text = vehicleSizeVal

        val distanceVal = sharedPreferences.getFloat(DISTANCE, 0F)
        val distanceTV = findViewById<TextView>(R.id.distance_val)
        distanceTV.text = "$distanceVal Km"

        val timeOfDayVal = sharedPreferences.getString(TIME_OF_DAY,"")
        val timeOfDayTV = findViewById<TextView>(R.id.time_of_day_val)
        timeOfDayTV.text = timeOfDayVal

        val directionVal = sharedPreferences.getString(DIRECTION,"")
        val directionTV = findViewById<TextView>(R.id.direction_val)
        directionTV.text = directionVal

        val transponderVal = sharedPreferences.getBoolean(TRANSPONDER,false)
        val transponderTV = findViewById<TextView>(R.id.transponder_val)
        transponderTV.text = if(transponderVal)  "Yes" else "No"

        val tollVal = sharedPreferences.getString(TOLL,"")
        val tollTV = findViewById<TextView>(R.id.toll_val)
        tollTV.text = "$$tollVal"

        val loadOnlineCalc = sharedPreferences.getBoolean(LOAD_ONLINE_CALC,false)
        if(loadOnlineCalc){
            setWebView()
        }

    }
    private fun setWebView(){
        val wv = findViewById<View>(R.id.web_view_container) as WebView
        wv.visibility = View.VISIBLE
        wv.settings.javaScriptEnabled = true
        wv.loadUrl("https://www.407etr.com/en/tolls/tolls/toll-calculator.html")
    }
}