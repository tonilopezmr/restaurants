package com.come.restaurants.open.restaurant

import android.content.Context
import android.content.Intent
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.come.restaurants.R
import com.come.restaurants.base.MVP
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class OpenRestaurantPresenter : MVP.Presenter<OpenRestaurantPresenter.View>, GoogleApiClient.OnConnectionFailedListener {
  companion object {
    val RC_SIGN_IN = 9001
  }

  private val TAG = "OpenRestaurantActivity"

  interface View : MVP.View {
    fun showConnectionError()
    fun showLoginError()
    fun showNameError()
    fun showCodeError()
    fun showNameAndCodeError()
    fun navigateToOrderList()
    fun launchSignIn(intent: Intent)
  }

  lateinit private var view: View
  lateinit private var context: Context
  lateinit private var apiClient: GoogleApiClient

  override fun init() {
    view.initUi()
  }

  fun init(context: Context, fragmentActivity: FragmentActivity) {
    this.context = context

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(this.context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    apiClient = GoogleApiClient.Builder(this.context)
        .enableAutoManage(fragmentActivity, this)
        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
        .build()

    val mAuthListener = FirebaseAuth.AuthStateListener { auth ->
      val user = auth.currentUser
      if (user != null) {
        Log.d(TAG, "onAuthStateChanged:signed_in:${user.uid}")
      } else {
        Log.d(TAG, "onAuthStateChanged:signed_out")
      }
    }

    this.init()
  }

  fun signIn() {
    val signInIntent = Auth.GoogleSignInApi.getSignInIntent(apiClient)
    view.launchSignIn(signInIntent)
  }

  fun checkSignIn(requestCode: Int, resultCode: Int, data: Intent?) {
    if (requestCode == RC_SIGN_IN) {
      val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
      if (result.isSuccess) {
        val account = result.signInAccount
        this.firebaseAuthWithGoogle(account)
      }
    }
  }

  private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
    val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
    val firebaseAuth = FirebaseAuth.getInstance()
    firebaseAuth.signInWithCredential(credential)
        .addOnCompleteListener { task ->
          if (task.isSuccessful) {
            Log.d(TAG, "signInWithCredential:onComplete:${task.isSuccessful}")
            view.navigateToOrderList()
          } else {
            Log.d(TAG, "signInWithCredential", task.exception)
            view.showLoginError()
          }
        }
  }

  override fun setView(view: MVP.View) {
    this.view = view as View
  }

  override fun onConnectionFailed(result: ConnectionResult) {
    view.showConnectionError()
  }

}

