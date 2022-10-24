package com.example.niravgoswami_mapd711_test

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.roundToLong


const val DISTANCE = "distance"
const val VEHICLE_SIZE = "vehicle_size"
const val TIME_OF_DAY = "time_of_day"
const val DIRECTION = "direction"
const val TRANSPONDER = "transponder"
const val LOAD_ONLINE_CALC = "load_online_calc"
const val TOLL = "toll"


class InputActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private var timeOfDayArray = arrayOf(
        "Weekday 6:00 AM to 7:00 AM",
        "Weekday 7:00 AM to 9:30 AM",
        "Weekday 9:30 AM to 10:30 AM",
        "Weekday 10:30 AM to 2:30 PM",
        "Weekday 2:30 PM to 3:30 PM",
        "Weekday 3:30 PM to 6:00 PM",
        "Weekday 6:00 PM to 7:00 PM",
        "Weekday 7:00 PM to 12:00 AM",
        "Weekends & Holidays 12:00 AM to 11:00 AM",
        "Weekends & Holidays 11:00 AM to 7:00 PM",
        "Weekends & Holidays 7:00 PM to 12:00 M"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)
        title = getString(R.string.app_title)
        sharedPreferences = this.getSharedPreferences("com.example.niravgoswami_mapd711_test", Context.MODE_PRIVATE)
        calculateBtnHandler()
        initializeTimeOfDayAdapterSpinner()
    }

    private fun calculateBtnHandler() {
        val btn = findViewById<Button>(R.id.calculate_btn)
        btn.setOnClickListener{
            val distanceInput = findViewById<EditText>(R.id.distance_et)
            val distanceValue = if(!distanceInput.text.toString().isNullOrEmpty()) {
                distanceInput.text.toString().toFloat()
            } else{
                null
            }
            if(distanceValue != null &&  distanceValue < 24){
                val vehicleSizeInput = findViewById<RadioGroup>(R.id.vehicle_size_rg)
                val selectedVehicleSizeId = vehicleSizeInput.checkedRadioButtonId
                val selectedVehicleSizeRb = findViewById<RadioButton>(selectedVehicleSizeId)
                val selectedVehicleSize = selectedVehicleSizeRb.text.toString()

                val timeOfDayInput = findViewById<Spinner>(R.id.time_of_day_spinner)
                val timeOfDay = timeOfDayInput.selectedItem.toString()

                val directionInput = findViewById<RadioGroup>(R.id.direction_rg)
                val selectedDirectionId = directionInput.checkedRadioButtonId
                val selectedDirectionRb = findViewById<RadioButton>(selectedDirectionId)
                val selectedDirection = selectedDirectionRb.text.toString()

                val transponderInput = findViewById<Switch>(R.id.transponder_switch)
                val transponder = transponderInput.isChecked

                val loadOnlineCalcInput = findViewById<CheckBox>(R.id.online_calc_cb)
                val loadOnlineCalc = loadOnlineCalcInput.isChecked

                sharedPreferences.edit().putFloat(DISTANCE, distanceValue).apply()
                sharedPreferences.edit().putString(VEHICLE_SIZE, selectedVehicleSize).apply()
                sharedPreferences.edit().putString(TIME_OF_DAY, timeOfDay).apply()
                sharedPreferences.edit().putString(DIRECTION, selectedDirection).apply()
                sharedPreferences.edit().putBoolean(TRANSPONDER, transponder).apply()
                sharedPreferences.edit().putBoolean(LOAD_ONLINE_CALC, loadOnlineCalc).apply()
                val tollVal = calculateToll(distanceValue, selectedVehicleSize, timeOfDay, selectedDirection, transponder)
                sharedPreferences.edit().putString(TOLL, tollVal).apply()
                val intentActivity = Intent(applicationContext, ResultActivity::class.java)
                startActivity(intentActivity)
            }else{
                distanceInput.isEnabled = true
            }
        }

    }

    private fun calculateToll(distance: Float, vehicleSize: String, timeOfDay: String, direction: String, transponder: Boolean): String {
        val tollPerKm = 134.58
        val tripCharge = 1
        val cameraCharge = if(transponder) {
            0
        } else {
            2
        }

        var toll = (((distance * tollPerKm)/100) + tripCharge + cameraCharge)
        return String.format("%.2f", toll)

    }
    private fun initializeTimeOfDayAdapterSpinner() {
        val sp = findViewById<Spinner>(R.id.time_of_day_spinner)
        sp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            }

        }
        val timeOfDayAdapter: ArrayAdapter<*> = ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_item, timeOfDayArray)
        timeOfDayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Setting data on the Spinner
        sp.adapter = timeOfDayAdapter
    }
}