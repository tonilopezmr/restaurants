package com.come.restaurants.order.repository

import com.come.restaurants.order.Order
import com.come.restaurants.order.usecases.*

interface OrderRepository {
    fun getOrders(callback : GetOrders.Callback)
    fun getOrder(id: String, callback: GetOrder.Callback)
    fun orderReceived(order: Order, callback: OrderReceived.Callback)
    fun orderPrinted(order: Order, callback: OrderPrinted.Callback)
}