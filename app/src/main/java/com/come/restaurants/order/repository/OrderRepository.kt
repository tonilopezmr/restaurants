package com.come.restaurants.order.repository

import com.come.restaurants.order.Order

interface OrderRepository {
    fun getOrders(): List<Order>
    fun getOrder(id: String): Order?
    fun orderReceived(order: Order)
    fun orderPrinted(order: Order)
}