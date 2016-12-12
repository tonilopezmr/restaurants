package com.come.restaurants.order.list

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.come.restaurants.R
import com.come.restaurants.order.domain.model.Order
import com.come.restaurants.order.domain.usecases.GetNewOrder
import com.come.restaurants.order.domain.usecases.GetOrders
import com.come.restaurants.order.domain.usecases.PrintOrder
import com.come.restaurants.order.list.ui.OrderListUI
import com.come.restaurants.order.list.ui.adapter.OrderListAdapter
import com.come.restaurants.order.persistence.network.FirebaseOrderRepository
import com.come.restaurants.printer.domain.PrinterRepository
import com.come.restaurants.printer.domain.usecases.PrintWelcome
import com.come.restaurants.printer.service.PrinterFactory
import com.come.restaurants.printer.service.PrinterService
import kotlinx.android.synthetic.main.activity_list.*
import org.jetbrains.anko.setContentView

class OrderListActivity : AppCompatActivity(), OrderListPresenter.View {
  private lateinit var adapter: OrderListAdapter

  private lateinit var presenter: OrderListPresenter

  override fun showLoader() {
    progressBar.visibility = View.VISIBLE
    emptyCase.visibility = View.GONE
  }

  override fun close() {
    finish() //at the moment
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

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.order_list_menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    if (item?.itemId == R.id.action_close) {
      presenter.close()
    }
    return true
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    OrderListUI().setContentView(this)

    val repository = FirebaseOrderRepository()
    val getOrders = GetOrders(repository)
    val printer = PrinterFactory.getPrinter()
    val printerJob = PrinterService(printer)
    var printerRepository = PrinterRepository(printerJob)
    val printOrder = PrintOrder(printerRepository)

    val printWelcome = PrintWelcome(printerRepository)

    this.presenter = OrderListPresenter(getOrders, printOrder, printWelcome, GetNewOrder(repository))
    this.presenter.setView(this)
    this.presenter.init()
  }
}
