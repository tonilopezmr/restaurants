package com.come.restaurants.order.list.ui.adapter

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.support.v7.util.SortedList
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.util.SortedListAdapterCallback
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.come.restaurants.R
import com.come.restaurants.order.domain.model.Order
import com.come.restaurants.order.detail.OrderDetailActivity
import kotlinx.android.synthetic.main.order_detail.view.*
import java.util.*

class OrderListAdapter() : RecyclerView.Adapter<OrderListAdapter.ListViewHolder>() {
    lateinit var orderList : SortedList<Order>

    init {
        orderList  = SortedList<Order>(Order::class.java, object : SortedListAdapterCallback<Order>(this) {
            override fun compare(o1: Order, o2: Order): Int {
                return o1.timestamp.toInt().minus(o2.timestamp.toInt())
            }

            override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
                return oldItem.equals(newItem)
            }

            override fun areItemsTheSame(item1: Order, item2: Order): Boolean {
                return item1.id.equals(item2.id)
            }
        })
    }

    override fun getItemCount(): Int = orderList.size()

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bindOrder(orderList[position], position)
        holder.itemView.setOnClickListener { it ->
            val intent: Intent = Intent(it.context, OrderDetailActivity::class.java)
            intent.putExtra(OrderDetailActivity.NUMBER, position.toString())
            intent.putExtra(OrderDetailActivity.ID, orderList[position].id)
            it.context.startActivity(intent) }
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