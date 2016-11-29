package com.come.restaurants.ui.list.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.come.restaurants.R
import com.come.restaurants.order.Order
import kotlinx.android.synthetic.main.order_detail.view.*
import java.util.*

class OrderListAdapter() : RecyclerView.Adapter<OrderListAdapter.ListViewHolder>() {
    val orderList = ArrayList<Order>()

    override fun getItemCount(): Int = orderList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bindOrder(orderList[position], position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.order_detail, parent, false)
        return ListViewHolder(view)
    }

    fun addAll(orderList: List<Order>) {
        this.orderList.addAll(orderList)
        notifyDataSetChanged()
    }

    class ListViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bindOrder(order: Order, position: Int) {
            with(order) {
                itemView.orderNumberText.text = "$position"
                itemView.orderHourText.text = "${Date(order.timestamp).hours}:${Date(order.timestamp).minutes}"
                itemView.totalPriceText.text = "${order.getPrice()}€"
                itemView.orderPlatesText.text = orderLines.fold("", {total, current ->
                    total.plus("${current.quantity}x${current.plate.name}\n")} )
            }
        }
    }

}