package com.come.restaurants.printer.service

import com.come.restaurants.printer.service.bluetooth.BluetoothPrinter
import com.come.restaurants.printer.service.usb.USBPrinter

object PrinterFactory {

  var isBluetoothPrinter = false

  private fun getBluetoothPrinter(): BluetoothPrinter {
    return BluetoothPrinter.getPrinter()
  }

  private fun getUSBPrinter(): USBPrinter {
    return USBPrinter
  }

  public fun getPrinter(): Printer {
    if (isBluetoothPrinter) {
      return getBluetoothPrinter()
    } else {
      return getUSBPrinter()
    }
  }

}