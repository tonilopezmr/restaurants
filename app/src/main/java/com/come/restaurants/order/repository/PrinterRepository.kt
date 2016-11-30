package com.come.restaurants.order.repository

import com.come.restaurants.order.Order
import com.come.restaurants.order.usecases.PrintOrder

class PrinterRepository {
    fun print(order: Order, callback: PrintOrder.Callback) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}