package com.come.restaurants.order.usecases

import com.come.restaurants.order.Order
import com.come.restaurants.order.persistence.network.FirebaseOrderRepository

class OrderReceived(val repository : FirebaseOrderRepository) {

    fun send(order: Order) {
        repository.orderReceived(order)
    }

}