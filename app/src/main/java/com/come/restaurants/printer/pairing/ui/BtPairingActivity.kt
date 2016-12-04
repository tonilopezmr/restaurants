package com.come.restaurants.printer.pairing.ui

import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
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
    private lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bt_pairing)

        this.presenter = BtPairingPresenter()
        this.presenter.view = this
        this.presenter.init()
    }

    override fun onDestroy() {
        this.presenter.finish()
        super.onDestroy()
    }

    override fun turnOnBtMessage() {
        val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        startActivityForResult(intent, 1000)
    }

    override fun showProgressDialog() {
        this.progressDialog = ProgressDialog(this)
        this.progressDialog.setMessage(getString(R.string.scanning_bt))
        this.progressDialog.setCancelable(false)
        this.progressDialog.show()

    }

    override fun setReceiver(receiver: BroadcastReceiver, filter: IntentFilter) {
        registerReceiver(receiver, filter)
    }

    override fun unsetReceiver(receiver: BroadcastReceiver) {
        unregisterReceiver(receiver)
    }

    override fun showList(printers: List<Printer>) {
        this.progressDialog.dismiss()
        this.adapter.addAll(printers)
    }

    override fun initUi() {
        this.adapter = BtPairingAdapter()
        printersRecyclerView.adapter = this.adapter
        printersRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}
