package com.come.restaurants.printer.domain.usecases

import com.come.restaurants.printer.domain.PrinterRepository

class PrintWelcome(val printerRepository: PrinterRepository) {
  fun print() {
    printerRepository.printWelcome()
  }
}