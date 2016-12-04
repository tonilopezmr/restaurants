package com.come.restaurants.printer.pairing.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.come.restaurants.R
import com.come.restaurants.printer.domain.model.BtDevice
import kotlinx.android.synthetic.main.printer_list_item.view.*


class BtPairingAdapter() : RecyclerView.Adapter<BtPairingAdapter.ListViewHolder>() {

    lateinit var printerList: MutableList<BtDevice>

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
    }

    fun addAll(printers: List<BtDevice>) {
        this.printerList.addAll(printers)
    }

    class ListViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bindPrinter(printer: BtDevice) {
            with(printer) {
            itemView.printerNameText.text = printer.name
            }
        }
    }

}