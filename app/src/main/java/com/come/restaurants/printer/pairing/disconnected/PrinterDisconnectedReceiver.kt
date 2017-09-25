package com.come.restaurants.printer.pairing.disconnected

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.hardware.usb.UsbManager
import com.come.restaurants.restaurant.login.UserProvider

class PrinterDisconnectedReceiver : BroadcastReceiver() {

  override fun onReceive(context: Context, intent: Intent) {
    val action = intent.action
    if ((action == BluetoothDevice.ACTION_ACL_DISCONNECTED
        || action == UsbManager.ACTION_USB_DEVICE_DETACHED) && UserProvider.isLogged) {
      launchChoosePairingDevice(context, intent)
    }
  }

  private fun launchChoosePairingDevice(context: Context, intent: Intent) {
    val startPrinterPairing = Intent(context, PrinterDisconnectedActivity::class.java)
    startPrinterPairing.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(startPrinterPairing)
  }
}

