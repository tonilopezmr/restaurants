package com.come.restaurants.printer.pairing

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.come.restaurants.R
import com.come.restaurants.printer.pairing.bluetooth.BluetoothPairingActivity
import com.come.restaurants.printer.pairing.usb.USBPairingActivity
import com.come.restaurants.printer.service.PrinterFactory
import kotlinx.android.synthetic.main.activity_pairing.*

class ChoosePairingActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_pairing)

    PrinterFactory.getPrinter().disconnect()

    bluetoothButton.setOnClickListener {
      PrinterFactory.isBluetoothPrinter = true
      this@ChoosePairingActivity.startActivity(Intent(this@ChoosePairingActivity, BluetoothPairingActivity::class.java))
    }
    usbButton.setOnClickListener {
      PrinterFactory.isBluetoothPrinter = false
      this@ChoosePairingActivity.startActivity(Intent(this@ChoosePairingActivity, USBPairingActivity::class.java))
    }
  }
}
