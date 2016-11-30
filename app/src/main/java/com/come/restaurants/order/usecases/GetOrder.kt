package com.come.restaurants.order.usecases

import com.come.restaurants.order.Order
import com.come.restaurants.order.repository.OrderRepository
import com.come.restaurants.order.usecases.GetOrder.Callback

class GetOrder(val repository: OrderRepository) {
    interface Callback : BaseCallback{
        fun orderReceived(order : Order)
    }
    lateinit private var callback : Callback
    fun get(id: String){
        repository.getOrder(id, callback)
    }

}