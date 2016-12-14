package com.come.restaurants.printer.pairing.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbManager;
import android.widget.Toast;

import com.come.restaurants.R;
import com.come.restaurants.printer.pairing.ChoosePairingActivity;

public class PrinterDisconnectedReceiver extends BroadcastReceiver {

  private static boolean hasPrinterBeenPaired = false;
  @Override
  public void onReceive(Context context, Intent intent) {
    //TODO check what type of printer was paired
    if (hasPrinterBeenPaired) {
      String action = intent.getAction();
      if (action.equals(BluetoothDevice.ACTION_ACL_DISCONNECTED) ||
          action.equals(UsbManager.ACTION_USB_DEVICE_DETACHED)) {
        launchChoosingDevice(context, intent, context.getString(R.string.error_printer_disconnected));
      }
    }
  }

  private void launchChoosingDevice(Context context, Intent intent, String toastMessage) {
    Toast.makeText(context, toastMessage, Toast.LENGTH_LONG)
        .show();
    Intent startPrinterPairing = new Intent(context, ChoosePairingActivity.class);
    //WARNING!!! TO REVIEW
    startPrinterPairing.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(startPrinterPairing);
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

