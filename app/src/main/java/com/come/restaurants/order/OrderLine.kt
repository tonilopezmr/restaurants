package com.come.restaurants.order

class OrderLine(val id: String, val plate: Plate, val quantity: Int = 1) {

    fun getPrice(): Double {
        return plate.price * quantity
    }

}