package com.come.restaurants.DI

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.come.restaurants.order.domain.OrderRepository
import com.come.restaurants.order.domain.usecases.GetNewOrder
import com.come.restaurants.order.domain.usecases.GetOrder
import com.come.restaurants.order.domain.usecases.GetOrders
import com.come.restaurants.order.domain.usecases.PrintOrder
import com.come.restaurants.order.persistence.network.FirebaseOrderRepository
import com.come.restaurants.printer.domain.PrinterRepository
import com.come.restaurants.printer.domain.usecases.PrintWelcome
import com.come.restaurants.printer.service.Printer
import com.come.restaurants.printer.service.PrinterFactory
import com.come.restaurants.printer.service.PrinterQueue
import com.come.restaurants.printer.service.PrinterService
import com.come.restaurants.restaurant.domain.RestaurantRepository
import com.come.restaurants.restaurant.domain.usecases.Close
import com.come.restaurants.restaurant.domain.usecases.Open
import com.come.restaurants.restaurant.persistence.network.FirebaseRestaurantRepository

object DependencyInjector {

  lateinit var context: Context
  lateinit var restaurantRepository: RestaurantRepository
  lateinit var orderRepository: OrderRepository
  lateinit var printer: Printer
  lateinit var printerJob: PrinterService
  lateinit var printerRepository: PrinterRepository
  lateinit var printerQueue: PrinterQueue

  fun init(context: Context) {
    this.context = context
    restaurantRepository = FirebaseRestaurantRepository(DependencyInjector.context.getSharedPreferences("shared", MODE_PRIVATE))
    orderRepository = FirebaseOrderRepository(DependencyInjector.context.getSharedPreferences("shared", MODE_PRIVATE))
    printer = PrinterFactory.getPrinter()
    printerJob = PrinterService(printer)
    printerRepository = PrinterRepository(printerJob)
    printerQueue = PrinterQueue(printerRepository)
  }

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

  fun stopQueue() {
    printerQueue.stopQueue()
  }

}
