package com.come.restaurants.ui.orderdetail

import com.come.restaurants.order.Order
import com.come.restaurants.order.usecases.GetOrder
import com.come.restaurants.ui.base.MVP


class OrderDetailPresenter(val getOrder: GetOrder) : Presenter<OrderDetailPresenter.View> {

    lateinit var orderId: String

    interface View : MVP.View {
        fun showDetails(details : Order)
        fun showError()
    }

    lateinit private var view : View

    override fun init(orderId: String) {
        this.orderId = orderId
        view.initUi()
        this.requestDetails()
    }

    override fun setView(view: MVP.View) {
        this.view = view as View
    }

    private fun requestDetails() {
        var result = this.getOrder.get(this.orderId)
        receivedDetails(result)
    }

    private fun receivedDetails(details: Order?) {
        if(details == null) {
            view.showError()
        } else {
            view.showDetails(details)
        }
    }
}