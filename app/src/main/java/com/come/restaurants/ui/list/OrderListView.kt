package com.come.restaurants.ui.list

import com.come.restaurants.order.Order

interface OrderListView {
    // fun showError()
    fun showLoader()
    fun hideLoader()
    fun showEmptyCase()
    fun showList(orders: List<Order>)
}