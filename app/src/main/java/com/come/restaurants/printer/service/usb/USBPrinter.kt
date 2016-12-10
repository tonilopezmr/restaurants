package com.come.restaurants.printer.service.usb

import android.content.Context
import android.hardware.usb.UsbDevice
import android.os.Handler
import com.come.restaurants.printer.service.Printer

object USBPrinter : Printer() {

  lateinit private var usbService: USBService
  lateinit private var device: UsbDevice

  fun connect(device: UsbDevice, context: Context, messageHandler: Handler) {
    this.usbService = USBService("com.come.printer.usb", context, messageHandler)

    if (!usbService.isHasPermission(device)) {
      usbService.getPermission(device) //TODO how to know if the permissions are not granted
    }

    this.device = device
  }

  override fun disconnect() {
    usbService.close()
  }

  override fun write(data: ByteArray) {
    usbService.sendByte(data, device)
  }
}