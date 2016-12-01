package com.come.restaurants.ui.orderdetail

import com.come.restaurants.order.Order
import com.come.restaurants.order.usecases.GetOrder
import com.come.restaurants.ui.base.MVP


class OrderDetailPresenter(val getOrder: GetOrder) : MVP.Presenter<OrderDetailPresenter.View> {

    lateinit var orderId: String

    interface View : MVP.View {
        fun showDetails(details : Order)
        fun showError()
    }

    lateinit private var view : View

    override fun init() {
        view.initUi()
        this.requestDetails()
    }

    fun init(orderId: String) {
        this.orderId = orderId
        this.init()
    }

    override fun setView(view: MVP.View) {
        this.view = view as View
    }

    private fun requestDetails() {
        this.getOrder.get(this.orderId, object : GetOrder.Callback {
            override fun orderReceived(order: Order) {
                receivedDetails(order)
            }

            override fun error(exception: Exception) {
                view.showError()
            }

        })

    }

    private fun receivedDetails(details: Order?) {
        if(details == null) {
            view.showError()
        } else {
            view.showDetails(details)
        }
    }
}