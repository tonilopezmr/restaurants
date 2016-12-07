package com.come.restaurants.printer.pairing

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.come.restaurants.base.MVP


class PairingBluetoothPresenter : MVP.Presenter<PairingBluetoothPresenter.View> {

  private val TAG = javaClass.canonicalName

  private lateinit var view: View
  private lateinit var btAdapter: BluetoothAdapter
  private val devicesList: MutableList<BluetoothDevice> = mutableListOf()

  private val btReceiver: BroadcastReceiver = object : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
      val action = intent.action
      when (action) {
        BluetoothAdapter.ACTION_DISCOVERY_STARTED -> view.showProgressDialog()
        BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
          Log.d(TAG, "Showing list with ${devicesList.size} items")
          if (devicesList.isEmpty()) {
            view.emptyCase()
          } else{
            view.showList(devicesList)
          }
        }
        BluetoothDevice.ACTION_FOUND -> {
          val device = intent
              .getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
          Log.i(TAG, "Found device: ${device.name} with MAC ${device.address}")
          devicesList.add(device)
        }
      }
    }

  }

  interface View : MVP.View {
    fun showList(printers: List<BluetoothDevice>)
    fun setReceiver(receiver: BroadcastReceiver, filter: IntentFilter)
    fun unsetReceiver(receiver: BroadcastReceiver)
    fun turnOnBtMessage()
    fun showProgressDialog()
    fun requestPermission()
    fun showPermissionError()
    fun hasPermission(): Boolean
    fun emptyCase()
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

    var hasProblems = false

    if (!btAdapter.isEnabled) {
      this.view.turnOnBtMessage()
      hasProblems = true
    }

    if (!this.view.hasPermission()) {
      this.view.requestPermission()
      hasProblems = true
    }

    if(!hasProblems){
      doDiscovery()
    }
  }

  fun doDiscovery() {
    val boundedDevices = btAdapter.bondedDevices
    devicesList.addAll(boundedDevices)
    if (btAdapter.isDiscovering) {
      btAdapter.cancelDiscovery()
    }
    btAdapter.startDiscovery()
  }

  fun cancelDiscovery() {
    btAdapter.cancelDiscovery()
  }
}