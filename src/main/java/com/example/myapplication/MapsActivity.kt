package com.example.myapplication

import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMapsBinding
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.annotations.AfterPermissionGranted
import android.graphics.Color
import android.widget.CompoundButton


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, SensorEventListener {
    var initialStepCount = -1
    var caloriesBurnt = 0
    var distanceTraveled = 0.0
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.d("TAG", "onAccuracyChanged: Sensor: $sensor; accuracy: $accuracy")
    }

    override fun onSensorChanged(sensorEvent: SensorEvent?) {
        sensorEvent ?: return
        sensorEvent.values.firstOrNull()?.let {
            if (initialStepCount == -1) {
                initialStepCount = it.toInt()
            }
            val currentNumberOfStepCount = it.toInt() - initialStepCount
            caloriesBurnt = (0.04 * currentNumberOfStepCount).toInt()
            distanceTraveled = (0.0005f * currentNumberOfStepCount).toDouble()
            updateAllDisplayText(currentNumberOfStepCount,distanceTraveled,caloriesBurnt)
            Log.d("TAG", "Steps count: $currentNumberOfStepCount ")
        }
    }
    val locationCallback = object: LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)
            locationResult ?: return
            locationResult.locations.forEach {
                Log.d("TAG", "New location got: (${it.latitude}, ${it.longitude})")
            }
        }
    }


    @SuppressLint("MissingPermission")
    @AfterPermissionGranted(REQUEST_CODE_FINE_LOCATION)
    private fun setupLocationChangeListener() {
        if (EasyPermissions.hasPermissions(this, ACCESS_FINE_LOCATION)) {
            val locationRequest = LocationRequest()
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            locationRequest.interval = 5000 // 5000ms (5s)
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        } else {
            EasyPermissions.requestPermissions(
                host = this,
                rationale = "For showing your current location on the map.",
                requestCode = REQUEST_CODE_FINE_LOCATION,
                perms = *arrayOf(ACCESS_FINE_LOCATION)
            )
        }
    }

    private lateinit var binding: ActivityMapsBinding

    private lateinit var mMap: GoogleMap

    companion object {
        private const val KEY_SHARED_PREFERENCE = "com.rwRunTrackingApp.sharedPreferences"
        private const val KEY_IS_TRACKING = "com.rwRunTrackingApp.isTracking"

        private const val REQUEST_CODE_FINE_LOCATION = 1
        private const val REQUEST_CODE_ACTIVITY_RECOGNITION = 2
    }
    fun setupStepCounterListener() {
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        stepCounterSensor ?: return
        sensorManager.registerListener(this@MapsActivity, stepCounterSensor, SensorManager.SENSOR_DELAY_FASTEST)
    }

    private var isTracking: Boolean
        get() = this.getSharedPreferences(KEY_SHARED_PREFERENCE, Context.MODE_PRIVATE).getBoolean(
            KEY_IS_TRACKING, false)
        set(value) = this.getSharedPreferences(KEY_SHARED_PREFERENCE, Context.MODE_PRIVATE).edit().putBoolean(
            KEY_IS_TRACKING, value).apply()
    private var darkMode: Boolean
        get() = this.getSharedPreferences(KEY_SHARED_PREFERENCE, Context.MODE_PRIVATE).getBoolean(
            KEY_IS_TRACKING, false)
        set(value) = this.getSharedPreferences(KEY_SHARED_PREFERENCE, Context.MODE_PRIVATE).edit().putBoolean(
            KEY_IS_TRACKING, value).apply()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        /* binding.startButton.setOnClickListener {
            mMap.clear()

            isTracking = true
            updateButtonStatus()

            updateAllDisplayText(0, 0f)

            startTracking()
        }
        binding.endButton.setOnClickListener { endButtonClicked() }

        updateButtonStatus()

        if (isTracking) {
            startTracking()
        }
    }*/
        binding.locationToggle.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mMap.clear()
                isTracking = true
                updateAllDisplayText(0, 0.0,0)
                startTracking()
            } else {
                endButtonClicked()
            }
        })

        binding.themeToggle.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                darkMode = true
                updateTheme()
            } else {
                darkMode = false
                updateTheme()
            }
        })

    }
    private fun updateButtonStatus() {
        //binding.startButton.isEnabled = !isTracking
        //binding.endButton.isEnabled = isTracking
        binding.locationToggle.setChecked(isTracking)
    }

    private fun updateTheme() {
        if (darkMode) {
            binding.rootLayout.setBackgroundColor(Color.rgb(33, 33, 33));
            binding.titleTextView.setTextColor(Color.rgb(255, 255, 255));
            binding.averagePaceTextView.setTextColor(Color.rgb(255, 255, 255));
            binding.numberOfStepTextView.setTextColor(Color.rgb(255, 255, 255));
            binding.totalDistanceTextView.setTextColor(Color.rgb(255, 255, 255));
            binding.totalCaloriesburntTextView.setTextColor(Color.rgb(255,255,255));
            binding.locationToggle.setTextColor(Color.rgb(255, 255, 255));
            binding.themeToggle.setTextColor(Color.rgb(255, 255, 255));
        } else {
            binding.rootLayout.setBackgroundColor(Color.rgb(255, 255, 255));
            binding.titleTextView.setTextColor(Color.rgb(0, 0, 0));
            binding.averagePaceTextView.setTextColor(Color.rgb(0, 0, 0));
            binding.numberOfStepTextView.setTextColor(Color.rgb(0, 0, 0));
            binding.totalDistanceTextView.setTextColor(Color.rgb(0, 0, 0));
            binding.totalCaloriesburntTextView.setTextColor(Color.rgb(0, 0, 0));
            binding.locationToggle.setTextColor(Color.rgb(0, 0, 0));
            binding.themeToggle.setTextColor(Color.rgb(0, 0, 0));
        }
    }

    private fun updateAllDisplayText(stepCount: Int, totalDistanceTravelled: Double, caloriesBurnt: Int) {
        binding.numberOfStepTextView.text =  String.format("Step count: %d", stepCount)
        binding.totalDistanceTextView.text = String.format("Total distance: %.2f Miles", totalDistanceTravelled)
        binding.totalCaloriesburntTextView.text = String.format("Total calories burnt: %d KCal", caloriesBurnt)

        val averagePace = if (stepCount != 0) totalDistanceTravelled / stepCount.toDouble() else 0.0
        binding.averagePaceTextView.text = String.format("Average pace: %.2fm/ step", averagePace)
    }

    private fun endButtonClicked() {
        AlertDialog.Builder(this)
            .setTitle("Are you sure to stop tracking?")
            .setPositiveButton("Confirm") { _, _ ->
                isTracking = false
                updateButtonStatus()
                stopTracking()
            }.setNegativeButton("Cancel") { _, _ ->
                updateButtonStatus()
            }
            .create()
            .show()
    }

    @SuppressLint("CheckResult")
    private fun startTracking() {
        val isActivityRecognitionPermissionFree = Build.VERSION.SDK_INT < Build.VERSION_CODES.Q
        val isActivityRecognitionPermissionGranted = EasyPermissions.hasPermissions(this,
            ACTIVITY_RECOGNITION,
            ACCESS_FINE_LOCATION,
            ACCESS_COARSE_LOCATION)
        Log.d("TAG", "Is ACTIVITY_RECOGNITION permission granted $isActivityRecognitionPermissionGranted")
        if (isActivityRecognitionPermissionFree || isActivityRecognitionPermissionGranted) {
            setupStepCounterListener()
            setupLocationChangeListener()
        } else {
            EasyPermissions.requestPermissions(
                host = this,
                rationale = "For showing your step counts and calculate the average pace.",
                requestCode = REQUEST_CODE_ACTIVITY_RECOGNITION,
                perms = *arrayOf(ACTIVITY_RECOGNITION)
            )
        }
    }

    private fun stopTracking() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        sensorManager.unregisterListener(this, stepCounterSensor)
    }


    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        showUserLocation()
    }
    @SuppressLint("MissingPermission")
    @AfterPermissionGranted(REQUEST_CODE_FINE_LOCATION)
    private fun showUserLocation() {
        if (EasyPermissions.hasPermissions(this, ACCESS_FINE_LOCATION)) {
            mMap.isMyLocationEnabled = true
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(
                host = this,
                rationale = "For showing your current location on the map.",
                requestCode = REQUEST_CODE_FINE_LOCATION,
                perms = *arrayOf(ACCESS_FINE_LOCATION)
            )
        }
    }
}