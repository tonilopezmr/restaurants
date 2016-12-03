package com.come.restaurants.open.restaurant

import android.content.Intent
import com.come.restaurants.base.MVP
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient

class OpenRestaurantPresenter : MVP.Presenter<OpenRestaurantPresenter.View>,
        GoogleApiClient.OnConnectionFailedListener {

    interface View : MVP.View {
        fun showConnectionError()
        fun showLoginError()
        fun showNameError()
        fun showCodeError()
        fun showNameAndCodeError()
        fun navigateToOrderList()
        fun signIn(intent: Intent)
    }

    lateinit private var view: View

    override fun init() {
        view.initUi()
    }

    override fun setView(view: MVP.View) {
        this.view = view as View
    }

    fun open() {
        view.navigateToOrderList()
    }

    fun createSignInIntent(apiClient: GoogleApiClient) {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(apiClient)
        view.signIn(signInIntent)
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        view.showConnectionError()
    }
}

