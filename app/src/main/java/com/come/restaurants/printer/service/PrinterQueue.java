package com.come.restaurants.printer.service;

import com.come.restaurants.order.domain.model.Order;
import com.come.restaurants.order.domain.usecases.PrintOrder;
import com.come.restaurants.printer.domain.PrinterRepository;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.Queue;

public class PrinterQueue extends Thread {

  public static final int BASE_SECOND = 3;

  private Queue<Order> orderQueue;
  private PrinterRepository repository;

  public PrinterQueue(PrinterRepository repository) {
    this.orderQueue = new LinkedList<>();
    this.repository = repository;
  }

  public synchronized void add(Order order) {
    orderQueue.add(order);
  }

  private synchronized long pollQueueElement() {
    Order order = orderQueue.poll();

    Long waitTime = calculateWaitTime(order.getTimestamp(), orderQueue.size());
    repository.print(order, new PrintOrder.Callback() {

      @Override
      public void error(@NotNull Exception exception) {
          //TODO MADREMIA
      }

      @Override
      public void orderPrinted(@NotNull Order order) {
          //TODO OTRA MADREMIA
      }
    });

    return waitTime;
  }

  private long calculateWaitTime(long queueSizeAtArrive, int actualSize) {
    double waitTimeSizeAtArrival, waitTimeActualSize;

    if (queueSizeAtArrive <= 50) {
      waitTimeSizeAtArrival = BASE_SECOND - 0.040 * queueSizeAtArrive;
    } else {
      waitTimeSizeAtArrival = 1 / (queueSizeAtArrive / 100 * Math.log(2));
    }


    if (actualSize <= 50) {
      waitTimeActualSize = BASE_SECOND - 0.040 * actualSize;
    } else {
      waitTimeActualSize = 1 / (actualSize / 100 * Math.log(2));
    }

    waitTimeActualSize = 1000 * waitTimeActualSize;
    waitTimeSizeAtArrival = 1000 * waitTimeSizeAtArrival;
    return Math.min((int) waitTimeActualSize, (int) waitTimeSizeAtArrival);
  }

  private synchronized boolean isQueueEmpty() {
    return orderQueue.isEmpty();
  }

  @Override
  public void run() {

    long waitTime;
    while (true) {
      waitTime = 1000;
      if (!isQueueEmpty()) {
        waitTime = pollQueueElement();
      }


      try {
        Thread.sleep(waitTime);
      } catch (InterruptedException e) {
      }
    }
  }
}
