package com.come.restaurants.printer.service.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.util.Log;
import com.come.restaurants.printer.service.Printer;
import com.come.restaurants.printer.service.PrinterException;

import java.io.IOException;

public class BluetoothPrinter extends Printer {

  //TODO REMOVE SINGLETON PATTERN
  private static BluetoothPrinter printer;
  private static boolean isConnected = false;
  private final String TAG = getClass().getCanonicalName();
  private BluetoothService bluetoothService;

  private BluetoothPrinter() {
  }

  public static BluetoothPrinter getPrinter() {
    if (printer == null) printer = new BluetoothPrinter();
    return printer;
  }

  public static boolean isConnected() {
    return isConnected;
  }

  public void connect(BluetoothDevice printer, Handler messageHandler) throws PrinterException {
    try {
      bluetoothService = new BluetoothService(messageHandler);

      checkDevice(printer);

      bluetoothService.connect(printer);
      isConnected = true;
    } catch (IOException ioe) {
      throw new PrinterException(ioe);
    }
  }

  private void checkDevice(BluetoothDevice printer) throws PrinterException {
    if (printer == null) {
      throw new PrinterException("Bluetooth Connect: No paired devices found");
    }
  }

  @Override
  public void disconnect() {
    if (bluetoothService != null) {
      bluetoothService.stop();
      Log.i(TAG, "Disconnect");
    }
    isConnected = false;
  }

  /**
   * Private because the main purpose to Printer is that we don't want handle bytes.
   *
   * @param data data in bytes
   * @throws PrinterException
   */
  @Override
  protected void write(byte[] data) throws PrinterException {
    if (bluetoothService.getState() != BluetoothService.STATE_CONNECTED)
      throw new PrinterException("NO DEVICE CONNECTED");
    bluetoothService.write(data);
  }
}
