package com.come.restaurants.order

class OrderLine(val id: String, val quantity: Int, val plate: Plate) {

    fun getPrice(): Double {
        return plate.price * quantity
    }

}