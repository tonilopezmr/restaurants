package com.come.restaurants.order.list

import com.come.restaurants.BuildConfig
import com.come.restaurants.DI.DependencyInjector
import com.come.restaurants.base.MVP
import com.come.restaurants.order.domain.model.Order
import com.come.restaurants.order.domain.usecases.GetNewOrder
import com.come.restaurants.order.domain.usecases.GetOrders
import com.come.restaurants.order.domain.usecases.PrintOrder
import com.come.restaurants.printer.domain.usecases.PrintWelcome
import com.come.restaurants.printer.service.PrinterFactory
import com.come.restaurants.restaurant.domain.usecases.Close
import com.come.restaurants.restaurant.domain.usecases.Open
import com.come.restaurants.restaurant.login.UserProvider


class OrderListPresenter(val getOrders: GetOrders,
                         val printOrder: PrintOrder,
                         val printWelcome: PrintWelcome,
                         val getNewOrder: GetNewOrder,
                         val openRestaurant: Open,
                         val closeRestaurant: Close) : MVP.Presenter<OrderListPresenter.View> {

  interface View : MVP.View {
    fun showLoader()
    fun hideLoader()
    fun showEmptyCase()
    fun showList(orders: List<Order>)
    fun close()
    fun showGetNewOrderError()
    fun showGetOrdersError()
    fun showErrorCantOpenRestaurant()
    fun showErrorCantCloseRestaurant()
  }

  lateinit private var view: View
  override fun init() {
    view.initUi()
    view.showLoader()
    requestOrders()
    requestNewOrder()
    printWelcome()
  }

  private fun printWelcome() {
    if (!BuildConfig.DEBUG) {
      printWelcome.print()
    }
  }

  private fun requestNewOrder() {
    getNewOrder.get(object : GetNewOrder.Callback {
      override fun error(exception: Exception) {
        view.showGetNewOrderError()
      }

      override fun orderReceived(order: Order) {
        show(listOf(order))
        printOrder.print(order, object : PrintOrder.Callback {
          override fun error(exception: Exception) {
            //TODO error do nothing at the moment
          }

          override fun orderPrinted(order: Order) {
            //TODO order printed do nothing at the moment
          }
        })
      }

    })
  }

  override fun setView(view: MVP.View) {
    this.view = view as View
  }

  fun requestOrders() {
    getOrders.getOrders(object : GetOrders.Callback {
      override fun ordersReceived(orders: List<Order>) {
        show(orders)
        openRestaurant.open(UserProvider.user, object : Open.Callback {
          override fun success() {

          }

          override fun fail() {
            openRestaurant.open(UserProvider.user, object : Open.Callback {
              override fun success() {
              }

              override fun fail() {
                view.showErrorCantOpenRestaurant()
              }

            })
          }

        })
      }

      override fun error(exception: Exception) {
        view.showGetNewOrderError()
      }

    })
  }

  fun show(orderList: List<Order>) {
    view.hideLoader()
    if (orderList.isEmpty()) {
      view.showEmptyCase()
    } else {
      view.showList(orderList)
    }
  }

  fun close() {
    view.showLoader()
    closeRestaurant.close(UserProvider.user, object : Close.Callback {
      override fun success() {
        PrinterFactory.getPrinter().disconnect()
        DependencyInjector.stopQueue()
        UserProvider.isLogged = false
        view.hideLoader()
        view.close()
      }

      override fun fail() {
        view.hideLoader()
        view.showErrorCantCloseRestaurant()
      }
    })
  }

}