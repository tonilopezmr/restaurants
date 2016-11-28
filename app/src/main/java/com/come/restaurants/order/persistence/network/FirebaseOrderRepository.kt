package com.come.restaurants.order.persistence.network

import com.come.restaurants.order.Order
import com.come.restaurants.order.repository.OrderRepository

class FirebaseOrderRepository : OrderRepository {

    override fun getOrders(): List<Order> {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getOrder(id: String): Order? {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun orderReceived(order: Order) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun orderPrinted(order: Order) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}