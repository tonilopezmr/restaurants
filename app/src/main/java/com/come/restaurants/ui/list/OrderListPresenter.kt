package com.come.restaurants.ui.list

import com.come.restaurants.order.Order
import com.come.restaurants.order.usecases.GetOrders
import com.come.restaurants.ui.base.MVP


class OrderListPresenter(val getOrders: GetOrders) : MVP.Presenter<OrderListPresenter.View> {

    interface View : MVP.View {
        fun showLoader()
        fun hideLoader()
        fun showEmptyCase()
        fun showList(orders: List<Order>)
    }

    lateinit private var view : View

    override fun init() {
        view.initUi()
        view.showLoader()
        requestOrders()
    }

    override fun setView(view: MVP.View) {
        this.view = view as View
    }

    override fun onDestroy() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun requestOrders() {
        val list = this.getOrders.get()
        receivedOrders(list)
    }

    fun receivedOrders(orderList: List<Order>) {
        if(orderList.isEmpty()) {
            view.showEmptyCase()
        } else {
            view.showList(orderList)
        }
    }



}