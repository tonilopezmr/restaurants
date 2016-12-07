package com.come.restaurants.order.detail

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.come.restaurants.R
import com.come.restaurants.order.domain.model.Order
import com.come.restaurants.order.domain.usecases.GetOrder
import com.come.restaurants.order.domain.usecases.PrintOrder
import com.come.restaurants.order.persistence.stubs.StubOrderRepository
import com.come.restaurants.printer.domain.PrinterRepository
import com.come.restaurants.printer.pairing.ui.PairingPrinterActivity
import kotlinx.android.synthetic.main.activity_order_detail.*

class OrderDetailActivity : AppCompatActivity(), OrderDetailPresenter.View {
    companion object {
        val NUMBER: String = "ORDER_NUMBER"
        val ID: String = "ORDER_ID"
    }

    private lateinit var presenter : OrderDetailPresenter

    override fun showDetails(details: Order) {
        orderNumberTextView.text = getString(R.string.number) + " " + intent.getStringExtra(NUMBER)
        orderTextView.text = details.orderLines.fold("",
                {total, current -> total.plus(
                        "${current.quantity}x ${current.plate.name} \t${current.getPrice()}€\n"
                )})
        totalPriceTextView.text = "${details.getPrice()}€"
    }

    override fun showFetchingError() {
        val toast = Toast.makeText(applicationContext,
                "Error ocurred while fetching order details", 3)
        toast.show()
    }

    override fun showPrintError() {
        val toast = Toast.makeText(applicationContext,
                "Error ocurred while printing order details", 3)
        toast.show()
    }

    override fun showOrderPrinted() {
        val toast = Toast.makeText(applicationContext,
                "Order details printed", 3)
        toast.show()
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

        var repository = StubOrderRepository()
        val getOrder = GetOrder(repository)
        val orderId = intent.getStringExtra(ID)

        var printerRepository = PrinterRepository()
        val printOrder = PrintOrder(printerRepository)

        this.presenter = OrderDetailPresenter(getOrder, printOrder)
        this.presenter.setView(this)
        this.presenter.init(orderId)
    }

    override fun moveToPairingActivity() {
        val intent = Intent(this, PairingPrinterActivity::class.java)
        startActivity(intent)
    }
}
