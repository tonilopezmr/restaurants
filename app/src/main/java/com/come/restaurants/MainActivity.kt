package com.come.restaurants

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.come.restaurants.order.Order
import com.come.restaurants.order.OrderLine
import com.come.restaurants.order.Plate
import com.come.restaurants.ui.list.OrderListActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val orderList = ArrayList<Order>()
        var orderLines = ArrayList<OrderLine>()
        val examplePlate = Plate("Plato", "Chivito", 5.0, emptyList())

        orderLines.add(OrderLine("Linea", examplePlate, 1))
        orderList.add(Order("Bla", "Bla bla", Date().time, orderLines))

        val intent = Intent(this, OrderListActivity::class.java)
        startActivity(intent)
    }
}
