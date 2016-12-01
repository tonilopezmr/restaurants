package com.come.restaurants.order.detail

import com.come.restaurants.order.domain.model.Order
import com.come.restaurants.order.domain.usecases.GetOrder
import com.come.restaurants.base.MVP


class OrderDetailPresenter(val getOrder: GetOrder) : MVP.Presenter<OrderDetailPresenter.View> {

    interface View : MVP.View {
        fun showDetails(details : Order)
        fun showError()
    }

    lateinit private var view : View

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
}