package com.come.restaurants.order.usecases

import com.come.restaurants.order.Order
import com.come.restaurants.order.persistence.network.FirebaseOrderRepository

class GetOrder(val repository: FirebaseOrderRepository) {

    fun get(id: String): Order {
        return repository.getOrder(id)
    }

}