package com.come.restaurants.restaurant.domain

import com.come.restaurants.restaurant.domain.model.Restaurant
import com.come.restaurants.restaurant.domain.usecases.Login


interface RestaurantRepository {
  fun getRestaurant(name: String, code: String, callback: Login.Callback) : Restaurant
}