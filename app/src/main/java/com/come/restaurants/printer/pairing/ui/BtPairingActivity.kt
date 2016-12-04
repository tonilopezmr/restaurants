package com.come.restaurants.printer.pairing.ui

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.come.restaurants.R
import com.come.restaurants.printer.domain.model.BtDevice
import com.come.restaurants.printer.pairing.BtPairingPresenter
import com.come.restaurants.printer.pairing.ui.adapter.BtPairingAdapter
import kotlinx.android.synthetic.main.activity_bt_pairing.*

class BtPairingActivity : AppCompatActivity(), BtPairingPresenter.View {

    private val REQUEST_ENABLE_BT : Int = 1

    private lateinit var presenter: BtPairingPresenter
    private lateinit var adapter: BtPairingAdapter


    override fun turnOnBtMessage() {
        val toast = Toast.makeText(applicationContext, "Turn on bluetooth", Toast.LENGTH_LONG)
        toast.show()
    }

    override fun bluetoothNotSupportedMessage() {
        val toast = Toast.makeText(applicationContext,
                getString(R.string.bluetooth_not_supported), Toast.LENGTH_LONG)
        toast.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bt_pairing)

        this.presenter = BtPairingPresenter()
        this.presenter.view = this
        this.presenter.init()
    }

    override fun onDestroy() {
        this.presenter.finish()
    }

    override fun setReceiver(receiver: BroadcastReceiver, filter: IntentFilter) {
        registerReceiver(receiver, filter)
    }

    override fun unsetReceiver(receiver: BroadcastReceiver) {
        unregisterReceiver(receiver)
    }

    override fun showList(printers: List<BtDevice>) {
        this.adapter.addAll(printers)
    }

    override fun initUi() {
        this.adapter = BtPairingAdapter()
        printersRecyclerView.adapter = this.adapter
        printersRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun requestEnableBluetooth(btEnableIntent : Intent) {
        startActivityForResult(btEnableIntent, REQUEST_ENABLE_BT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_ENABLE_BT) {
            if(resultCode == RESULT_OK)
                presenter.startDiscovery()
            else if (resultCode == RESULT_CANCELED)
                turnOnBtMessage()
        }
    }
}
