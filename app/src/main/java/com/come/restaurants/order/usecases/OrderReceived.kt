package com.come.restaurants.order.usecases

import com.come.restaurants.order.Order
import com.come.restaurants.order.repository.OrderRepository

class OrderReceived(val repository : OrderRepository) {
    interface Callback : BaseCallback{
        fun orderRegistered()
    }

    fun send(order: Order, callback: Callback) {
        repository.orderReceived(order,callback)
    }

}