package com.come.restaurants.printer.domain

import com.come.restaurants.order.domain.model.Order
import com.come.restaurants.order.domain.usecases.PrintOrder
import com.come.restaurants.printer.service.PrinterJob
import com.come.restaurants.printer.service.util.PrinterCommands
import java.util.ArrayList

class PrinterRepository(val printer: PrinterJob) {
    fun print(order: Order, callback: PrintOrder.Callback) {

        val orderLines = ArrayList<String>()
        order.orderLines.map { orderLine ->
            orderLines.add("${orderLine.quantity}x ${orderLine.plate.name}\t${orderLine.plate.price}€")
        }

        printer.setAlignment(PrinterCommands.Align.ALIGNMENT_CENTER)
                .setFont(PrinterCommands.Font.FONT_STYLE_C)
                .printLine("Order code ${order.code}")
        printer.setFont(PrinterCommands.Font.FONT_STYLE_B)
                .printLines(orderLines)
        printer.setSeparatorSpacing(5)
                .printSeparator()

        callback.orderPrinted(order)
    }
}