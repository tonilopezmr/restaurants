package com.come.restaurants.printer.pairing;

import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.usb.UsbManager;

import com.come.restaurants.R;

public class PrinterDisconnectedReceiver extends BroadcastReceiver {

  private static boolean hasPrinterBeenPaired = false;
  @Override
  public void onReceive(Context context, Intent intent) {
    //TODO check what type of printer was paired
    if (hasPrinterBeenPaired) {
      String action = intent.getAction();
      if (action.equals(BluetoothDevice.ACTION_ACL_DISCONNECTED) ||
          action.equals(UsbManager.ACTION_USB_DEVICE_DETACHED)) {
        launchChoosePairingDevice(context, intent);
      }
    }
  }

  private void launchChoosePairingDevice(final Context context, Intent intent) {
    new AlertDialog.Builder(context)
        .setTitle(R.string.error_printer_disconnected)
        .setMessage(R.string.prompt_reconnect_printer)
        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            Intent startPrinterPairing = new Intent(context, ChoosePairingActivity.class);
            startPrinterPairing.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(startPrinterPairing);
          }
        })
        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            //do nothing
          }
        }).show();
  }
  public static boolean hasPrinterBeenPaired() {
    return hasPrinterBeenPaired;
  }

  public static void initReceiver() {
    //We only accept the Disconnected Status Broadcasts if a printer has already
    //been paired in ChoosePairing
    hasPrinterBeenPaired = true;
  }
}

