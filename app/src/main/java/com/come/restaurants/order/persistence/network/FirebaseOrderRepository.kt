package com.come.restaurants.order.persistence.network

import com.come.restaurants.order.domain.OrderRepository
import com.come.restaurants.order.domain.model.Order
import com.come.restaurants.order.domain.usecases.GetNewOrder
import com.come.restaurants.order.domain.usecases.GetOrder
import com.come.restaurants.order.domain.usecases.GetOrders
import com.come.restaurants.order.domain.usecases.OrderPrinted
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat

class FirebaseOrderRepository : OrderRepository {

  private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
  private val reference: DatabaseReference
  private val today: String

  init {
    today = SimpleDateFormat("dd-MM-yyyy").format(System.currentTimeMillis())
    reference = database.getReference("restaurant/vella/orders/")
  }

  override fun getOrder(id: String, callback: GetOrder.Callback) {
    reference.child(today).child(id).addListenerForSingleValueEvent(object : ValueEventListener {
      override fun onCancelled(databaseError: DatabaseError) {
        callback.error(Exception(databaseError.message))
      }

      override fun onDataChange(dataSnapshot: DataSnapshot) {
        callback.orderReceived(dataSnapshot.getValue(Order :: class.java))
      }

    })
  }

  override fun orderPrinted(order: Order, callback: OrderPrinted.Callback) {
    reference.child(today).child(order.id).removeValue()
    reference.child("history").child(order.id).setValue(order)
  }

  override fun getOrders(callback: GetOrders.Callback) {
    reference.child(today).addListenerForSingleValueEvent(object : ValueEventListener {
      override fun onCancelled(databaseError: DatabaseError) {
        callback.error(Exception(databaseError.message))
      }

      override fun onDataChange(dataSnapshot: DataSnapshot) {
        val orders = dataSnapshot.children.map { it.getValue(Order ::class.java) }
        callback.ordersReceived(orders)
      }
    })
  }

  override fun getNewOrder(callback: GetNewOrder.Callback) {
    reference.child(today).addChildEventListener(object : ChildEventListener {
      override fun onChildChanged(p0: DataSnapshot?, p1: String?) {

      }

      override fun onChildRemoved(p0: DataSnapshot?) {

      }

      override fun onCancelled(databaseError: DatabaseError) {
        callback.error(Exception(databaseError.message))
      }

      override fun onChildMoved(p0: DataSnapshot?, p1: String?) {

      }

      override fun onChildAdded(dataSnapshot: DataSnapshot, child: String?) {
        callback.orderReceived(dataSnapshot.getValue(Order :: class.java))
      }

    })
  }

}
