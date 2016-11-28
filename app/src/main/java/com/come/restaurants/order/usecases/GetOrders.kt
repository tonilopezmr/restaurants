package com.come.restaurants.order.usecases

import com.come.restaurants.order.Order
import com.come.restaurants.order.repository.OrderRepository

class GetOrders(val repository : OrderRepository) {

    fun get(): List<Order> {
        return repository.getOrders()
    }

}
