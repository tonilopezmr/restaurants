package com.come.restaurants.open.restaurant.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.come.restaurants.R
import com.come.restaurants.open.restaurant.LoginRestaurantPresenter
import com.come.restaurants.open.restaurant.LoginRestaurantPresenter.View
import com.come.restaurants.printer.pairing.ChoosePairingActivity
import com.come.restaurants.restaurant.login.ui.LoginRestaurantUI
import com.come.restaurants.restaurant.login.usecases.Login
import com.come.restaurants.restaurant.persistence.stubs.StubRestaurantRepository
import kotlinx.android.synthetic.main.activity_login_restaurant.*
import org.jetbrains.anko.setContentView

class LoginRestaurantActivity : AppCompatActivity(), View {

  private lateinit var presenter: LoginRestaurantPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    LoginRestaurantUI().setContentView(this)

    val restaurantRepository = StubRestaurantRepository()
    val login = Login(restaurantRepository)
    this.presenter = LoginRestaurantPresenter(login)
    this.presenter.setView(this)
    this.presenter.init(applicationContext, this)
  }

  override fun launchSignIn(intent: Intent) {
    startActivityForResult(intent, LoginRestaurantPresenter.RC_SIGN_IN)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    this.presenter.checkSignIn(requestCode, resultCode, data)
  }

  override fun moveToChoosePairing() {
    ChoosePairingActivity.launch(this)
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
    //this.presenter.signIn(
//    nameEditText.text.toString(),
//    textPassword.text.toString())
    openButton.setOnClickListener { moveToChoosePairing() }

    openGoogleButton.setOnClickListener { this.presenter.signInGoogle() }
  }
}
