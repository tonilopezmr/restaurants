package com.come.restaurants.printer.service

import android.util.Log
import com.come.restaurants.printer.service.bluetooth.BluetoothPrinter
import com.come.restaurants.printer.service.usb.USBPrinter

object PrinterFactory {

  val TAG: String = javaClass.canonicalName

  var isBluetoothPrinter = false

  private fun getBluetoothPrinter(): BluetoothPrinter {
    return BluetoothPrinter.getPrinter()
  }

  private fun getUSBPrinter(): USBPrinter {
    return USBPrinter
  }

  public fun getPrinter(): Printer {
    if (isBluetoothPrinter) {
      Log.i(TAG, "Get bluetooth printer")
      return getBluetoothPrinter()
    } else {
      Log.i(TAG, "Get USB printer")
      return getUSBPrinter()
    }
  }

}