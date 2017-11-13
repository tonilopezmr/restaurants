package com.come.restaurants.order.domain.usecases

import com.come.restaurants.base.BaseCallback
import com.come.restaurants.order.domain.OrderRepository
import com.come.restaurants.order.domain.model.Order

class GetNewOrder(private val repository: OrderRepository) {
  interface Callback : BaseCallback {
    fun orderReceived(order: Order)
  }

  fun get(callback: Callback) {
    repository.getNewOrder(callback)
  }

}
