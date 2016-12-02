package com.come.restaurants.printer.pairing.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.come.restaurants.R
import com.come.restaurants.printer.domain.model.Printer
import com.come.restaurants.printer.pairing.BtPairingPresenter

class BtPairingActivity : AppCompatActivity(), BtPairingPresenter.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bt_pairing)
    }

    override fun show(printers: List<Printer>) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initUi() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
