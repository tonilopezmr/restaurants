package com.come.restaurants.order.usecases

import com.come.restaurants.order.Order
import com.come.restaurants.order.repository.OrderRepository

class OrderPrinted(val repository: OrderRepository) {

    fun send(order: Order) {
        repository.orderPrinted(order)
    }

}
