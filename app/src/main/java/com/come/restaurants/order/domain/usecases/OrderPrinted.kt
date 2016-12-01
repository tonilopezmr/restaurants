package com.come.restaurants.order.domain.usecases

import com.come.restaurants.base.BaseCallback
import com.come.restaurants.order.domain.model.Order
import com.come.restaurants.order.domain.OrderRepository

class OrderPrinted(val repository: OrderRepository) {
    interface Callback : BaseCallback {
        fun orderPrinted()
    }

    fun send(order: Order, callback: Callback) {
        repository.orderPrinted(order,callback)
    }

}
