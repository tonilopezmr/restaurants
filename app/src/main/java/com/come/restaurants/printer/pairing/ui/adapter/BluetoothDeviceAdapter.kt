package com.come.restaurants.printer.pairing.ui.adapter

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.os.Handler
import android.os.Message
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.come.restaurants.R
import com.come.restaurants.printer.pairing.ui.PairingPrinterActivity
import com.come.restaurants.printer.service.Printer
import kotlinx.android.synthetic.main.printer_list_item.view.*
import java.util.*
import com.come.restaurants.printer.service.bluetooth.BluetoothService
import com.come.restaurants.printer.service.bluetooth.PrinterBluetooth


class BluetoothDeviceAdapter() : RecyclerView.Adapter<BluetoothDeviceAdapter.ListViewHolder>() {

  private val TAG = "BtPairing"
  private var printer : Printer = PrinterBluetooth.getPrinter()
  val printerList: MutableList<BluetoothDevice> = ArrayList()

  override fun getItemCount(): Int {
    return printerList.size
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
    val view = LayoutInflater
        .from(parent.context)
        .inflate(R.layout.printer_list_item, parent, false)
    return ListViewHolder(view)
  }

  override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
    holder.bindPrinter(printerList[position])
    holder.itemView.setOnClickListener { it ->
      val device = printerList[position]
      /*if (device.bondState == BluetoothDevice.BOND_BONDED) {
        val result = unpairDevice(device)
        if (result) {
            Toast.makeText(it.context,
                "Device ${device.name} was unpaired correctly", Toast.LENGTH_SHORT)
                .show()
            (it.context as PairingPrinterActivity).finish()
        }
      } else {
      */
        val result = pairDevice(it.context, device)
      //}
    }
  }

  fun addAll(printers: List<BluetoothDevice>) {
    this.printerList.addAll(printers)
    notifyDataSetChanged()
  }

  private fun unpairDevice(device: BluetoothDevice): Boolean {
    val method = device.javaClass.getMethod("removeBond")
    return method.invoke(device) as Boolean
  }

  private fun pairDevice(context: Context, device: BluetoothDevice): Boolean {
    printer.connect(device, context, getHandler(context, device))
    return true //TODO WE ASSUME THAT IT GOES WELL
  }

  class ListViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    fun bindPrinter(printer: BluetoothDevice) {
      with(printer) {
        if (printer.name != null) {
          itemView.printerNameText.text = printer.name
        } else {
          itemView.printerNameText.text = itemView.context.getString(R.string.unknown_device)
        }
      }
    }
  }


  private fun getHandler(context : Context, device : BluetoothDevice) : Handler {
    val MESSAGE_STATE_CHANGE = 1
    val MESSAGE_READ = 2
    val MESSAGE_WRITE = 3
    val MESSAGE_DEVICE_NAME = 4
    val MESSAGE_TOAST = 5
    val MESSAGE_CONNECTION_LOST = 6
    val MESSAGE_UNABLE_CONNECT = 7

    @SuppressLint("HandlerLeak")
    val mHandler = object : Handler() {
      override fun handleMessage(msg: Message) {
        when (msg.what) {
          MESSAGE_STATE_CHANGE -> {
            when (msg.arg1) {
              BluetoothService.STATE_CONNECTED -> {
                Toast.makeText(context,
                    "Device ${device.name} was paired correctly", Toast.LENGTH_SHORT)
                    .show()
                (context as PairingPrinterActivity).finish()
              }
              BluetoothService.STATE_CONNECTING -> {

              }
              BluetoothService.STATE_LISTEN, BluetoothService.STATE_NONE -> {
              }
            }
          }
          MESSAGE_WRITE -> {
          }
          MESSAGE_READ -> {
          }
          MESSAGE_DEVICE_NAME -> {
            // save the connected device's name

          }
          MESSAGE_TOAST -> {}
          MESSAGE_CONNECTION_LOST    //蓝牙已断开连接
          -> {
          }
          MESSAGE_UNABLE_CONNECT     //无法连接设备
          -> {
          }
        }
      }
    }
    return mHandler
  }
}