package com.example.fitnessproject.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.fitnessproject.R
import com.example.fitnessproject.navigation.Screen

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        try{
            showNotification(context,"Fitness Project", "You are finished your workout")
        }catch (ex : Exception){}
    }
}

private fun showNotification(context: Context, title: String,desc : String) {
val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val channelId="message_channel"
    val channelName="message_name"
    if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
        manager.createNotificationChannel(channel)
    }
    val builder = NotificationCompat.Builder(context, channelId)
        .setContentTitle(title)
        .setContentText(desc)
        .setSmallIcon(R.drawable.ic_launcher_foreground)

    manager.notify(1, builder.build())

}



// move it

fun setAlarm(context: Context) {
    val timeSec=System.currentTimeMillis()
    val alarmManager=context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    val intent=Intent(context, Screen.HomeScreen::class.java)
    val pendingIntent= PendingIntent.getBroadcast(context,0,intent, PendingIntent.FLAG_IMMUTABLE)
    alarmManager.set(AlarmManager.RTC_WAKEUP, timeSec,pendingIntent)
}
