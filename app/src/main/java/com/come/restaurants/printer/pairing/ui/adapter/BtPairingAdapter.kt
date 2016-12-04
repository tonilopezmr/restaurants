package com.come.restaurants.printer.pairing.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.come.restaurants.R
import com.come.restaurants.printer.domain.model.Printer
import kotlinx.android.synthetic.main.printer_list_item.view.*
import java.util.*


class BtPairingAdapter() : RecyclerView.Adapter<BtPairingAdapter.ListViewHolder>() {

    val printerList: MutableList<Printer> = ArrayList()

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
            val toast = Toast.makeText(it.context, printerList[position].name, Toast.LENGTH_LONG)
            toast.show()
        }
    }

    fun addAll(printers: List<Printer>) {
        this.printerList.addAll(printers)
    }

    class ListViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bindPrinter(printer: Printer) {
            with(printer) {
            itemView.printerNameText.text = printer.name
            }
        }
    }

}