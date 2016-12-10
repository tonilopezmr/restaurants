package com.come.restaurants.printer.pairing

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.come.restaurants.R
import com.come.restaurants.printer.pairing.ui.PairingPrinterActivity
import com.come.restaurants.printer.pairing.usb.USBPairingActivity
import com.come.restaurants.printer.service.PrinterFactory
import kotlinx.android.synthetic.main.activity_pairing.*

class PairingActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_pairing)

    PrinterFactory.getPrinter().disconnect()

    bluetoothButton.setOnClickListener {
      PrinterFactory.isBluetoothPrinter = true
      this@PairingActivity.startActivity(Intent(this@PairingActivity, PairingPrinterActivity::class.java))
    }
    usbButton.setOnClickListener {
      PrinterFactory.isBluetoothPrinter = false
      this@PairingActivity.startActivity(Intent(this@PairingActivity, USBPairingActivity::class.java))
    }
  }
}
