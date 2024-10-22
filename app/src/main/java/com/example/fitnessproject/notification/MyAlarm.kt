package com.example.fitnessproject.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.fitnessproject.R

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        try {
            // Retrieve the title and message from the Intent extras
            val title = intent.getStringExtra("title") ?: "Fitness Project"
            val message = intent.getStringExtra("message") ?: "You have finished your workout!"

            // Show the notification with the dynamic text
            showNotification(context, title, message)
            Log.d("notify", "Notification sent")
        } catch (ex: Exception) {
            ex.localizedMessage?.let { Log.d("notify", it) }
        }
    }
}

private fun showNotification(context: Context, title: String, desc: String) {
    val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val channelId = "message_channel"
    val channelName = "message_name"

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
        manager.createNotificationChannel(channel)
    }

    val builder = NotificationCompat.Builder(context, channelId)
        .setContentTitle(title)
        .setContentText(desc)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setAutoCancel(true)

    manager.notify(1, builder.build())
}

fun setAlarm(context: Context, title: String, message: String) {
    val timeSec = System.currentTimeMillis() // Set alarm for 10 seconds from now (for testing)
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java)
    intent.putExtra("title", title)
    intent.putExtra("message", message)
    val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    alarmManager.set(AlarmManager.RTC_WAKEUP, timeSec, pendingIntent)
}
