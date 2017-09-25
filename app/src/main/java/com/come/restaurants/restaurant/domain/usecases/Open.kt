package com.come.restaurants.restaurant.domain.usecases


import com.come.restaurants.restaurant.domain.RestaurantRepository
import com.come.restaurants.restaurant.domain.model.Restaurant

class Open(private val restaurantRepository: RestaurantRepository) {

  interface Callback {
    fun success()
    fun fail()
  }

  fun open(restaurant: Restaurant, callback: Callback) {
    restaurantRepository.open(restaurant, success = {
      callback.success()
    }, fail = {
      callback.fail()
    })
  }

}