package com.come.restaurants.order.repository

import com.come.restaurants.order.Order
import com.come.restaurants.order.usecases.GetOrder
import com.come.restaurants.order.usecases.GetOrders
import com.come.restaurants.order.usecases.OrderReceived
import com.come.restaurants.order.usecases.PrintOrder

interface OrderRepository {
    fun getOrders(callback : GetOrders.Callback)
    fun getOrder(id: String, callback: GetOrder.Callback)
    fun orderReceived(order: Order, callback: OrderReceived.Callback)
    fun orderPrinted(order: Order, callback: PrintOrder.Callback)
}