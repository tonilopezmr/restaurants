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
    lateinit var btAdapter : BluetoothAdapter

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
        fun bluetoothNotSupportedMessage()
        fun requestEnableBluetooth(btEnableIntent: Intent)
    }

    override fun init() {
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        this.view.setReceiver(btReceiver, filter)
        requestDevices()
    }

    fun finish() {
        this.view.unsetReceiver(btReceiver)
    }

    override fun setView(view: MVP.View) {
        this.view = view as View
    }

    private fun requestDevices() {
        btAdapter = BluetoothAdapter.getDefaultAdapter()
        if(btAdapter == null) {
            this.view.bluetoothNotSupportedMessage()
        }
        else if(!btAdapter.isEnabled) {
            enableBluetooth()
        }
        else {
            startDiscovery()
        }
    }

    fun enableBluetooth() {
        val enableBluetoothIntent : Intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        this.view.requestEnableBluetooth(enableBluetoothIntent)
    }

    fun startDiscovery() {
        btAdapter.startDiscovery()
        this.show(devicesList)
    }

    private fun show(printers: List<Printer>) {
        this.view.showList(printers)
    }
}