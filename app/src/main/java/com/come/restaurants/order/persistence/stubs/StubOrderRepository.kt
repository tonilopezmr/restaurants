package com.come.restaurants.order.persistence.stubs

import com.come.restaurants.order.Order
import com.come.restaurants.order.OrderLine
import com.come.restaurants.order.Plate
import com.come.restaurants.order.repository.OrderRepository
import com.come.restaurants.order.usecases.GetOrder
import com.come.restaurants.order.usecases.GetOrders
import com.come.restaurants.order.usecases.OrderReceived
import com.come.restaurants.order.usecases.PrintOrder

class StubOrderRepository : OrderRepository {

    var order = emptyList<Order>()

    init {
        val tortilla = Plate("1", "Tortilla", 1.30, emptyList())
        val zumo = Plate("2", "Zumo", 0.95, emptyList())

        val orderLine1 = OrderLine("1", tortilla, 2)
        val orderLine2 = OrderLine("2", zumo)
        val order1 = Order("1", "234d", 234, listOf(orderLine1, orderLine2))
        val orderLine3 = OrderLine("3", tortilla)
        val orderLine4 = OrderLine("4", zumo, 5)

        val order2 = Order("2", "23Z", 234, listOf(orderLine3, orderLine4))
        this.order = listOf(order1, order2)
    }

    override fun getOrders(callback: GetOrders.Callback) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getOrder(id: String, callback: GetOrder.Callback) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun orderReceived(order: Order, callback: OrderReceived.Callback) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun orderPrinted(order: Order, callback: PrintOrder.Callback) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}