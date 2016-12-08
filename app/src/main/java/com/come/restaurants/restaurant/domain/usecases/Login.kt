package com.come.restaurants.restaurant.domain.usecases

import com.come.restaurants.restaurant.domain.RestaurantRepository
import com.google.common.hash.Hashing
import java.nio.charset.Charset


class Login(val restaurantRepository: RestaurantRepository) {

  fun login(name: String, passwd: String): Boolean {
    val hash = Hashing.sha256()
        .hashString(passwd, Charset.forName("UTF-8"))
        .toString()
    val restaurant = restaurantRepository.getRestaurant(name, hash) ?: return false

    return hash == restaurant.password
  }

}