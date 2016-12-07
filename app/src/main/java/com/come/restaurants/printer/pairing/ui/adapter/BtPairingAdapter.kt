package com.come.restaurants.printer.pairing.ui.adapter

import android.bluetooth.BluetoothDevice
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.come.restaurants.R
import com.come.restaurants.printer.pairing.ui.BtPairingActivity
import kotlinx.android.synthetic.main.printer_list_item.view.*
import java.util.ArrayList


class BtPairingAdapter() : RecyclerView.Adapter<BtPairingAdapter.ListViewHolder>() {

  private val TAG = "BtPairing"

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
      if (device.bondState == BluetoothDevice.BOND_BONDED) {
        val result = unpairDevice(device)
        if (result) {
          Toast.makeText(it.context,
              "Device ${device.name} was unpaired correctly", Toast.LENGTH_SHORT)
              .show()
          (it.context as BtPairingActivity).finish()
        }
      } else {
        val result = pairDevice(device)
        if (result) {
          Toast.makeText(it.context,
              "Device ${device.name} was paired correctly", Toast.LENGTH_SHORT)
              .show()
          (it.context as BtPairingActivity).finish()
        }
      }
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

  private fun pairDevice(device: BluetoothDevice): Boolean {
    val method = device.javaClass.getMethod("createBond")
    return method.invoke(device) as Boolean
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

}