package com.come.restaurants.order.usecases

import com.come.restaurants.order.Order
import com.come.restaurants.order.repository.OrderRepository

class OrderReceived(val repository : OrderRepository) {
    interface Callback{
        fun orderRegistered(order : Order)
        fun error(e : Exception)
    }
    fun send(order: Order) {

    }

}