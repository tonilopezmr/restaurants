package com.come.restaurants.order.domain.usecases

import com.come.restaurants.base.BaseCallback
import com.come.restaurants.order.domain.OrderRepository
import com.come.restaurants.order.domain.model.Order

class OrderReceived(val repository: OrderRepository) {
    interface Callback : BaseCallback {
        fun orderRegistered()
    }

    fun send(order: Order, callback: Callback) {
        repository.orderReceived(order, callback)
    }

}