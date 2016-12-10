package com.come.restaurants.printer.domain.usecases

import com.come.restaurants.printer.domain.PrinterRepository

class PrintWelcome(val printerRepository: PrinterRepository) {
  fun print() {
    try{
      printerRepository.printWelcome()
    }catch (ex: Exception) {
      //TODO do anything or pass callback
    }
  }
}