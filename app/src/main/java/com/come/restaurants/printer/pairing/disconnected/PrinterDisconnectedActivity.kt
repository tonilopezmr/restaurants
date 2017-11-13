package com.come.restaurants.printer.pairing.disconnected

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.come.restaurants.R
import com.come.restaurants.printer.pairing.ChoosePairingActivity

class PrinterDisconnectedActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    displayDialog()
  }

  private fun displayDialog() {
    val alert = AlertDialog.Builder(this)
        .setTitle(R.string.error_printer_disconnected)
        .setMessage(R.string.prompt_reconnect_printer)
        .setCancelable(false)
        .setPositiveButton("Yes") { dialog, id ->
          startPairingActivity()
        }
        .setNegativeButton("No") { dialog, id ->
          startPairingActivity()
        }.create()
    alert.show()
  }

  private fun startPairingActivity() {
    val startPrinterPairing = Intent(this, ChoosePairingActivity::class.java)
    startPrinterPairing.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(startPrinterPairing)
    finish()
  }

}