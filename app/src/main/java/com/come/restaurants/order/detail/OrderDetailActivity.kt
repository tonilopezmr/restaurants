package com.come.restaurants.order.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.come.restaurants.R
import com.come.restaurants.order.detail.ui.OrderDetailUI
import com.come.restaurants.order.domain.model.Order
import com.come.restaurants.order.domain.usecases.GetOrder
import com.come.restaurants.order.domain.usecases.PrintOrder
import com.come.restaurants.order.persistence.stubs.StubOrderRepository
import com.come.restaurants.printer.domain.PrinterRepository
import com.come.restaurants.printer.service.PrinterFactory
import com.come.restaurants.printer.service.PrinterService
import kotlinx.android.synthetic.main.activity_order_detail.*
import org.jetbrains.anko.setContentView

class OrderDetailActivity : AppCompatActivity(), OrderDetailPresenter.View {

  companion object {
    val ID: String = "ORDER_ID"

    fun launch(activity: Activity, id: String) {
      val intent: Intent = Intent(activity, OrderDetailActivity::class.java)
      intent.putExtra(OrderDetailActivity.ID, id)
      activity.startActivity(intent)
    }
  }

  private lateinit var presenter: OrderDetailPresenter

  override fun showDetails(details: Order) {
    orderNumberTextView.text = details.code
    orderTextView.text = details.orderLines.fold("",
        { total, current ->
          total.plus(
              "${current.quantity}x ${current.plate.name} \t${current.getPrice()}€\n"
          )
        })
    totalPriceTextView.text = "${details.getPrice()}€"
  }

  override fun showFetchingError() {
    Toast.makeText(applicationContext,
        getString(R.string.error_fetching_order_details), Toast.LENGTH_SHORT).show()
  }

  override fun showPrintError() {
    Toast.makeText(applicationContext,
        getString(R.string.error_printing_order_details), Toast.LENGTH_SHORT).show()
  }

  override fun showOrderPrinted() {
    Toast.makeText(applicationContext,
        getString(R.string.order_printed), Toast.LENGTH_SHORT).show()
  }

  override fun initUi() {
    actionBar?.setDisplayHomeAsUpEnabled(true)

    orderNumberTextView.text = getString(R.string.fetching_order)
    orderTextView.text = ""
    totalPriceTextView.text = ""

    printButton.setOnClickListener { presenter.print() }
  }


  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      android.R.id.home -> onBackPressed()
    }

    return true
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    OrderDetailUI().setContentView(this)

    var repository = StubOrderRepository()
    val getOrder = GetOrder(repository)
    val orderId = intent.getStringExtra(ID)

    val printer = PrinterFactory.getPrinter()
    val printerJob = PrinterService(printer)
    var printerRepository = PrinterRepository(printerJob)
    val printOrder = PrintOrder(printerRepository)

    this.presenter = OrderDetailPresenter(getOrder, printOrder)
    this.presenter.setView(this)
    this.presenter.init(orderId)
  }

}
