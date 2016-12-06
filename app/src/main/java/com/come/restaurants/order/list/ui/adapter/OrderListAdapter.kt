package com.come.restaurants.order.list.ui.adapter

import android.content.Intent
import android.support.v7.util.SortedList
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.util.SortedListAdapterCallback
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.come.restaurants.R
import com.come.restaurants.order.detail.OrderDetailActivity
import com.come.restaurants.order.domain.model.Order
import kotlinx.android.synthetic.main.order_item.view.*
import java.text.SimpleDateFormat
import java.util.Date

class OrderListAdapter() : RecyclerView.Adapter<OrderListAdapter.ListViewHolder>() {
    private var orderList : SortedList<Order>

    init {
        orderList  = SortedList<Order>(Order::class.java, object : SortedListAdapterCallback<Order>(this) {
            override fun compare(o1: Order, o2: Order): Int {
                return o2.timestamp.compareTo(o1.timestamp)
            }

            override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(item1: Order, item2: Order): Boolean {
                return item1.id === item2.id
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
                .inflate(R.layout.order_item, parent, false)
        return ListViewHolder(view)
    }

    fun addAll(orderList: List<Order>) {
        this.orderList.addAll(orderList)
        notifyDataSetChanged()
    }

    fun getItems() = this.orderList

    class ListViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bindOrder(order: Order, position: Int) {
            with(order) {
                var dateFromat = SimpleDateFormat("HH:mm:ss")
                itemView.orderNumberText.text = "${itemView.context.getString(R.string.number)} $position"
                itemView.orderHourText.text = "${dateFromat.format(Date(order.timestamp))}"
                itemView.totalPriceText.text = "${itemView.context.getString(R.string.total_price)} ${order.getPrice()}€"
                itemView.orderPlatesText.text = orderLines.fold("", {total, current ->
                    total.plus("${current.quantity}x${current.plate.name}\n")} )
            }
        }
    }

}