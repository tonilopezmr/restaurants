package com.come.restaurants.order.domain

import com.come.restaurants.order.domain.model.Order
import com.come.restaurants.order.domain.usecases.GetOrder
import com.come.restaurants.order.domain.usecases.GetOrders
import com.come.restaurants.order.domain.usecases.OrderPrinted
import com.come.restaurants.order.domain.usecases.OrderReceived

interface OrderRepository {
    fun getOrders(callback: GetOrders.Callback)
    fun getOrder(id: String, callback: GetOrder.Callback)
    fun orderReceived(order: Order, callback: OrderReceived.Callback)
    fun orderPrinted(order: Order, callback: OrderPrinted.Callback)
}