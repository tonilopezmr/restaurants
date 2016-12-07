package com.come.restaurants.restaurant.domain

import com.come.restaurants.restaurant.domain.model.Restaurant


interface RestaurantRepository {
  fun getRestaurantByCode(code: String): Restaurant
  fun getRestaurantByName(name: String): Restaurant
  fun getRestaurants(): List<Restaurant>
}