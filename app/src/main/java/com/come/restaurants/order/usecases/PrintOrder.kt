package com.come.restaurants.order.usecases

import com.come.restaurants.order.Order
import com.come.restaurants.order.repository.PrinterRepository

class PrintOrder(val repository: PrinterRepository) {
    interface Callback : BaseCallback{
        fun orderPrinted()
    }

    fun print(order: Order,callback: Callback) {
        repository.print(order,callback)
    }

}


