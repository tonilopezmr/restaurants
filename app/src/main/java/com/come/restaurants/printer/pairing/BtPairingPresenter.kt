package com.come.restaurants.printer.pairing

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v4.app.ActivityCompat
import android.util.Log
import com.come.restaurants.base.MVP
import com.come.restaurants.printer.pairing.ui.BtPairingActivity


class BtPairingPresenter : MVP.Presenter<BtPairingPresenter.View> {

    private val TAG = "BtPairing"

    private lateinit var view : View
    private lateinit var btAdapter: BluetoothAdapter
    private val devicesList: MutableList<BluetoothDevice> = mutableListOf()

    private val btReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val action = intent.action
            if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                view.showProgressDialog()
            } else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Log.d(TAG, "Showing list with ${devicesList.size} items")
                view.showList(devicesList)
            } else if(BluetoothDevice.ACTION_FOUND.equals(action)) {
                val device = intent
                        .getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                Log.i(TAG, "Found device: ${device.name} with MAC ${device.address}")
                devicesList.add(device)
            }
        }

    }

    interface View : MVP.View {
        fun showList(printers : List<BluetoothDevice>)
        fun setReceiver(receiver: BroadcastReceiver, filter: IntentFilter)
        fun unsetReceiver(receiver: BroadcastReceiver)
        fun turnOnBtMessage()
        fun showProgressDialog()
        fun requestPermission()
        fun showPermissionError()
        fun hasPermission(): Boolean
    }

    override fun init() {
        this.view.initUi()
        requestPrinters()
    }

    fun finish() {
        this.view.unsetReceiver(btReceiver)
    }

    override fun setView(view: MVP.View) {
        this.view = view as View
    }

    private fun requestPrinters() {
        val filter = IntentFilter()
        filter.addAction(BluetoothDevice.ACTION_FOUND)
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        this.view.setReceiver(btReceiver, filter)

        btAdapter = BluetoothAdapter.getDefaultAdapter()
        if(!btAdapter.isEnabled) {
            this.view.turnOnBtMessage()
        }
        if(this.view.hasPermission()) {
            this.doDiscovery()
        } else {
            this.view.requestPermission()
        }
    }

    fun doDiscovery() {
        btAdapter.startDiscovery()
    }

    fun cancelDiscovery() {
        btAdapter.cancelDiscovery()
    }
}