package com.come.restaurants.printer.domain

import com.come.restaurants.order.domain.model.Order
import com.come.restaurants.order.domain.usecases.PrintOrder
import com.come.restaurants.printer.service.PrinterService
import com.come.restaurants.printer.service.util.PrinterCommands
import java.text.DateFormat
import java.util.ArrayList
import java.util.Date

class PrinterRepository(val printerService: PrinterService) {

  fun print(order: Order, callback: PrintOrder.Callback) {

    val orderLines = ArrayList<String>()
    order.orderLines.map { orderLine ->
      orderLines.add("${orderLine.quantity}x ${orderLine.plate.name}")
    }

    printerService.alignment(PrinterCommands.Align.ALIGNMENT_CENTER)
        .font(PrinterCommands.Font.FONT_STYLE_C)
        .printLine("# " + order.code)

    printerService.feed(PrinterCommands.FeedPaper.FEED_LINE)
    printerService.font(PrinterCommands.Font.FONT_STYLE_B)
        .printLines(orderLines)

    printerService.whiteLines(2)
        .printSeparator()

    callback.orderPrinted(order)
  }

  fun printWelcome() {

    feedLine()

    printerJob.setAlignment(PrinterCommands.Align.ALIGNMENT_CENTER)
        .setFont(PrinterCommands.Font.FONT_STYLE_C)
        .printLine("COME")

    printerJob.setAlignment(PrinterCommands.Align.ALIGNMENT_CENTER)
        .printLine("Reinventing your meals")

    printerJob.setAlignment(PrinterCommands.Align.ALIGNMENT_CENTER)
        .printLine(DateFormat.getDateTimeInstance(
        DateFormat.SHORT, DateFormat.SHORT).format(Date()))

    feedLine()

    printerJob.printLine("No hay amor mas sincero que el  amor a la comida")

    feedLine()

    printerJob.setAlignment(PrinterCommands.Align.ALIGNMENT_RIGHT)
        .setFont(PrinterCommands.Font.FONT_DEFAULT)
        .printLine("- George Bernard Shaw")

    feedLine()

    printerJob.setSeparatorSpacing(4)
        .printSeparator()
  }

  private fun feedLine() {
    printerJob.feed(PrinterCommands.FeedPaper.FEED_LINE)
        .printLine("")
  }

}