package com.come.restaurants.order.usecases

import com.come.restaurants.order.Order
import com.come.restaurants.order.repository.OrderRepository

class OrderReceived(val repository : OrderRepository) {
    interface Callback : BaseCallback{
        fun orderRegistered()
    }
    lateinit private var callback : Callback
    fun send(order: Order) {
        repository.orderReceived(order,callback)
    }

}