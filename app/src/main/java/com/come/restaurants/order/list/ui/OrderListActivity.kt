package com.come.restaurants.order.list.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.come.restaurants.R
import com.come.restaurants.order.domain.model.Order
import com.come.restaurants.order.list.OrderListPresenter
import com.come.restaurants.order.list.ui.adapter.OrderListAdapter
import com.come.restaurants.order.persistence.stubs.StubOrderRepository
import com.come.restaurants.order.domain.usecases.GetOrders
import kotlinx.android.synthetic.main.activity_list_view.*

class OrderListActivity : AppCompatActivity(), OrderListPresenter.View {

    private lateinit var adapter : OrderListAdapter
    private lateinit var presenter : OrderListPresenter

    override fun showLoader() {
        orderLoader.visibility = View.VISIBLE
    }

    override fun hideLoader() {
        orderLoader.visibility = View.GONE
    }

    override fun showEmptyCase() {
        val toast = Toast.makeText(applicationContext, "Empty list", 3)
        toast.show()
    }

    override fun showList(orders: List<Order>) {
        adapter.addAll(orders)
    }

    override fun initUi() {
        this.adapter = OrderListAdapter()
        ordersRecyclerView.adapter = this.adapter
        ordersRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)

        val repository = StubOrderRepository()
        val getOrders = GetOrders(repository)
        this.presenter = OrderListPresenter(getOrders)
        this.presenter.setView(this)
        this.presenter.init()
    }
}
