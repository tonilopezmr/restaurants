package com.come.restaurants.order.usecases

import com.come.restaurants.order.Order
import com.come.restaurants.order.repository.OrderRepository
import com.come.restaurants.order.usecases.GetOrder.Callback

class GetOrder(val repository: OrderRepository) {
    interface Callback : BaseCallback{
        fun orderReceived(order : Order)
    }

    fun get(id: String, callback: Callback){
        repository.getOrder(id, callback)
    }

}