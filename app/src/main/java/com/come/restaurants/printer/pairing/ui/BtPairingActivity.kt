package com.come.restaurants.printer.pairing.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.come.restaurants.R
import com.come.restaurants.printer.domain.model.Printer
import com.come.restaurants.printer.pairing.BtPairingPresenter
import com.come.restaurants.printer.pairing.ui.adapter.BtPairingAdapter
import kotlinx.android.synthetic.main.activity_bt_pairing.*

class BtPairingActivity : AppCompatActivity(), BtPairingPresenter.View {

    private lateinit var presenter: BtPairingPresenter
    private lateinit var adapter: BtPairingAdapter

    override fun turnOnBtMessage() {
        val toast = Toast.makeText(applicationContext, "Turn on bluetooth", 3)
        toast.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bt_pairing)

        this.presenter = BtPairingPresenter()
        this.presenter.view = this
        this.presenter.init()
    }

    override fun showList(printers: List<Printer>) {
        this.adapter.addAll(printers)
    }

    override fun initUi() {
        this.adapter = BtPairingAdapter()
        printersRecyclerView.adapter = this.adapter
        printersRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}
