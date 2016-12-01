package com.come.restaurants.order.domain.usecases

import com.come.restaurants.base.BaseCallback
import com.come.restaurants.order.domain.OrderRepository
import com.come.restaurants.order.domain.model.Order

class GetOrders(val repository: OrderRepository) {
    interface Callback : BaseCallback {
        fun ordersReceived(orders: List<Order>)
    }

    fun getOrders(callback: Callback) {
        repository.getOrders(callback)
    }

}
