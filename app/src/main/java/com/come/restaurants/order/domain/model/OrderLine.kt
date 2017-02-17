package com.come.restaurants.order.domain.model

class OrderLine(val id: String = "",
                val plate: Plate = Plate(),
                val quantity: Int = 1) {

  fun getPrice(): Double {
    return plate.price * quantity
  }

}