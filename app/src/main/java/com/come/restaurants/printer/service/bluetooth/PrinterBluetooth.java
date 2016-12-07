package com.come.restaurants.printer.service.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;

import com.come.restaurants.printer.service.Printer;
import com.come.restaurants.printer.service.PrinterException;
import com.come.restaurants.printer.service.util.PrinterCommands;

public class PrinterBluetooth implements Printer {
  private BluetoothService bluetoothService;

  @Override

  public void connect(Context context, Handler messageHandler) throws PrinterException {
    bluetoothService = new BluetoothService(context, messageHandler);
    BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();
    //We assume the printer was the most recent connection
    BluetoothDevice printer = bAdapter.getBondedDevices().iterator().next();
    if (printer != null) {
      bluetoothService.connect(printer);
      initialize();
    } else {
      throw new PrinterException("NO PAIRED DEVICES FOUND");
    }
  }

  @Override
  public void disconnect() {

  }

  @Override
  public void write(String line) throws PrinterException {
    write(line.getBytes());
  }

  @Override
  public void write(byte[] data) throws PrinterException {
    if (bluetoothService.getState() != BluetoothService.STATE_CONNECTED)
      throw new PrinterException("NO DEVICE CONNECTED");
    bluetoothService.write(data);
  }

  @Override
  public void setAlignment(PrinterCommands.Align alignment) throws PrinterException {
    write(alignment.getValue());
  }

  @Override
  public void setFont(PrinterCommands.Font font) throws PrinterException {
    write(font.getValue());
  }

  @Override
  public void feedPaper(PrinterCommands.FeedPaper feed) throws PrinterException {
    write(feed.getValue());
  }

  @Override
  public void initialize() throws PrinterException {
    write(PrinterCommands.INITIALIZE);
  }
}
