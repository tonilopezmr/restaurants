package com.come.restaurants.printer.service;

import com.come.restaurants.printer.service.util.PrinterCommands;

public abstract class Printer {

  public abstract void disconnect();
  protected abstract void write(byte[] data) throws PrinterException;

  public void write(String line) throws PrinterException {
    write(line.getBytes());
  }

  
  public void alignment(PrinterCommands.Align alignment) throws PrinterException {
    write(alignment.getValue());
  }

  
  public void font(PrinterCommands.Font font) throws PrinterException {
    write(font.getValue());
  }

  
  public void feed(PrinterCommands.FeedPaper feed) throws PrinterException {
    write(feed.getValue());
  }

  
  public void initialize() throws PrinterException {
    write(PrinterCommands.INITIALIZE);
  }
}
