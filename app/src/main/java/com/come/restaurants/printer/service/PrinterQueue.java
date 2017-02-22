package com.come.restaurants.printer.service;

import com.come.restaurants.order.domain.model.Order;
import com.come.restaurants.order.domain.usecases.PrintOrder;
import com.come.restaurants.printer.domain.PrinterRepository;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.Queue;

public class PrinterQueue extends Thread {

  public static final int BASE_SECOND = 1;
  public static final int MAX_WAIT_TIME = 3000;
  public static final double SPEED_MODIFICATOR = 0.040;

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

    Long waitTime = calculateWaitTime(orderQueue.size());
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

  private long calculateWaitTime(int actualSize) {
    double waitTimeActualSize;

    waitTimeActualSize = BASE_SECOND + SPEED_MODIFICATOR * actualSize;
    waitTimeActualSize = 1000 * waitTimeActualSize;

    return Math.min((int) waitTimeActualSize, MAX_WAIT_TIME);
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
