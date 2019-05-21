package com.example.androidfcm.Service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.example.androidfcm.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

class MyFirebaseInstanceService : FirebaseMessagingService() {

    companion object{
        const val NOTIFICATION_CHANNEL = "com.example.androidfcm.test"
    }

    override fun onMessageReceived(p0: RemoteMessage?) {
        super.onMessageReceived(p0)

        if(p0?.data?.isEmpty() as Boolean)
            showNotification(p0?.notification?.title, p0?.notification?.body)
        else
            showNotification(p0.data.toString())
    }

    private fun showNotification(data : Map<String, String>)

    private fun showNotification(title : String?, body : String?){
        var notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            var notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL, "Notification", NotificationManager.IMPORTANCE_DEFAULT) as NotificationChannel

            notificationChannel.description = "EDMT Channel"
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.BLUE
            var arr = longArrayOf(0, 1000, 500, 1000)
            notificationChannel.vibrationPattern = arr
            notificationChannel.enableLights(true)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        var notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL) as NotificationCompat.Builder
        notificationBuilder.setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(body)
            .setContentInfo("Info");

            notificationManager.notify(Random.nextInt(), notificationBuilder.build())


    }

    override fun onNewToken(p0: String?) {
        super.onNewToken(p0)

        Log.d("Token", p0)
    }
}
