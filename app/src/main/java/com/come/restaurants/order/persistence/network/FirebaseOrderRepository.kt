package com.come.restaurants.order.persistence.network

import android.content.SharedPreferences
import com.come.restaurants.order.domain.OrderRepository
import com.come.restaurants.order.domain.model.Order
import com.come.restaurants.order.domain.usecases.GetNewOrder
import com.come.restaurants.order.domain.usecases.GetOrder
import com.come.restaurants.order.domain.usecases.GetOrders
import com.come.restaurants.order.domain.usecases.OrderPrinted
import com.google.firebase.database.*
import java.text.SimpleDateFormat

class FirebaseOrderRepository(private val preferences: SharedPreferences) : OrderRepository {

  private companion object {
    val NEWS = "/news"
    val SERVED = "/served"
  }

  private var database: FirebaseDatabase
  private val today: String
  lateinit private var newOrderListener: ChildEventListener

  init {
    today = SimpleDateFormat("dd-MM-yyyy").format(System.currentTimeMillis())
    database = FirebaseDatabase.getInstance()

  }

  private fun getServedOrdersRef(): DatabaseReference {
    val reference = getDatabaseReference()
    return reference.child(today + SERVED)
  }

  private fun getNewsOrderRef(): DatabaseReference {
    val reference = getDatabaseReference()
    return reference.child(today + NEWS)
  }

  private fun getDatabaseReference(): DatabaseReference {
    val reference = database.getReference("restaurant/" + preferences.getString("code", "") + "/orders/")
    return reference
  }

  override fun getOrder(id: String, callback: GetOrder.Callback) {
    getServedOrdersRef().child(id).addListenerForSingleValueEvent(object : ValueEventListener {
      override fun onCancelled(databaseError: DatabaseError) {
        callback.error(Exception(databaseError.message))
      }

      override fun onDataChange(dataSnapshot: DataSnapshot) {
        callback.orderReceived(dataSnapshot.getValue(Order::class.java))
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
        val orders = dataSnapshot.children.map { it.getValue(Order::class.java) }
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
        val order = dataSnapshot.getValue(Order::class.java)
        callback.orderReceived(order)
        getServedOrdersRef().child(order.id).setValue(order)
        dataSnapshot.ref.removeValue()
      }

    })
  }

  override fun removeListeners() {
    getNewsOrderRef().removeEventListener(newOrderListener)
  }

}
