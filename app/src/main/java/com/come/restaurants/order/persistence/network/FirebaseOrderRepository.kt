package com.come.restaurants.order.persistence.network

import com.come.restaurants.order.domain.OrderRepository
import com.come.restaurants.order.domain.model.Order
import com.come.restaurants.order.domain.usecases.GetNewOrder
import com.come.restaurants.order.domain.usecases.GetOrder
import com.come.restaurants.order.domain.usecases.GetOrders
import com.come.restaurants.order.domain.usecases.OrderPrinted
import com.come.restaurants.restaurant.domain.model.Restaurant
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat

class FirebaseOrderRepository(private val currentUser : Restaurant) : OrderRepository {

  private companion object {
    val NEWS = "/news"
    val SERVED = "/served"
  }

  private var database: FirebaseDatabase
  private var reference: DatabaseReference
  private val today: String
  lateinit private var newOrderListener: ChildEventListener

  init {
    today = SimpleDateFormat("dd-MM-yyyy").format(System.currentTimeMillis())
    database = FirebaseDatabase.getInstance()
    reference = database.getReference("restaurant/"+ currentUser.code +"/orders/")
  }

  private fun getServedOrdersRef() = reference.child(today + SERVED)

  private fun getNewsOrderRef() = reference.child(today + NEWS)

  override fun getOrder(id: String, callback: GetOrder.Callback) {
    getServedOrdersRef().child(id).addListenerForSingleValueEvent(object : ValueEventListener {
      override fun onCancelled(databaseError: DatabaseError) {
        callback.error(Exception(databaseError.message))
      }

      override fun onDataChange(dataSnapshot: DataSnapshot) {
        callback.orderReceived(dataSnapshot.getValue(Order :: class.java))
      }

    })
  }

  override fun orderPrinted(order: Order, callback: OrderPrinted.Callback) {
    //TODO implement
  }

  override fun getOrders(callback: GetOrders.Callback) {
    getServedOrdersRef().addListenerForSingleValueEvent(object : ValueEventListener {
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
    this.newOrderListener = getNewsOrderRef().addChildEventListener(object : ChildEventListener {
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
        val order = dataSnapshot.getValue(Order :: class.java)
        callback.orderReceived(order)
        getServedOrdersRef().child(order.id).setValue(order)
        dataSnapshot.ref.removeValue()
      }

    })
  }

  override fun removeListeners(){
    getNewsOrderRef().removeEventListener(newOrderListener)
  }

}
