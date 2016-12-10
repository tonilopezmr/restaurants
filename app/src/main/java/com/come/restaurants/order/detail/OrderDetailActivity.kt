package com.come.restaurants.order.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.come.restaurants.R
import com.come.restaurants.order.domain.model.Order
import com.come.restaurants.order.domain.usecases.GetOrder
import com.come.restaurants.order.domain.usecases.PrintOrder
import com.come.restaurants.order.persistence.network.FirebaseOrderRepository
import com.come.restaurants.printer.domain.PrinterRepository
import com.come.restaurants.printer.service.PrinterFactory
import com.come.restaurants.printer.service.PrinterService
import kotlinx.android.synthetic.main.activity_order_detail.*

class OrderDetailActivity : AppCompatActivity(), OrderDetailPresenter.View {
  companion object {
    val NUMBER: String = "ORDER_NUMBER"
    val ID: String = "ORDER_ID"
  }

  private lateinit var presenter: OrderDetailPresenter

  override fun showDetails(details: Order) {
    orderNumberTextView.text = getString(R.string.number) + " " + intent.getStringExtra(NUMBER)
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
    orderNumberTextView.text = getString(R.string.fetching_order)
    orderTextView.text = ""
    totalPriceTextView.text = ""

    printButton.setOnClickListener { presenter.print() }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_order_detail)

    var repository = FirebaseOrderRepository()
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
