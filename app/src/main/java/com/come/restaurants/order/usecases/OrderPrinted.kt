package com.come.restaurants.order.usecases

import com.come.restaurants.order.Order
import com.come.restaurants.order.repository.OrderRepository

class OrderPrinted(val repository: OrderRepository) {
    interface Callback : BaseCallback{
        fun orderPrinted()
    }

    fun send(order: Order, callback: Callback) {
        repository.orderPrinted(order,callback)
    }

}
