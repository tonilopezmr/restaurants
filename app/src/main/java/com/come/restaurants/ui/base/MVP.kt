package com.come.restaurants.ui.base

public interface MVP {

    interface View {
        fun initUi()
    }

    interface Presenter<T : View> {
        fun init()
        fun setView(view : View)
        fun onDestroy()
    }

}

