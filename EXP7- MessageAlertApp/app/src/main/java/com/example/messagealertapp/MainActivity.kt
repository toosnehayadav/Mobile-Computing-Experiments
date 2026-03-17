package com.example.messagealertapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Request notification permission (IMPORTANT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
        }

        createNotificationChannel()

        setContent {
            NotificationUI(this)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "MyChannel"
            val descriptionText = "Channel for notifications"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("channel_id", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}

@Composable
fun NotificationUI(context: Context) {

    var title by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var interval by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {

        Text(text = "Notification Alert App", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            label = { Text("Message") }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = interval,
            onValueChange = { interval = it },
            label = { Text("Time interval (seconds)") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            showNotification(context, title, message)
        }) {
            Text("FIRE")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            NotificationManagerCompat.from(context).cancel(1)        }) {
            Text("Cool Down Last")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {
            NotificationManagerCompat.from(context).cancelAll()
        }) {
            Text("Cool Down All")
        }
    }
}


fun showNotification(context: Context, title: String, message: String) {

    // ✅ Permission check
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
    }

    val builder = NotificationCompat.Builder(context, "channel_id")
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle(title.ifEmpty { "Default Title" })
        .setContentText(message.ifEmpty { "Default Message" })
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)

    NotificationManagerCompat.from(context).notify(1, builder.build())
}