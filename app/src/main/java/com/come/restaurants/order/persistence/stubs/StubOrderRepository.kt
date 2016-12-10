package com.come.restaurants.order.persistence.stubs

import com.come.restaurants.order.domain.OrderRepository
import com.come.restaurants.order.domain.model.Order
import com.come.restaurants.order.domain.model.OrderLine
import com.come.restaurants.order.domain.model.Plate
import com.come.restaurants.order.domain.usecases.GetNewOrder
import com.come.restaurants.order.domain.usecases.GetOrder
import com.come.restaurants.order.domain.usecases.GetOrders
import com.come.restaurants.order.domain.usecases.OrderPrinted
import java.util.Date

class StubOrderRepository : OrderRepository {

  override fun getNewOrder(callback: GetNewOrder.Callback) {
    callback.orderReceived(orderList[orderList.size - 1])
  }

  var orderList = emptyList<Order>()

  init {
    val tortilla = Plate("1", "Tortilla", 1.30, emptyList())
    val zumo = Plate("2", "Zumo", 0.95, emptyList())

    val orderLine1 = OrderLine("1", tortilla, 2)
    val orderLine2 = OrderLine("2", zumo)
    val order1 = Order("1", "234d", Date().time, listOf(orderLine1, orderLine2))
    val orderLine3 = OrderLine("3", tortilla)
    val orderLine4 = OrderLine("4", zumo, 5)

    val order2 = Order("2", "23Z", Date().time - 10000000, listOf(orderLine3, orderLine4))
    this.orderList = listOf(order1, order2)
  }

  override fun getOrders(callback: GetOrders.Callback) {
    callback.ordersReceived(orderList)
  }

  override fun getOrder(id: String, callback: GetOrder.Callback) {
    callback.orderReceived(orderList.filter { it.id == id }.first())
  }

  override fun orderPrinted(order: Order, callback: OrderPrinted.Callback) {
    callback.orderPrinted()
  }

}
