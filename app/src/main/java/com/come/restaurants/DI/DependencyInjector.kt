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
import com.come.restaurants.restaurant.domain.usecases.Close
import com.come.restaurants.restaurant.domain.usecases.Open
import com.come.restaurants.restaurant.login.UserProvider
import com.come.restaurants.restaurant.persistence.network.FirebaseRestaurantRepository

object DependencyInjector {

  val restaurantRepository = FirebaseRestaurantRepository()
  val orderRepository = FirebaseOrderRepository(UserProvider.user)
  val printer = PrinterFactory.getPrinter()
  val printerJob = PrinterService(printer)
  var printerRepository = PrinterRepository(printerJob)
  val printerQueue = PrinterQueue(printerRepository)

  fun getOrder(): GetOrder {
    return GetOrder(orderRepository)
  }

  fun getOrders(): GetOrders {
    return GetOrders(orderRepository)
  }

  fun getPrintOrder(): PrintOrder {
    return PrintOrder(printerQueue)
  }

  fun getWelcome(): PrintWelcome {
    return PrintWelcome(printerRepository)
  }

  fun getNewOrder(): GetNewOrder {
    return GetNewOrder(orderRepository)
  }

  fun getOpen(): Open {
    return Open(restaurantRepository)
  }

  fun getClose(): Close {
    return Close(restaurantRepository)
  }

  fun removeListeners() {
    orderRepository.removeListeners()
  }

  fun startQueue() {
    if (!printerQueue.isAlive) printerQueue.start()
  }

  fun stopQueue(){
    printerQueue.stopQueue()
  }

}
