package com.come.restaurants.restaurant.persistence.stubs

import com.come.restaurants.menu.Menu
import com.come.restaurants.restaurant.domain.RestaurantRepository
import com.come.restaurants.restaurant.domain.model.Restaurant
import com.come.restaurants.restaurant.domain.usecases.Login
import java.util.ArrayList


class StubRestaurantRepository : RestaurantRepository {

  override fun getRestaurant(name: String, passwd: String, callback: Login.Callback) {
    val list = restaurants
        .filter { rest -> rest.password == passwd && rest.name == name }

    if (list.isEmpty()) {
      callback.loginCorrect(null)
    } else {
      callback.loginCorrect(list.first())
    }
  }

  val restaurants = ArrayList<Restaurant>()

  init {
    val menu = Menu(emptyList())

    restaurants.add(Restaurant(
        "La Vella",
        "7e79497526e08d1e87ee778364101f27ba062e77d8d21bda6956ff47c8c1a9fe",
        "1",
        menu))
    restaurants.add(Restaurant(
        "Conservatorio",
        "1c4a5a3f18a6f390ffab9aa5d5585bf80ff31b3a6c0fe1f1761d5da881f66c4c",
        "2",
        menu
    ))

  }
}
