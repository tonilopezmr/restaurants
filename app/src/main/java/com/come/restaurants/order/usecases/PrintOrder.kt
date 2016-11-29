package com.come.restaurants.order.usecases

import com.come.restaurants.order.Order
import com.come.restaurants.order.repository.PrinterRepository

class PrintOrder(val repository: PrinterRepository) {
    interface Callback{
        fun orderPrinted(order : Order)
        fun error(e : Exception)
    }
    fun print(order: Order) {
        repository.print(order)
    }

}


