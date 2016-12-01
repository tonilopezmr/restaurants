package com.come.restaurants.ui.startscreen

import com.come.restaurants.ui.base.MVP

class StartScreenPresenter : MVP.Presenter<StartScreenPresenter.View> {

    interface View : MVP.View {
        fun showConnectionError()
        fun showNameError()
        fun showCodeError()
        fun showNameAndCodeError()
    }

    lateinit private var view : View

    override fun init() {
        view.initUi()
    }

    override fun setView(view: MVP.View) {
        this.view = view as View
    }

    fun login(){
        // This method will be implemented later
    }
}

