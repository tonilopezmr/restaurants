package com.come.restaurants.printer.pairing.usb

import android.hardware.usb.UsbDevice
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.come.restaurants.R
import com.come.restaurants.printer.pairing.bluetooth.adapter.BluetoothDeviceAdapter
import kotlinx.android.synthetic.main.printer_list_item.view.*

class USBDeviceAdapter(private val onclick: (UsbDevice) -> Unit)
  : RecyclerView.Adapter<BluetoothDeviceAdapter.ListViewHolder>() {

  private val list = mutableListOf<UsbDevice>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BluetoothDeviceAdapter.ListViewHolder {
    val view = LayoutInflater
        .from(parent.context)
        .inflate(R.layout.printer_list_item, parent, false)
    return BluetoothDeviceAdapter.ListViewHolder(view)
  }

  override fun onBindViewHolder(holder: BluetoothDeviceAdapter.ListViewHolder, position: Int) {
    val device = list[position]
    holder.itemView.printerNameText.text = String.format("%d %s", device.deviceId, device.deviceName)
    holder.itemView.setOnClickListener { onclick(device) }
  }

  override fun getItemCount(): Int {
    return list.size
  }

  fun addAll(list: List<UsbDevice>) {
    this.list.addAll(list)
    notifyDataSetChanged()
  }

}