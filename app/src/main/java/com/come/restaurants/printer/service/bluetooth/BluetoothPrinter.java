package com.come.restaurants.printer.service.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Handler;

import com.come.restaurants.printer.service.Printer;
import com.come.restaurants.printer.service.PrinterException;
import com.come.restaurants.printer.service.util.PrinterCommands;

public class BluetoothPrinter implements Printer {
    //TODO REMOVE SINGLETON PATTERN
    private static BluetoothPrinter printer = new BluetoothPrinter();
    private static boolean isConnected = false;
    private BluetoothService bluetoothService;

    public static BluetoothPrinter getPrinter() {
        return printer;
    }

    public static boolean isConnected() {
        return isConnected;
    }

    @Override
    public void connect(Handler messageHandler) throws PrinterException {
        bluetoothService = new BluetoothService(messageHandler);
        BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();
        //We assume the printer was the most recent connection
        BluetoothDevice printer = bAdapter.getBondedDevices().iterator().next();

        checkDevice(printer);

        bluetoothService.connect(printer);
        isConnected = true;
    }

    public void connect(BluetoothDevice printer, Handler messageHandler) throws PrinterException {
        bluetoothService = new BluetoothService(messageHandler);

        checkDevice(printer);

        bluetoothService.connect(printer);
        isConnected = true;
    }

    private void checkDevice(BluetoothDevice printer) throws PrinterException {
        if (printer == null) {
            throw new PrinterException("Bluetooth Connect: No paired devices found");
        }
    }

    @Override
    public void disconnect() {
        bluetoothService.stop();
        isConnected = false;
    }

    @Override
    public void write(String line) throws PrinterException {
        write(line.getBytes());
    }

    @Override
    public void alignment(PrinterCommands.Align alignment) throws PrinterException {
        write(alignment.getValue());
    }

    @Override
    public void font(PrinterCommands.Font font) throws PrinterException {
        write(font.getValue());
    }

    @Override
    public void feed(PrinterCommands.FeedPaper feed) throws PrinterException {
        write(feed.getValue());
    }

    @Override
    public void initialize() throws PrinterException {
        write(PrinterCommands.INITIALIZE);
    }

    /**
     * Private because the main purpose to Printer is that we don't want handle bytes.
     *
     * @param data
     * @throws PrinterException
     */
    private void write(byte[] data) throws PrinterException {
        if (bluetoothService.getState() != BluetoothService.STATE_CONNECTED)
            throw new PrinterException("NO DEVICE CONNECTED");
        bluetoothService.write(data);
    }
}
