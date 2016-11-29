package com.come.restaurants.order.usecases

import com.come.restaurants.order.Order
import com.come.restaurants.order.repository.OrderRepository

class GetOrder(val repository: OrderRepository) {
    interface Callback{
        fun orderReceived(order : Order)
        fun error(e : Exception)
    }
    fun get(id: String){

    }

}