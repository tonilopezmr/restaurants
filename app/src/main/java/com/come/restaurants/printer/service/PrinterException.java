package com.come.restaurants.printer.service;

public class PrinterException extends Exception {

  public PrinterException(String message) {
    super(message);
  }

  public PrinterException(Exception e) {
    super(e);
  }
}
