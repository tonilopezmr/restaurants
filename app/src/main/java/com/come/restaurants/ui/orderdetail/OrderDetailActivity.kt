package com.come.restaurants.ui.orderdetail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.come.restaurants.R
import com.come.restaurants.order.Order
import com.come.restaurants.order.persistence.stubs.StubOrderRepository
import com.come.restaurants.order.usecases.GetOrder
import kotlinx.android.synthetic.main.activity_order_detail.*

class OrderDetailActivity : AppCompatActivity(), OrderDetailPresenter.View {
    companion object {
        val NUMBER: String = "ORDER_NUMBER"
        val ID: String = "ORDER_ID"
    }

    private lateinit var presenter : OrderDetailPresenter

    override fun showDetails(details: Order) {
        orderNumberTextView.text = intent.getStringExtra(NUMBER)
        orderTextView.text = details.orderLines.fold("",
                {total, current -> total.plus(
                        "${current.quantity}x ${current.plate.name} \t${current.getPrice()}€\n"
                )})
        totalPriceTextView.text = "${details.getPrice()}€"
    }

    override fun showError() {
        val toast = Toast.makeText(applicationContext,
                "Error ocurred while fetching order details", 3)
        toast.show()
    }

    override fun initUi() {
        orderNumberTextView.text = "Fetching order..."
        orderTextView.text = ""
        totalPriceTextView.text = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        var repository = StubOrderRepository()
        val getOrder = GetOrder(repository)
        val orderId = intent.getStringExtra(ID)

        this.presenter = OrderDetailPresenter(getOrder, orderId)
        this.presenter.setView(this)
        this.presenter.init()
    }
}
