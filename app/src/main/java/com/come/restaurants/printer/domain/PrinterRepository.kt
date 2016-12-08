package com.come.restaurants.printer.domain

import com.come.restaurants.order.domain.model.Order
import com.come.restaurants.order.domain.usecases.PrintOrder
import com.come.restaurants.printer.service.PrinterJob
import com.come.restaurants.printer.service.util.PrinterCommands
import java.util.ArrayList

class PrinterRepository(val printerJob: PrinterJob) {

  fun print(order: Order, callback: PrintOrder.Callback) {

    val orderLines = ArrayList<String>()
    order.orderLines.map { orderLine ->
      orderLines.add("${orderLine.quantity}x ${orderLine.plate.name}")
    }

    printerJob.setAlignment(PrinterCommands.Align.ALIGNMENT_CENTER)
        .setFont(PrinterCommands.Font.FONT_STYLE_C)
        .printLine("# " + order.code)
    printerJob.feed(PrinterCommands.FeedPaper.FEED_LINE)
    printerJob.setFont(PrinterCommands.Font.FONT_STYLE_B)
        .printLines(orderLines)

    printerJob.setSeparatorSpacing(2)
        .printSeparator()
    callback.orderPrinted(order)
  }
}