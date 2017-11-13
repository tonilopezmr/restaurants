package com.come.restaurants.printer.service

class PrinterException : Exception {

  constructor(message: String) : super(message) {
  }

  constructor(e: Exception) : super(e) {
  }
}
