package com.come.restaurants.order.usecases

import com.come.restaurants.order.Order
import com.come.restaurants.order.repository.OrderRepository

class GetOrders(val repository : OrderRepository) {
    interface Callback{
        fun ordersReceived(orders: List<Order>)
        fun error(exception: Exception)
    }
    lateinit private var callback : Callback
    fun get() {
        repository.getOrders(callback)
    }

}
