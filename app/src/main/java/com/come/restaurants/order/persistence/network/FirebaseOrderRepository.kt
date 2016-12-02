package com.come.restaurants.order.persistence.network

import com.come.restaurants.order.domain.model.Order
import com.come.restaurants.order.domain.OrderRepository
import com.come.restaurants.order.domain.usecases.*
import com.google.firebase.database.*
import java.util.*

class FirebaseOrderRepository : OrderRepository {
    private var database : FirebaseDatabase
    private var reference : DatabaseReference
    init {
        database = FirebaseDatabase.getInstance()
        reference = database.getReference("restaurant/vella")
    }

    override fun getOrder(id: String, callback: GetOrder.Callback) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun orderReceived(order: Order, callback: OrderReceived.Callback) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun orderPrinted(order: Order, callback: OrderPrinted.Callback) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getOrders(callback: GetOrders.Callback) {
       reference.child("orders").addListenerForSingleValueEvent(object : ValueEventListener{
           override fun onCancelled(databaseError: DatabaseError){
               callback.error(Exception(databaseError.message))
           }

           override fun onDataChange(dataSnapshot: DataSnapshot){
               val orders = dataSnapshot.children.map { it.getValue(Order ::class.java) }
               callback.ordersReceived(orders)
           }

       })
    }

}