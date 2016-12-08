package com.come.restaurants.open.restaurant.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.come.restaurants.R
import com.come.restaurants.open.restaurant.OpenRestaurantPresenter
import com.come.restaurants.open.restaurant.OpenRestaurantPresenter.View
import com.come.restaurants.order.list.ui.OrderListActivity
import com.come.restaurants.restaurant.domain.usecases.Login
import com.come.restaurants.restaurant.persistence.stubs.StubRestaurantRepository
import kotlinx.android.synthetic.main.activity_open_restaurant.*

class OpenRestaurantActivity : AppCompatActivity(), View {

  private lateinit var presenter: OpenRestaurantPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_open_restaurant)

    val restaurantRepository = StubRestaurantRepository()
    val login = Login(restaurantRepository)
    this.presenter = OpenRestaurantPresenter(login)
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
    Toast.makeText(applicationContext, getString(R.string.connection_error),
            Toast.LENGTH_SHORT).show()
  }

  override fun showLoginError() {
    Toast.makeText(applicationContext, getString(R.string.login_error), Toast.LENGTH_SHORT).show()
  }

  override fun showNameError() {
    Toast.makeText(applicationContext, getString(R.string.invalid_name), Toast.LENGTH_SHORT).show()
  }

  override fun showCodeError() {
    Toast.makeText(applicationContext, getString(R.string.invalid_code), Toast.LENGTH_SHORT).show()
  }

  override fun showNameAndCodeError() {
    Toast.makeText(applicationContext, getString(R.string.invalid_name_and_code),
            Toast.LENGTH_SHORT).show()
  }

  override fun initUi() {
    openButton.setOnClickListener { navigateToOrderList() }
  }
}
