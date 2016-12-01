package com.come.restaurants.ui.base

interface MVP {

    interface View {
        fun initUi()
    }

    interface Presenter<T : View> {
        fun init()
        fun setView(view : View)
    }

}

