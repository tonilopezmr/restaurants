package com.come.restaurants.open.restaurant

import com.come.restaurants.base.MVP

class OpenRestaurantPresenter : MVP.Presenter<OpenRestaurantPresenter.View> {

    interface View : MVP.View {
        fun showConnectionError()
        fun showNameError()
        fun showCodeError()
        fun showNameAndCodeError()
        fun navigateToOrderList();
    }

    lateinit private var view: View

    override fun init() {
        view.initUi()
    }

    override fun setView(view: MVP.View) {
        this.view = view as View
    }

    fun open() {
        // This method will be implemented later
        view.navigateToOrderList()
    }
}

