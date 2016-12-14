package com.come.restaurants.printer.pairing.bluetooth

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.come.restaurants.R
import com.come.restaurants.printer.pairing.ChoosePairingActivity

class BluetoothDisconnectedReceiver : BroadcastReceiver() {
  override fun onReceive(context: Context?, intent: Intent?) {
    var action = intent?.action
    when(action) {
      BluetoothDevice.ACTION_ACL_DISCONNECTED -> {
        Toast.makeText(context, context?.getString(R.string.permission_failure), Toast.LENGTH_LONG).show()
        var startPrinterPairing : Intent = Intent(context, ChoosePairingActivity::class.java)
        context?.startActivity(startPrinterPairing)
      }
    }
  }
}