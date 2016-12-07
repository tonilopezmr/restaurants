package com.come.restaurants.printer.pairing.ui

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.come.restaurants.R
import com.come.restaurants.printer.pairing.PairingPresenter
import com.come.restaurants.printer.pairing.ui.adapter.BluetoothDeviceAdapter
import kotlinx.android.synthetic.main.activity_list.*

class PairingPrinterActivity : AppCompatActivity(), PairingPresenter.View {
    private val REQUEST_COARSE_LOCATION_PERMISSIONS = 2000
    private val REQUEST_OPEN_BLUETOOTH = 1000

    private lateinit var presenter: PairingPresenter
    private lateinit var adapter: BluetoothDeviceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        this.presenter = PairingPresenter()
        this.presenter.setView(this)
        this.presenter.init()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            REQUEST_COARSE_LOCATION_PERMISSIONS -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    this.presenter.doDiscovery()
                } else {
                    this.showPermissionError()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when(requestCode) {
            REQUEST_OPEN_BLUETOOTH -> {
                if(resultCode == Activity.RESULT_OK){
                    this.presenter.doDiscovery()
                } else {
                    emptyCase() //TODO SHOW A BUTTON TO START BLUETOOTH
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onDestroy() {
        this.presenter.finish()
        super.onDestroy()
    }

    override fun hasPermission(): Boolean {
        val hasPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        return hasPermission == PackageManager.PERMISSION_GRANTED
    }

    override fun requestPermission() {
        ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                REQUEST_COARSE_LOCATION_PERMISSIONS
        )
    }

    override fun turnOnBtMessage() {
        val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        startActivityForResult(intent, REQUEST_OPEN_BLUETOOTH)
    }

    override fun showPermissionError() {
        Toast.makeText(this, getString(R.string.permission_failure), Toast.LENGTH_LONG)
                .show()
    }

    override fun showProgressDialog() {
        progressBar.visibility = View.VISIBLE
        emptyCase.visibility = View.GONE
    }

    override fun setReceiver(receiver: BroadcastReceiver, filter: IntentFilter) {
        registerReceiver(receiver, filter)
    }

    override fun unsetReceiver(receiver: BroadcastReceiver) {
        unregisterReceiver(receiver)
    }

    override fun showList(printers: List<BluetoothDevice>) {
        progressBar.visibility = View.GONE
        this.adapter.addAll(printers)
    }

    override fun emptyCase() {
        emptyCase.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    override fun initUi() {
        emptyCase.text = String.format(getString(R.string.there_are_not), getString(R.string.printers))
        this.adapter = BluetoothDeviceAdapter()
        recyclerView.adapter = this.adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}
