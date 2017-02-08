package com.come.restaurants.restaurant.domain

import com.come.restaurants.restaurant.login.usecases.Login


interface RestaurantRepository {
  fun getRestaurant(name: String, code: String, callback: Login.Callback)
}