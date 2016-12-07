package com.come.restaurants.restaurant.persistence.network

import com.come.restaurants.menu.Menu
import com.come.restaurants.restaurant.domain.RestaurantRepository
import com.come.restaurants.restaurant.domain.model.Restaurant
import com.come.restaurants.restaurant.domain.usecases.Login
import com.google.firebase.database.*
import java.util.*

class FirebaseRestaurantRepository : RestaurantRepository {

  private var database: FirebaseDatabase
  private var reference: DatabaseReference

  init {
    database = FirebaseDatabase.getInstance()
    reference = database.getReference("restaurant")
  }

  override fun getRestaurant(name: String, code: String, callback: Login.Callback): Restaurant {
    reference.child(name).addListenerForSingleValueEvent(object : ValueEventListener {
      override fun onCancelled(databaseError: DatabaseError) {
        callback.error(Exception(databaseError.message))
      }

      override fun onDataChange(dataSnapshot: DataSnapshot) {
        if (dataSnapshot.hasChildren()) {
          if (dataSnapshot.child("password").value.equals(code)) {
            callback.loginCorrect(Restaurant(name, code, "", Menu(ArrayList())))
          } else {
            callback.passwordNotCorrect()
          }
        } else {
          callback.nameNotFound()
        }
      }

    })
    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}
