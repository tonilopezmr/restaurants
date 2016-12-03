package com.come.restaurants.order.detail

import com.come.restaurants.order.domain.model.Order
import com.come.restaurants.order.domain.usecases.GetOrder
import com.come.restaurants.base.MVP
import com.come.restaurants.order.domain.usecases.PrintOrder
import com.come.restaurants.printer.PrinterRepository


class OrderDetailPresenter(val getOrder: GetOrder) : MVP.Presenter<OrderDetailPresenter.View> {

    interface View : MVP.View {
        fun showDetails(details : Order)
        fun showError()
        fun showPrintError()
        fun showOrderPrinted()
    }

    lateinit private var view : View
    lateinit private var printerRepository : PrinterRepository
    lateinit private var _order : Order

    override fun init() {
        view.initUi()
        printerRepository = PrinterRepository()
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
                _order = order
            }

            override fun error(exception: Exception) {
                view.showError()
            }

        })
    }

    private fun show(details: Order?) {
        if(details == null) {
            view.showError()
        } else {
            view.showDetails(details)
        }
    }

    public fun print() {
        var printOrder = PrintOrder(printerRepository)
        printOrder.print(_order, object : PrintOrder.Callback{
            override fun orderPrinted() {
                view.showOrderPrinted()
            }

            override fun error(exception: Exception) {
                view.showPrintError()
            }
        })
    }
}