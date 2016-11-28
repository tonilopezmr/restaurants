package com.come.restaurants.order.usecases

import com.come.restaurants.order.Order
import com.come.restaurants.order.persistence.network.FirebaseOrderRepository

class OrderPrinted(val repository: FirebaseOrderRepository) {

    fun send(order: Order) {
        repository.orderPrinted(order)
    }

}
