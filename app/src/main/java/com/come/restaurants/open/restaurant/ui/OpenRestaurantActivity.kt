package com.come.restaurants.open.restaurant.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.come.restaurants.R
import com.come.restaurants.open.restaurant.OpenRestaurantPresenter
import com.come.restaurants.open.restaurant.OpenRestaurantPresenter.View
import com.come.restaurants.order.list.ui.OrderListActivity
import kotlinx.android.synthetic.main.activity_open_restaurant.*

class OpenRestaurantActivity : AppCompatActivity(), View {

    private lateinit var presenter: OpenRestaurantPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_restaurant)

        this.presenter = OpenRestaurantPresenter()
        this.presenter.setView(this)
        this.presenter.init(applicationContext, this)
    }

    override fun launchSignIn(intent: Intent) {
        startActivityForResult(intent, OpenRestaurantPresenter.RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        this.presenter.checkSignIn(requestCode, resultCode, data)
    }

    override fun navigateToOrderList() {
        startActivity(Intent(this, OrderListActivity::class.java))
        finish()
    }

    override fun showConnectionError() {
        val toast = Toast.makeText(applicationContext, getString(R.string.connection_error), 3)
        toast.show()
    }

    override fun showLoginError() {
        val toast = Toast.makeText(applicationContext, getString(R.string.login_error), 3)
        toast.show()
    }

    override fun showNameError() {
        val toast = Toast.makeText(applicationContext, getString(R.string.invalid_name), 3)
        toast.show()
    }

    override fun showCodeError() {
        val toast = Toast.makeText(applicationContext, getString(R.string.invalid_code), 3)
        toast.show()
    }

    override fun showNameAndCodeError() {
        val toast = Toast.makeText(applicationContext, "Invalid Name and Code", 3)
        toast.show()
    }

    override fun initUi() {
        openButton.setOnClickListener { this.presenter.signIn() }
    }
}
