package com.come.restaurants

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.come.restaurants.ui.list.OrderListActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, OrderListActivity::class.java)
        startActivity(intent)
    }
}
