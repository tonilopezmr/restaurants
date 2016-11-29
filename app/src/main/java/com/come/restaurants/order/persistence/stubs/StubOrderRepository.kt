package com.come.restaurants.order.persistence.stubs

import com.come.restaurants.order.Order
import com.come.restaurants.order.OrderLine
import com.come.restaurants.order.Plate
import com.come.restaurants.order.repository.OrderRepository
import java.util.*

class StubOrderRepository : OrderRepository {

    var order = emptyList<Order>()

    init {
        val tortilla = Plate("1", "Tortilla", 1.30, emptyList())
        val zumo = Plate("2", "Zumo", 0.95, emptyList())

        val orderLine1 = OrderLine("1", tortilla, 2)
        val orderLine2 = OrderLine("2", zumo)
        val order1 = Order("1", "234d", Date().time , listOf(orderLine1, orderLine2))
        val orderLine3 = OrderLine("3", tortilla)
        val orderLine4 = OrderLine("4", zumo, 5)

        val order2 = Order("2", "23Z", Date().time-10000, listOf(orderLine3, orderLine4))
        this.order = listOf(order1, order2)
    }

    override fun getOrders(): List<Order> {
        return order
    }

    override fun getOrder(id: String): Order? {
        val orderList = order.filter { it -> it.id == id }
        return if (orderList.isEmpty()) null else orderList[0]
    }

    override fun orderReceived(order: Order) {

    }

    override fun orderPrinted(order: Order) {

    }

}