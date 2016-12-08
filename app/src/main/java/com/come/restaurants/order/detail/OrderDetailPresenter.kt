package com.come.restaurants.order.detail

import android.bluetooth.BluetoothAdapter
import com.come.restaurants.printer.service.bluetooth.PrinterBluetooth
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.come.restaurants.order.domain.model.Order
import com.come.restaurants.order.domain.usecases.GetOrder
import com.come.restaurants.base.MVP
import com.come.restaurants.order.domain.usecases.PrintOrder


class OrderDetailPresenter(val getOrder: GetOrder, val printOrder : PrintOrder) : MVP.Presenter<OrderDetailPresenter.View> {

    private val TAG = "OrderDetailPresenter"

    interface View : MVP.View {
        fun showDetails(details : Order)
        fun showFetchingError()
        fun showPrintError()
        fun showOrderPrinted()
        fun moveToPairingActivity()
        fun finishActivity()
        fun setReceiver(btReceiver: BroadcastReceiver, filter: IntentFilter)
    }

    lateinit private var view : View
    lateinit private var order : Order

    private var deviceConnected : Boolean = PrinterBluetooth.isConnected()
    private val btReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val action = intent.action
            val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
            if(BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
                Log.d(TAG, "Bluetooth device connected")
                deviceConnected = true
            } else if(BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
                Log.d(TAG, "Bluetooth device disconnected")
                deviceConnected = false
            }

        }

    }

    override fun init() {
        val filter = IntentFilter()
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
        this.view.setReceiver(btReceiver, filter)
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
        if(details == null) {
            view.showFetchingError()
        } else {
            view.showDetails(details)
        }
    }

    fun print() {
        if(!deviceConnected) {
            this.view.moveToPairingActivity()
        } else {
            this.printOrder.print(order, object : PrintOrder.Callback {
                override fun orderPrinted(order: Order) {
                    view.finishActivity()
                }

                override fun error(exception: Exception) {
                    view.showPrintError()
                }

            })
        }
    }
}