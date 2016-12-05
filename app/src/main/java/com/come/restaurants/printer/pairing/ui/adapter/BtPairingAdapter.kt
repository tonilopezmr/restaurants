package com.come.restaurants.printer.pairing.ui.adapter

import android.bluetooth.BluetoothDevice
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.come.restaurants.R
import kotlinx.android.synthetic.main.printer_list_item.view.*
import java.util.*


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
                val method = device.javaClass.getMethod("removeBond")
                val result = method.invoke(device)
                Log.d(TAG, "Device was unpaired correctly: $result")
            } else {
                val method = device.javaClass.getMethod("createBond")
                val result = method.invoke(device)
                Log.d(TAG, "Device was paired correctly: $result")
            }
        }
    }

    fun addAll(printers: List<BluetoothDevice>) {
        this.printerList.addAll(printers)
        notifyDataSetChanged()
    }

    class ListViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bindPrinter(printer: BluetoothDevice) {
            with(printer) {
            itemView.printerNameText.text = printer.name
            }
        }
    }

}