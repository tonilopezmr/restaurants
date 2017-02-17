package com.come.restaurants.order.domain.usecases

import com.come.restaurants.base.BaseCallback
import com.come.restaurants.order.domain.OrderRepository
import com.come.restaurants.order.domain.model.Order

class GetOrder(val repository: OrderRepository) {
  interface Callback : BaseCallback {
    fun orderReceived(order: Order)
  }

  fun get(id: String, callback: Callback) {
    repository.getOrder(id, callback)
  }

}