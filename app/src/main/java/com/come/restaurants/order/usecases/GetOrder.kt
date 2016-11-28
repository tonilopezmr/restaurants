package com.come.restaurants.order.usecases

import com.come.restaurants.order.Order
import com.come.restaurants.order.repository.OrderRepository

class GetOrder(val repository: OrderRepository) {

    fun get(id: String): Order? {
        return repository.getOrder(id)
    }

}