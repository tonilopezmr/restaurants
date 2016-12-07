package com.come.restaurants.order

import com.come.restaurants.order.domain.model.Order
import com.come.restaurants.order.domain.model.OrderLine
import com.come.restaurants.order.domain.model.Plate
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class OrderShould {

  @Test fun `get_correct_price_when_add_plate_once`() {
    val plate = Plate("", "", 1.30, emptyList())
    val orderLine = OrderLine("", plate)
    val order = Order("", "", 234, listOf(orderLine))

    assertThat(order.getPrice(), `is`(1.30))
  }

  @Test fun `get_correct_price_when_add_multiple_plates`() {
    val tortilla = Plate("", "Tortilla", 1.30, emptyList())
    val zumo = Plate("", "Zumo", 0.95, emptyList())

    val orderLine1 = OrderLine("", tortilla, 2)
    val orderLine2 = OrderLine("", zumo)
    val order = Order("", "", 234, listOf(orderLine1, orderLine2))

    assertThat(order.getPrice(), `is`(3.55))
  }

}