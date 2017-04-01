package com.come.restaurants.restaurant.persistence.network

import com.come.restaurants.menu.Menu
import com.come.restaurants.restaurant.domain.RestaurantRepository
import com.come.restaurants.restaurant.domain.model.Restaurant
import com.come.restaurants.restaurant.domain.usecases.Login
import com.google.firebase.database.*
import java.util.*

class FirebaseRestaurantRepository : RestaurantRepository {

  private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
  private val reference: DatabaseReference = database.getReference("restaurant")

  override fun close(restaurant: Restaurant, success: () -> Unit, fail: () -> Unit) {
    reference.child(restaurant.name).child("isOpen").setValue(false)
        .addOnSuccessListener { success() }
        .addOnFailureListener { fail() }
  }

  override fun open(restaurant: Restaurant, success: () -> Unit, fail: () -> Unit) {
    reference.child(restaurant.name).child("isOpen").setValue(true)
        .addOnSuccessListener { success() }
        .addOnFailureListener { fail() }
  }

  override fun login(name: String, pass: String, callback: Login.Callback) {
    reference.child(name).addListenerForSingleValueEvent(object : ValueEventListener {
      override fun onCancelled(databaseError: DatabaseError) {
        callback.error(Exception(databaseError.message))
      }

      override fun onDataChange(dataSnapshot: DataSnapshot) {
        if (dataSnapshot.hasChildren()) {
          if (dataSnapshot.child("password").value == pass) {
            callback.loginCorrect(Restaurant(name, pass, name, Menu(ArrayList())))
          } else {
            callback.passwordNotCorrect()
          }
        } else {
          callback.nameNotFound()
        }
      }

    })
  }
}
