package com.example.gpsapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GPSApp()
        }
    }
}

@Composable
fun GPSApp() {

    var locationText by remember {
        mutableStateOf("Location will appear here")
    }

    val context = LocalContext.current

    val fusedLocationClient =
        LocationServices.getFusedLocationProviderClient(context)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(text = locationText, fontSize = 18.sp)

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {

                // Check permission
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                    ActivityCompat.requestPermissions(
                        context as MainActivity,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        1
                    )
                    return@Button
                }

                // Get location
                fusedLocationClient.getCurrentLocation(
                    com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY,
                    null
                ).addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        locationText =
                            "Latitude: ${location.latitude}\nLongitude: ${location.longitude}"
                    } else {
                        locationText = "Location not available"
                    }
                }

            }) {
                Text("Get Location")
            }
        }
    }
}