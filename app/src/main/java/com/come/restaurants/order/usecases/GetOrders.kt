package com.come.restaurants.order.usecases

import com.come.restaurants.order.Order
import com.come.restaurants.order.repository.OrderRepository

class GetOrders(val repository : OrderRepository) {
    interface Callback : BaseCallback {
        fun ordersReceived(orders: List<Order>)
    }
    lateinit private var callback : Callback
    fun get() {
        repository.getOrders(callback)
    }

}
