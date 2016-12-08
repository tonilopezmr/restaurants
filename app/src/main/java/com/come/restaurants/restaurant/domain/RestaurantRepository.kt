package com.come.restaurants.restaurant.domain

import com.come.restaurants.restaurant.domain.model.Restaurant


interface RestaurantRepository {
  fun getRestaurant(name: String, code: String) : Restaurant
  fun getRestaurants(): List<Restaurant>
}