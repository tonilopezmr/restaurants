package com.come.restaurants.open.restaurant.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.Toast
import com.come.restaurants.R
import com.come.restaurants.open.restaurant.LoginRestaurantPresenter
import com.come.restaurants.open.restaurant.LoginRestaurantPresenter.View
import com.come.restaurants.printer.pairing.ChoosePairingActivity
import com.come.restaurants.restaurant.domain.usecases.Login
import com.come.restaurants.restaurant.persistence.network.FirebaseRestaurantRepository
import kotlinx.android.synthetic.main.activity_login_restaurant.*

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

class LoginRestaurantActivity : AppCompatActivity(), View {

  private lateinit var presenter: LoginRestaurantPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Fabric.with(this, Crashlytics())

    supportActionBar?.hide()
    window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN)
    setContentView(R.layout.activity_login_restaurant)

    val restaurantRepository = FirebaseRestaurantRepository()
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

  override fun showLoader() {
    progressBar.visibility = android.view.View.VISIBLE
  }

  override fun hideLoader() {
    progressBar.visibility = android.view.View.GONE
  }

  override fun initUi() {

    openButton.setOnClickListener {
      this.presenter.signIn(
        nameEditText.text.toString(),
        textPassword.text.toString())
    }

  }
}
