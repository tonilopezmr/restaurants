package com.come.restaurants.printer.service.usb;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;

//TODO REMOVE HANDLER AND PERMISSION and get devices
public class USBService {
  private final Context mApplicationContext;
  private final UsbManager mUsbManager;
  protected final String ACTION_USB_PERMISSION;
  private final Handler mHandler;
  public static final int USB_CONNECTED = 0;
  public static final int USB_DISCONNECTED = 1;
  private UsbEndpoint ep = null;
  private UsbInterface usbIf = null;
  private UsbDeviceConnection conn = null;


  public USBService(String name, Context parentActivity, Handler handler) {
    this.ACTION_USB_PERMISSION = name;
    this.mApplicationContext = parentActivity;
    this.mUsbManager =
        ((UsbManager) this.mApplicationContext.getSystemService(Context.USB_SERVICE));
    this.mHandler = handler;
  }

  public synchronized UsbDevice getDev(int vid, int pid) {
    UsbDevice dev = null;
    this.ep = null;
    this.usbIf = null;
    this.conn = null;
    HashMap<String, UsbDevice> devlist = this.mUsbManager.getDeviceList();
    Iterator<UsbDevice> deviter = devlist.values().iterator();
    while (deviter.hasNext()) {
      UsbDevice d = (UsbDevice) deviter.next();
      Log.d("usb device:", d.getDeviceName() + "  " +
          String.format("%04X:%04X", new Object[]{Integer.valueOf(d.getVendorId()),
              Integer.valueOf(d.getProductId())}));
      if ((d.getVendorId() == vid) && (d.getProductId() == pid)) {
        dev = d;
        break;
      }
    }
    return dev;
  }


  public synchronized HashMap<String, UsbDevice> getUsbList() {
    return this.mUsbManager.getDeviceList();
  }

  public synchronized boolean isHasPermission(UsbDevice dev) {
    return this.mUsbManager.hasPermission(dev);
  }

  public synchronized void getPermission(UsbDevice dev) {
    if (dev == null)
      return;
    if (!isHasPermission(dev)) {
      PendingIntent pi = PendingIntent.getBroadcast(this.mApplicationContext, 0, new Intent(ACTION_USB_PERMISSION), 0);
      this.mApplicationContext.registerReceiver(this.mPermissionReceiver, new IntentFilter(ACTION_USB_PERMISSION));
      this.mUsbManager.requestPermission(dev, pi);
    } else {
      Message msg = this.mHandler.obtainMessage(USB_CONNECTED);
      this.mHandler.sendMessage(msg);
    }
  }

  public synchronized void sendMessage(String message, UsbDevice dev) {
    byte[] send;
    try {
      send = message.getBytes("GBK");
    } catch (UnsupportedEncodingException e) {
      send = message.getBytes();
    }
    sendByte(send, dev);
    sendByte(new byte[]{13, 10}, dev);
  }

  public synchronized void sendMsg(String msg, String charset, UsbDevice dev) {
    if (msg.length() == 0)
      return;
    byte[] send;
    try {
      send = msg.getBytes(charset);
    } catch (UnsupportedEncodingException e) {
      send = msg.getBytes();
    }
    sendByte(send, dev);
    sendByte(new byte[]{13, 10}, dev);
  }

  public void sendByte(byte[] bits, UsbDevice dev) {
    if (bits == null)
      return;
    if ((this.ep != null) && (this.usbIf != null) && (this.conn != null)) {
      this.conn.bulkTransfer(this.ep, bits, bits.length, 0);
    } else {

      if (this.conn == null)
        this.conn = this.mUsbManager.openDevice(dev);

      if (dev.getInterfaceCount() == 0) {
        return;
      }

      this.usbIf = dev.getInterface(0);

      if (this.usbIf.getEndpointCount() == 0) {

        return;
      }

      for (int i = 0; i < this.usbIf.getEndpointCount(); i++) {
        if ((this.usbIf.getEndpoint(i).getType() == 2) &&
            (this.usbIf.getEndpoint(i).getDirection() != 128)) {

          this.ep = this.usbIf.getEndpoint(i);
        }
      }


      if (this.conn.claimInterface(this.usbIf, true)) {

        this.conn.bulkTransfer(this.ep, bits, bits.length, 0);
      }
    }
  }


  public byte revByte(UsbDevice dev) {

    byte[] bits = new byte[2];

    if (this.conn == null)
      this.conn = this.mUsbManager.openDevice(dev);

    this.conn.controlTransfer(161, 1, 0, 0, bits, bits.length, 0);

    return bits[0];
  }

  public synchronized void close() {

    if (this.conn != null) {

      this.conn.close();
      this.ep = null;
      this.usbIf = null;
      this.conn = null;
    }
  }


  public synchronized void cutPaper(UsbDevice dev, int n) {
    byte[] bits = new byte[4];

    bits[0] = 29;

    bits[1] = 86;

    bits[2] = 66;

    bits[3] = ((byte) n);

    sendByte(bits, dev);
  }


  public synchronized void catPaperByMode(UsbDevice dev, int mode) {

    byte[] bits = new byte[3];

    switch (mode) {
      case 0:

        bits[0] = 29;

        bits[1] = 86;

        bits[2] = 48;

        break;
      case 1:

        bits[0] = 29;

        bits[1] = 86;

        bits[2] = 49;

        break;
    }


    sendByte(bits, dev);
  }


  public synchronized void openCashBox(UsbDevice dev) {

    byte[] bits = new byte[5];

    bits[0] = 27;
    bits[1] = 112;
    bits[2] = 0;
    bits[3] = 64;
    bits[4] = 80;
    sendByte(bits, dev);
  }


  public synchronized void defaultBuzzer(UsbDevice dev) {
    byte[] bits = new byte[4];

    bits[0] = 27;
    bits[1] = 66;
    bits[2] = 4;
    bits[3] = 1;
    sendByte(bits, dev);
  }


  public synchronized void buzzer(UsbDevice dev, int n, int time) {
    byte[] bits = new byte[4];
    bits[0] = 27;
    bits[1] = 66;
    bits[2] = ((byte) n);
    bits[3] = ((byte) time);
    sendByte(bits, dev);
  }


  public synchronized void setBuzzerMode(UsbDevice dev, int n, int time, int mode) {
    byte[] bits = new byte[5];
    bits[0] = 27;
    bits[1] = 67;
    bits[2] = ((byte) n);
    bits[3] = ((byte) time);
    bits[4] = ((byte) mode);
    sendByte(bits, dev);
  }

  private final BroadcastReceiver mPermissionReceiver = new BroadcastReceiver() {
    public void onReceive(Context context, Intent intent) {

      USBService.this.mApplicationContext.unregisterReceiver(this);

      if ((intent.getAction().equals(ACTION_USB_PERMISSION)) &&
          (intent.getBooleanExtra("permission", false))) {

        UsbDevice dev = (UsbDevice) intent.getParcelableExtra("device");
        if (dev != null) {
          Message msg = USBService.this.mHandler.obtainMessage(USB_CONNECTED);
          USBService.this.mHandler.sendMessage(msg);
        }
      }
    }
  };
}
