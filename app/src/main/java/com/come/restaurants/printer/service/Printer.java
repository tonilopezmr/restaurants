package com.come.restaurants.printer.service;

import android.os.Handler;
import com.come.restaurants.printer.service.util.PrinterCommands;

public interface Printer {
  void connect(Handler messageHandler) throws PrinterException;

  void disconnect();

  void write(String line) throws PrinterException;

  void alignment(PrinterCommands.Align alignment) throws PrinterException;

  void font(PrinterCommands.Font font) throws PrinterException;

  void feed(PrinterCommands.FeedPaper feed) throws PrinterException;

  void initialize() throws PrinterException;
}
