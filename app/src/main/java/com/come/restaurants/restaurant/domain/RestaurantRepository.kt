package com.come.restaurants.restaurant.domain

import com.come.restaurants.restaurant.domain.usecases.Login


interface RestaurantRepository {
  fun getRestaurant(name: String, pass: String, callback: Login.Callback)
}