package com.come.restaurants.ui.orderdetail

import com.come.restaurants.ui.base.MVP

interface Presenter<T: MVP.View> {      // In lack of a better name :_)
    fun init(orderId: String)
    fun setView(view: MVP.View)
}