package com.come.restaurants.printer.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.util.Log
import com.come.restaurants.DI.DependencyInjector
import com.come.restaurants.R
import com.come.restaurants.order.domain.model.Order
import com.come.restaurants.order.domain.usecases.GetNewOrder
import com.come.restaurants.order.list.OrderListActivity


class PrinterQueueService : Service() {

  val TAG: String = javaClass.canonicalName

  companion object {

    val START_SERVICE = "start_Service"
    val CLOSE_SERVICE = "CLOSE_Service"
    val PRINT_ORDER = "print_order"
  }

  private lateinit var printerQueue: PrinterQueue
  private lateinit var notificationManager: NotificationManagerCompat

  override fun onCreate() {
    super.onCreate()
    Log.e(TAG, "start")

    this.printerQueue = DependencyInjector.printerQueue
  }

  override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

    val action = intent.getStringExtra("action")
    Log.e(TAG, "action received: " + action)
    when (action) {
      START_SERVICE -> {
        if (!printerQueue.alive) {
          printerQueue.start()
          DependencyInjector.getNewOrder().get(object : GetNewOrder.Callback {
            override fun error(exception: Exception) {

            }

            override fun orderReceived(order: Order) {
              Log.e(TAG, "order received: " + order)
              printerQueue.add(order)
            }

          })
          startNotification()
        }
      }
      PRINT_ORDER -> {
        val parcelableExtra = intent.getParcelableExtra<Order>("order")
        print(parcelableExtra!!)
      }
      CLOSE_SERVICE -> {
        printerQueue.close()
        PrinterFactory.getPrinter().disconnect()
        closeNotification()
      }
    }

    return START_STICKY
  }

  fun startNotification() {
    notificationManager = NotificationManagerCompat.from(this)

    val intent = PendingIntent.getActivity(this, 12, Intent(this, OrderListActivity::class.java), PendingIntent.FLAG_CANCEL_CURRENT)


    var notificationBuilder = NotificationCompat.Builder(this)

    notificationBuilder
        .setSmallIcon(R.mipmap.ic_launcher)
        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        .setUsesChronometer(true)
        .setContentIntent(intent)
        .setContentTitle("COME está funcionando")
        .setContentText("Por favor no desconecte la impresora")

    var notification = notificationBuilder.build()
    startForeground(11, notification)
    notificationManager.notify(11, notification)
  }

  fun closeNotification() {
    try {
      notificationManager.cancel(11)
    } catch (e: Exception) {
      e.printStackTrace()
    }
    stopForeground(true)
  }

  fun print(order: Order) {
    printerQueue.add(order)
  }


  override fun onBind(intent: Intent?): IBinder? {
    return null
  }


}