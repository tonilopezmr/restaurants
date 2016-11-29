package com.come.restaurants.ui.list

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.come.restaurants.R
import com.come.restaurants.order.Order
import com.come.restaurants.ui.list.adapter.OrderListAdapter

class OrderListActivity : AppCompatActivity(), OrderListPresenter.View {

    val recyclerView : RecyclerView = findViewById(R.id.ordersRecyclerView) as RecyclerView
    val adapter : OrderListAdapter = OrderListAdapter()

    override fun showLoader() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoader() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showEmptyCase() {
        val toast = Toast.makeText(applicationContext, "Empty list", 3)
        toast.show()
    }

    override fun showList(orders: List<Order>) {
        adapter.addAll(orders)
    }

    override fun initUi() {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)
    }
}
