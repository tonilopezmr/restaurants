package com.come.restaurants.order.usecases

import com.come.restaurants.order.Order
import com.come.restaurants.order.repository.OrderRepository

class OrderPrinted(val repository: OrderRepository) {
    interface Callback : BaseCallback{
        fun orderPrinted()
    }
    lateinit private var callback : Callback
    fun send(order: Order) {
        repository.orderPrinted(order,callback)
    }

}
