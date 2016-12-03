package com.come.restaurants.order.persistence.network

import com.come.restaurants.order.domain.model.Order
import com.come.restaurants.order.domain.OrderRepository
import com.come.restaurants.order.domain.usecases.*
import com.google.firebase.database.*

class FirebaseOrderRepository : OrderRepository {
    private var database : FirebaseDatabase
    private var reference : DatabaseReference
    init {
        database = FirebaseDatabase.getInstance()
        reference = database.getReference("restaurant/vella")
    }

    override fun getOrder(id: String, callback: GetOrder.Callback) {
       reference.child("orders").child(id).addListenerForSingleValueEvent(object : ValueEventListener{
           override fun onCancelled(p0: DatabaseError) {
               callback.error(Exception(p0.message))
           }

           override fun onDataChange(p0: DataSnapshot) {
               callback.orderReceived(p0.getValue(Order :: class.java))
           }

       })
    }

    override fun orderPrinted(order: Order, callback: OrderPrinted.Callback) {
        reference.child("orders").child(order.id).removeValue()
        reference.child("history").child(order.id).setValue(order)
    }

    override fun getOrders(callback: GetOrders.Callback) {
       reference.child("orders").addValueEventListener(object : ValueEventListener{
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