package com.come.restaurants.restaurant.domain

import com.come.restaurants.restaurant.domain.model.Restaurant
import com.come.restaurants.restaurant.domain.usecases.Login


interface RestaurantRepository {
  fun login(name: String, pass: String, callback: Login.Callback)
  fun open(restaurant: Restaurant, success: () -> Unit, fail: () -> Unit)
  fun close(restaurant: Restaurant, success: () -> Unit, fail: () -> Unit)
}