package com.come.restaurants.printer.service;


public class PrinterJobException extends Exception {

  public PrinterJobException(String message) {
    super(message);
  }

  public PrinterJobException(Exception e) {
    super(e);
  }
}
