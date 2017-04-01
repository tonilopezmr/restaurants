package com.come.restaurants.DI

import com.come.restaurants.order.domain.usecases.GetNewOrder
import com.come.restaurants.order.domain.usecases.GetOrder
import com.come.restaurants.order.domain.usecases.GetOrders
import com.come.restaurants.order.domain.usecases.PrintOrder
import com.come.restaurants.order.persistence.network.FirebaseOrderRepository
import com.come.restaurants.printer.domain.PrinterRepository
import com.come.restaurants.printer.domain.usecases.PrintWelcome
import com.come.restaurants.printer.service.PrinterFactory
import com.come.restaurants.printer.service.PrinterQueue
import com.come.restaurants.printer.service.PrinterService
import com.come.restaurants.restaurant.login.UserProvider

object DependencyInjector {

  val repository = FirebaseOrderRepository(UserProvider.user)
  val printer = PrinterFactory.getPrinter()
  val printerJob = PrinterService(printer)
  var printerRepository = PrinterRepository(printerJob)
  val printerQueue = PrinterQueue(printerRepository)

  fun getOrder(): GetOrder {
    return GetOrder(repository)
  }

  fun getOrders(): GetOrders {
    return GetOrders(repository)
  }

  fun getPrintOrder(): PrintOrder {
    return PrintOrder(printerQueue)
  }

  fun getWelcome(): PrintWelcome {
    return PrintWelcome(printerRepository)
  }

  fun getNewOrder(): GetNewOrder {
    return GetNewOrder(repository)
  }

  fun removeListeners() {
    repository.removeListeners()
  }

  fun startQueue() {
    printerQueue.start()
  }

}
