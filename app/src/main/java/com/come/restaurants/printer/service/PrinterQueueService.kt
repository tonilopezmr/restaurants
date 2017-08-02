package com.come.restaurants.printer.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import com.come.restaurants.R


class PrinterQueueService : Service() {

  companion object {

    val START_SERVICE = "start_Service"

  }

  override fun onCreate() {
    super.onCreate()
  }

  override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
    val notificationManager = NotificationManagerCompat.from(this)

    var notificationBuilder = NotificationCompat.Builder(this)

    notificationBuilder
        .setSmallIcon(R.mipmap.ic_launcher)
        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        .setUsesChronometer(true)
        .setContentTitle("COME está funcionando")
        .setContentText("Por favor no desconecte la impresora")

    var notification = notificationBuilder.build()
    startForeground(1, notification)
    notificationManager.notify(1, notification)

    return START_STICKY
  }

  override fun onBind(intent: Intent?): IBinder? {
    return null
  }


}