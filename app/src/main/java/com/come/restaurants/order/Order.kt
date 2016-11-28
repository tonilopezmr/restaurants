package com.come.restaurants.order

class Order(val id: String, val code: String, val timestamp: Long, val orderLines: List<OrderLine>) {

    fun getPrice() {
        orderLines.fold(0.0) { price, orderLine ->
            price + orderLine.getPrice()
        }
    }

}
