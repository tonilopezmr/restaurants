package com.come.restaurants.order.domain.usecases

import com.come.restaurants.base.BaseCallback
import com.come.restaurants.order.domain.model.Order
import com.come.restaurants.printer.domain.PrinterRepository

class PrintOrder(val repository: PrinterRepository) {
    interface Callback : BaseCallback {
        fun orderPrinted(order: Order)
    }

    fun print(order: Order, callback: Callback) {
        repository.print(order, callback)
    }

}


