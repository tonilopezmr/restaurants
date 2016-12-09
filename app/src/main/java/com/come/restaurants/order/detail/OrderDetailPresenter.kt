package com.come.restaurants.order.detail

import com.come.restaurants.base.MVP
import com.come.restaurants.order.domain.model.Order
import com.come.restaurants.order.domain.usecases.GetOrder
import com.come.restaurants.order.domain.usecases.PrintOrder


class OrderDetailPresenter(val getOrder: GetOrder, val printOrder: PrintOrder) : MVP.Presenter<OrderDetailPresenter.View> {

  interface View : MVP.View {
    fun showDetails(details: Order)
    fun showFetchingError()
    fun showPrintError()
    fun showOrderPrinted()
  }

  lateinit private var view: View
  lateinit private var order: Order

  override fun init() {
    view.initUi()
  }

  fun init(orderId: String) {
    this.init()
    this.requestDetails(orderId)
  }

  override fun setView(view: MVP.View) {
    this.view = view as View
  }

  private fun requestDetails(id: String) {
    this.getOrder.get(id, object : GetOrder.Callback {
      override fun orderReceived(order: Order) {
        show(order)
        this@OrderDetailPresenter.order = order
      }

      override fun error(exception: Exception) {
        view.showFetchingError()
      }

    })
  }

  private fun show(details: Order?) {
    if (details == null) {
      view.showFetchingError()
    } else {
      view.showDetails(details)
    }
  }

  fun print() {
    this.printOrder.print(order, object : PrintOrder.Callback {
      override fun orderPrinted(order: Order) {
        //TODO send that is printed
      }

      override fun error(exception: Exception) {
        view.showPrintError()
      }

    })

  }
}