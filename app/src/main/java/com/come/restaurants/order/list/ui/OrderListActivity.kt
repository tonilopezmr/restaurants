package com.come.restaurants.order.list.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.come.restaurants.R
import com.come.restaurants.order.domain.model.Order
import com.come.restaurants.order.domain.usecases.GetNewOrder
import com.come.restaurants.order.domain.usecases.GetOrders
import com.come.restaurants.order.domain.usecases.PrintOrder
import com.come.restaurants.order.list.OrderListPresenter
import com.come.restaurants.order.list.ui.adapter.OrderListAdapter
import com.come.restaurants.order.persistence.network.FirebaseOrderRepository
import com.come.restaurants.printer.domain.PrinterRepository
import com.come.restaurants.printer.service.PrinterJobImpl
import com.come.restaurants.printer.service.bluetooth.PrinterBluetooth
import kotlinx.android.synthetic.main.activity_list.*

class OrderListActivity : AppCompatActivity(), OrderListPresenter.View {

  private lateinit var adapter: OrderListAdapter
  private lateinit var presenter: OrderListPresenter

  override fun showLoader() {
    progressBar.visibility = View.VISIBLE
    emptyCase.visibility = View.GONE
  }

  override fun hideLoader() {
    progressBar.visibility = View.GONE
  }

  override fun showEmptyCase() {
    emptyCase.visibility = View.VISIBLE
    progressBar.visibility = View.GONE
  }

  override fun showList(orders: List<Order>) {
    adapter.addAll(orders)
    emptyCase.visibility = View.GONE
    progressBar.visibility = View.GONE
  }

  override fun initUi() {
    emptyCase.text = String.format(getString(R.string.there_are_not), getString(R.string.orders))
    this.adapter = OrderListAdapter()
    recyclerView.adapter = this.adapter
    recyclerView.layoutManager = LinearLayoutManager(this)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_list)

    val repository = FirebaseOrderRepository()
    val getOrders = GetOrders(repository)
    val printer = PrinterBluetooth.getPrinter()
    val printerJob = PrinterJobImpl(printer)
    var printerRepository = PrinterRepository(printerJob)
    val printOrder = PrintOrder(printerRepository)

    this.presenter = OrderListPresenter(getOrders, printOrder, GetNewOrder(repository))
    this.presenter.setView(this)
    this.presenter.init()
  }
}
