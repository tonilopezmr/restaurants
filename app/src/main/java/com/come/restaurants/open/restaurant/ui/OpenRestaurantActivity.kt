package com.come.restaurants.open.restaurant.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.come.restaurants.R
import com.come.restaurants.open.restaurant.OpenRestaurantPresenter
import com.come.restaurants.open.restaurant.OpenRestaurantPresenter.View
import com.come.restaurants.order.list.ui.OrderListActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_open_restaurant.*

class OpenRestaurantActivity : AppCompatActivity(), View {

    private val RC_SIGN_IN = 9001
    private lateinit var presenter: OpenRestaurantPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_restaurant)

        this.presenter = OpenRestaurantPresenter()
        this.presenter.setView(this)
        this.presenter.init()

        this.loginGoogle()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if(result.isSuccess) {
                val account = result.signInAccount
                this.firebaseAuthWithGoogle(account)
            }
        }
    }

    fun loginGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.oauth_token))
                .requestEmail()
                .build()

        val apiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this, this.presenter)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()

        val mAuthListener = FirebaseAuth.AuthStateListener { auth ->
            val user = auth.currentUser
            if(user != null) {

            } else {

            }
        }

        openButton.setOnClickListener { presenter.createSignInIntent(apiClient) }
    }

    fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        presenter.open()
                    } else {
                        this.showLoginError()
                    }
                }

    }

    override fun signIn(intent: Intent) {
        startActivityForResult(intent, RC_SIGN_IN)
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

    }
}
