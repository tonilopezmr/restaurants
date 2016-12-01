package com.come.restaurants.startscreen

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.come.restaurants.R
import com.come.restaurants.startscreen.StartScreenPresenter.View

class StartScreenActivity : AppCompatActivity(), View
{
    private lateinit var presenter : StartScreenPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)

        this.presenter = StartScreenPresenter()
        this.presenter.setView(this)
        this.presenter.init()
    }

    override fun showConnectionError() {
        val toast = Toast.makeText(applicationContext, "Connection error", 3)
        toast.show()
    }

    override fun showNameError() {
        val toast = Toast.makeText(applicationContext, "Invalid name", 3)
        toast.show()
    }

    override fun showCodeError() {
        val toast = Toast.makeText(applicationContext, "Invalid Code", 3)
        toast.show()
    }

    override fun showNameAndCodeError() {
        val toast = Toast.makeText(applicationContext, "Invalid Name and Code", 3)
        toast.show()
    }

    override fun initUi() {

    }
}
