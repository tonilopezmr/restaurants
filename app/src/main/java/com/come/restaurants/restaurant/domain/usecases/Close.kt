package com.come.restaurants.restaurant.domain.usecases

import com.come.restaurants.restaurant.domain.RestaurantRepository
import com.come.restaurants.restaurant.domain.model.Restaurant

class Close(private val restaurantRepository: RestaurantRepository) {

  interface Callback {
    fun success()
    fun fail()
  }

  fun close(restaurant: Restaurant, callback: Callback) {
    restaurantRepository.close(restaurant, success = {
      callback.success()
    }, fail = {
      callback.fail()
    })
  }

}