package com.come.restaurants.order.list

import com.come.restaurants.BuildConfig
import com.come.restaurants.base.MVP
import com.come.restaurants.order.domain.model.Order
import com.come.restaurants.order.domain.usecases.GetNewOrder
import com.come.restaurants.order.domain.usecases.GetOrders
import com.come.restaurants.order.domain.usecases.PrintOrder
import com.come.restaurants.printer.domain.usecases.PrintWelcome


class OrderListPresenter(val getOrders: GetOrders,
                         val printOrder: PrintOrder,
                         val printWelcome: PrintWelcome,
                         val getNewOrder: GetNewOrder) : MVP.Presenter<OrderListPresenter.View> {

  interface View : MVP.View {
    fun showLoader()
    fun hideLoader()
    fun showEmptyCase()
    fun showList(orders: List<Order>)
    fun close()
    fun showGetNewOrderError()
    fun showGetOrdersError()
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
        /*printOrder.print(order, object : PrintOrder.Callback {
          override fun error(exception: Exception) {
            //TODO error do nothing at the moment
          }

          override fun orderPrinted(order: Order) {
            //TODO order printed do nothing at the moment
          }
        })*/
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
    view.close()
  }

}