package com.come.restaurants.printer.pairing

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.come.restaurants.base.MVP
import com.come.restaurants.printer.domain.model.Printer


class BtPairingPresenter : MVP.Presenter<BtPairingPresenter.View> {

    lateinit var view : View
    private val devicesList: MutableList<Printer> = mutableListOf()

    private val btReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val action = intent.action
            if(BluetoothDevice.ACTION_FOUND.equals(action)) {
                val device = intent
                        .getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                devicesList.add(Printer(device.name, device.address))
            }
        }

    }

    interface View : MVP.View {
        fun showList(printers : List<Printer>)
        fun setReceiver(receiver: BroadcastReceiver, filter: IntentFilter)
        fun unsetReceiver(receiver: BroadcastReceiver)
        fun turnOnBtMessage()
    }

    override fun init() {
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        this.view.setReceiver(btReceiver, filter)
        requestPrinters()
    }

    fun finish() {
        this.view.unsetReceiver(btReceiver)
    }

    override fun setView(view: MVP.View) {
        this.view = view as View
    }

    private fun requestPrinters() {
        val btAdapter = BluetoothAdapter.getDefaultAdapter()
        btAdapter.startDiscovery()
        this.show(devicesList)
    }

    private fun show(printers: List<Printer>) {
        this.view.showList(printers)
    }
}