package com.come.restaurants.printer.pairing.bluetooth.adapter

import android.bluetooth.BluetoothDevice
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.come.restaurants.R
import kotlinx.android.synthetic.main.printer_list_item.view.*
import java.util.ArrayList


class BluetoothDeviceAdapter(private val onItemClick: (BluetoothDevice) -> Unit)
  : RecyclerView.Adapter<BluetoothDeviceAdapter.ListViewHolder>() {

  private val printerList: MutableList<BluetoothDevice> = ArrayList()

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
    val device = printerList[position]
    holder.bindPrinter(device)
    holder.itemView.setOnClickListener { it ->
      onItemClick(device)
    }
  }

  fun addAll(printers: List<BluetoothDevice>) {
    this.printerList.clear()
    this.printerList.addAll(printers)
    notifyDataSetChanged()
  }

  fun resetList() {
    this.printerList.clear()
    notifyDataSetChanged()
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