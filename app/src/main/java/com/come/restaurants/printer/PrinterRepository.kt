package com.come.restaurants.printer

import com.come.restaurants.order.domain.model.Order
import com.come.restaurants.order.domain.usecases.PrintOrder

class PrinterRepository {
    fun print(order: Order, callback: PrintOrder.Callback) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}