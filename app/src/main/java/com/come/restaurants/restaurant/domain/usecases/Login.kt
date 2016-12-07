package com.come.restaurants.restaurant.domain.usecases

import com.come.restaurants.base.BaseCallback
import com.come.restaurants.restaurant.domain.RestaurantRepository
import com.come.restaurants.restaurant.domain.model.Restaurant
import com.google.common.hash.Hashing
import java.nio.charset.Charset


class Login(val restaurantRepository: RestaurantRepository) {
  interface Callback : BaseCallback {
    fun loginCorrect(restaurant: Restaurant)
    fun nameNotFound()
    fun passwordNotCorrect()
  }

  fun login(name: String, passwd: String, callback: Login.Callback): Boolean {
    val restaurant = restaurantRepository.getRestaurant(name, passwd, callback)
    val hash = Hashing.sha256()
        .hashString(passwd, Charset.forName("UTF-8"))
        .toString()
    return hash == restaurant.password
  }

}
