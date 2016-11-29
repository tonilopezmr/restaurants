package com.come.restaurants.order.persistence.network

import com.come.restaurants.order.Order
import com.come.restaurants.order.repository.OrderRepository
import com.come.restaurants.order.usecases.GetOrder
import com.come.restaurants.order.usecases.GetOrders
import com.come.restaurants.order.usecases.OrderReceived
import com.come.restaurants.order.usecases.PrintOrder
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FirebaseOrderRepository : OrderRepository {
    private var database : FirebaseDatabase
    private var reference : DatabaseReference
    init {
        database = FirebaseDatabase.getInstance()
        reference = database.getReference("")
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

    override fun getOrders(callback: GetOrders.Callback) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}