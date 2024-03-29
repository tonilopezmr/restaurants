package com.come.restaurants.printer.pairing.usb

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.hardware.usb.UsbManager
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import com.come.restaurants.R
import com.come.restaurants.order.list.OrderListActivity
import com.come.restaurants.printer.service.usb.USBPrinter
import com.come.restaurants.printer.service.usb.USBService
import kotlinx.android.synthetic.main.activity_list.*

class USBPairingActivity : AppCompatActivity() {

  companion object {
    fun launch(activity: Activity) {
      activity.startActivity(Intent(activity, USBPairingActivity::class.java))
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_list)

    actionBar?.setDisplayHomeAsUpEnabled(true)

    val mHandler = object : Handler() {
      override fun handleMessage(msg: Message) {
        when (msg.what) {
          USBService.USB_CONNECTED -> {
            OrderListActivity.launch(this@USBPairingActivity)
          }
        }
      }
    }

    val adapter = USBDeviceAdapter({
      USBPrinter.connect(it, this@USBPairingActivity.applicationContext, mHandler)
    })
    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(this)
    emptyCase.text = String.format(getString(R.string.there_are_not), getString(R.string.printers))

    val usbManager = this.getSystemService(Context.USB_SERVICE) as UsbManager
    val deviceList = usbManager.deviceList

    if (deviceList.isEmpty()) {
      emptyCase.visibility = VISIBLE
    } else {
      emptyCase.visibility = GONE
      adapter.addAll(deviceList.values.map { it })
    }
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      android.R.id.home -> {
        val upIntent = NavUtils.getParentActivityIntent(this)
        NavUtils.navigateUpTo(this, upIntent)
      }
    }

    return true
  }
}
