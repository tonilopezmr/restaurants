package com.come.restaurants.order.list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.come.restaurants.DI.DependencyInjector
import com.come.restaurants.R
import com.come.restaurants.order.detail.OrderDetailActivity
import com.come.restaurants.order.domain.model.Order
import com.come.restaurants.order.list.ui.OrderListUI
import com.come.restaurants.order.list.ui.adapter.OrderListAdapter
import com.come.restaurants.printer.service.PrinterQueueService
import kotlinx.android.synthetic.main.activity_list.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.setContentView

class OrderListActivity : AppCompatActivity(), OrderListPresenter.View {

  companion object {
    fun launch(activity: Activity) {
      val intent = Intent(activity, OrderListActivity::class.java)
      intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
      activity.startActivity(intent)
    }
  }

  private lateinit var adapter: OrderListAdapter
  private lateinit var presenter: OrderListPresenter

  override fun showLoader() {
    progressBar.visibility = View.VISIBLE
    emptyCase.visibility = View.GONE
  }

  override fun close() {
    val intent: Intent = Intent(this, PrinterQueueService::class.java)
    intent.putExtra("action", PrinterQueueService.CLOSE_SERVICE)
    startService(intent)
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

  override fun showGetNewOrderError() {
    Toast.makeText(applicationContext,
        getString(R.string.error_getting_new_order), Toast.LENGTH_SHORT).show()
  }

  override fun showGetOrdersError() {
    Toast.makeText(applicationContext,
        getString(R.string.error_getting_orders), Toast.LENGTH_SHORT).show()
  }

  override fun initUi() {
    emptyCase.text = String.format(getString(R.string.there_are_not), getString(R.string.orders))
    this.adapter = OrderListAdapter({
      OrderDetailActivity.launch(this@OrderListActivity, it.id)
    })
    recyclerView.adapter = this.adapter
    recyclerView.layoutManager = LinearLayoutManager(this)
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.order_list_menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    if (item?.itemId == R.id.action_close) {
      onBackPressed()
    }

    return super.onOptionsItemSelected(item)
  }

  override fun finish() {
    DependencyInjector.removeListeners()
    super.finish()
  }

  override fun onBackPressed() {
    alert(getString(R.string.do_you_want_close)) {
      title(getString(R.string.close_restaurant))
      yesButton {
        presenter.close()
        super.onBackPressed()
      }
      noButton { }
    }.show()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    OrderListUI().setContentView(this)

    val getOrders = DependencyInjector.getOrders()
    val printOrder = DependencyInjector.getPrintOrder()
    val printWelcome = DependencyInjector.getWelcome()
    val getNewOrder = DependencyInjector.getNewOrder()

    this.presenter = OrderListPresenter(getOrders, printOrder, printWelcome, getNewOrder)
    this.presenter.setView(this)
    this.presenter.init()
    val intent: Intent = Intent(this, PrinterQueueService::class.java)
    intent.putExtra("action", PrinterQueueService.START_SERVICE)
    startService(intent)
    //DependencyInjector.startQueue()
  }
}
