package com.come.restaurants.printer.pairing.ui

import android.Manifest
import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.come.restaurants.R
import com.come.restaurants.printer.pairing.BtPairingPresenter
import com.come.restaurants.printer.pairing.ui.adapter.BtPairingAdapter
import kotlinx.android.synthetic.main.activity_bt_pairing.*

class BtPairingActivity : AppCompatActivity(), BtPairingPresenter.View {

  private val REQUEST_COARSE_LOCATION_PERMISSIONS = 2000

  private lateinit var presenter: BtPairingPresenter
  private lateinit var adapter: BtPairingAdapter
  private lateinit var progressDialog: ProgressDialog


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_bt_pairing)

    this.presenter = BtPairingPresenter()
    this.presenter.setView(this)
    this.presenter.init()

  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    when (requestCode) {
      REQUEST_COARSE_LOCATION_PERMISSIONS -> {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          this.presenter.doDiscovery()
        } else {
          this.showPermissionError()
        }
      }
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
    startActivityForResult(intent, 1000)
  }

  override fun showPermissionError() {
    Toast.makeText(this, getString(R.string.permission_failure), Toast.LENGTH_LONG)
        .show()
  }

  override fun showProgressDialog() {
    this.progressDialog = ProgressDialog(this)
    this.progressDialog.setMessage(getString(R.string.scanning_bt))
    this.progressDialog.setCancelable(false)
    this.progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE,
        getString(R.string.cancel),
        { dialog, which ->
          progressDialog.dismiss()
          presenter.cancelDiscovery()
        })
    this.progressDialog.show()

  }

  override fun setReceiver(receiver: BroadcastReceiver, filter: IntentFilter) {
    registerReceiver(receiver, filter)
  }

  override fun unsetReceiver(receiver: BroadcastReceiver) {
    unregisterReceiver(receiver)
  }

  override fun showList(printers: List<BluetoothDevice>) {
    this.progressDialog.dismiss()
    this.adapter.addAll(printers)
  }

  override fun initUi() {
    this.adapter = BtPairingAdapter()
    printersRecyclerView.adapter = this.adapter
    printersRecyclerView.layoutManager = LinearLayoutManager(this)
  }
}
