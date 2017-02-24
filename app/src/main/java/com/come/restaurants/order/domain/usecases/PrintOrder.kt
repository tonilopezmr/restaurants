package com.come.restaurants.order.domain.usecases

import com.come.restaurants.base.BaseCallback
import com.come.restaurants.order.domain.model.Order
import com.come.restaurants.printer.service.PrinterQueue

class PrintOrder(private val printerQueue: PrinterQueue) {

  interface Callback : BaseCallback {
    fun orderPrinted(order: Order)
  }

  fun print(order: Order, callback: Callback) {
    printerQueue.add(order)
  }

}


