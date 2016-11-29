package com.come.restaurants.ui.list

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.come.restaurants.R
import com.come.restaurants.order.Order
import com.come.restaurants.order.OrderLine
import com.come.restaurants.order.Plate
import com.come.restaurants.ui.list.adapter.OrderListAdapter
import java.util.*

class ActivityOrderList : AppCompatActivity(), OrderListView {
    override fun showLoader() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoader() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showEmptyCase() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showList(orders: List<Order>) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)

        val orderList = ArrayList<Order>()
        var orderLines = ArrayList<OrderLine>()
        val examplePlate = Plate("Plato", "Chivito", 5.0, emptyList())

        orderLines.add(OrderLine("Linea", examplePlate, 2))
        orderList.add(Order("Bla", "Bla bla", Date().time, orderLines))

        val adapter = OrderListAdapter(orderList)
    }
}
