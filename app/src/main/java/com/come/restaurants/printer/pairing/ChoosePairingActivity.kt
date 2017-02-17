package com.come.restaurants.printer.pairing

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.come.restaurants.R
import com.come.restaurants.printer.pairing.bluetooth.BluetoothPairingActivity
import com.come.restaurants.printer.pairing.usb.USBPairingActivity
import com.come.restaurants.printer.service.PrinterFactory
import kotlinx.android.synthetic.main.activity_pairing.*

class ChoosePairingActivity : AppCompatActivity() {

  companion object {
    fun launch(activity: Activity) {
      activity.startActivity(Intent(activity, ChoosePairingActivity::class.java))
      activity.finish()
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_pairing)

    PrinterFactory.getPrinter().disconnect()

    bluetoothButton.setOnClickListener {
      PrinterFactory.isBluetoothPrinter = true
      BluetoothPairingActivity.launch(this@ChoosePairingActivity)
    }
    usbButton.setOnClickListener {
      PrinterFactory.isBluetoothPrinter = false
      USBPairingActivity.launch(this@ChoosePairingActivity)
    }
  }
}
