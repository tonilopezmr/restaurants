package com.come.restaurants.order.usecases

import com.come.restaurants.order.Order
import com.come.restaurants.order.persistence.network.FirebaseOrderRepository

class GetOrders(val repository : FirebaseOrderRepository) {

    fun get(): List<Order> {
        return repository.getOrders()
    }

}
