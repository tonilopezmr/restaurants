package com.come.restaurants

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.come.restaurants.order.Order
import com.come.restaurants.order.OrderLine
import com.come.restaurants.order.Plate
import com.come.restaurants.ui.list.adapter.ListViewAdapter
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)

        val orderList = ArrayList<Order>()
        var orderLines = ArrayList<OrderLine>()
        val examplePlate = Plate("Plato", "Chivito", 5.0, emptyList())

        orderLines.add(OrderLine("Linea", 2, examplePlate))
        orderList.add(Order("Bla", "Bla bla", Date().time, orderLines))

        val orderRecyclerView = findViewById(R.id.ordersRecyclerView) as RecyclerView
        val adapter = ListViewAdapter(orderList)
        orderRecyclerView.layoutManager = LinearLayoutManager(this)
        orderRecyclerView.adapter = adapter
    }
}
