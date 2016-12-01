package com.come.restaurants.order.domain.model

class Order(val id: String, val code: String, val timestamp: Long, val orderLines: List<OrderLine>) {

    fun getPrice(): Double {
        return orderLines.fold(0.0) { price, orderLine ->
            price + orderLine.getPrice()
        }
    }

}