package com.come.restaurants.order.detail

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.come.restaurants.base.MVP
import com.come.restaurants.order.domain.model.Order
import com.come.restaurants.order.domain.usecases.GetOrder
import com.come.restaurants.order.domain.usecases.PrintOrder
import com.come.restaurants.printer.service.bluetooth.PrinterBluetooth


class OrderDetailPresenter(val getOrder: GetOrder, val printOrder: PrintOrder) : MVP.Presenter<OrderDetailPresenter.View> {

  private val TAG = javaClass.canonicalName

  interface View : MVP.View {
    fun showDetails(details: Order)
    fun showFetchingError()
    fun showPrintError()
    fun showOrderPrinted()
  }

  lateinit private var view: View
  lateinit private var order: Order

  private var deviceConnected: Boolean = PrinterBluetooth.isConnected()
  private val btReceiver = object : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
      val action = intent.action
      if (BluetoothDevice.ACTION_ACL_CONNECTED == action) {
        Log.d(TAG, "Bluetooth device connected")
        deviceConnected = true
      } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED == action) {
        Log.d(TAG, "Bluetooth device disconnected")
        deviceConnected = false
      }

    }

  }

  override fun init() {
    view.initUi()
  }

  fun init(orderId: String) {
    this.init()
    this.requestDetails(orderId)
  }

  override fun setView(view: MVP.View) {
    this.view = view as View
  }

  private fun requestDetails(id: String) {
    this.getOrder.get(id, object : GetOrder.Callback {
      override fun orderReceived(receivedOrder: Order) {
        show(receivedOrder)
        order = receivedOrder
      }

      override fun error(exception: Exception) {
        view.showFetchingError()
      }

    })
  }

  private fun show(details: Order?) {
    if (details == null) {
      view.showFetchingError()
    } else {
      view.showDetails(details)
    }
  }

  fun print() {
    this.printOrder.print(order, object : PrintOrder.Callback {
      override fun orderPrinted(order: Order) {
        //TODO send that is printed
      }

      override fun error(exception: Exception) {
        view.showPrintError()
      }

    })

  }
}