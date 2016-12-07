package com.come.restaurants.printer.service;

import com.come.restaurants.printer.service.util.PrintConfig;
import com.come.restaurants.printer.service.util.PrinterCommands;

import java.util.List;

public class PrinterJobImpl implements PrinterJob {

  private Printer printer;
  private PrintConfig config;

  //Implementation following the Builder pattern -> The product is each print
  public PrinterJobImpl(Printer printer) {
    this.printer = printer;
    this.config = new PrintConfig();
  }

  private void print() throws PrinterJobException {
    //After a print we reset the settings
    initializePrinter();
  }

  @Override
  public PrinterJob initializePrinter() throws PrinterJobException {
    try {
      printer.initialize();
      config = new PrintConfig();
    } catch (PrinterException e) {
      throw new PrinterJobException(e.getMessage());
    }
    return this;
  }

  @Override
  public void printLine(String line) throws PrinterJobException {
    try {
      printer.setAlignment(config.getAlignment());
      printer.setFont(config.getFont());
      printer.write(line + PrinterCommands.NEW_LINE);
    } catch (PrinterException e) {
      throw new PrinterJobException(e.getMessage());
    }
    print();
  }

  @Override
  public void printLines(List<String> lines) throws PrinterJobException {
    try {
      printer.setAlignment(config.getAlignment());
      printer.setFont(config.getFont());
      for (String line : lines) {
        printer.write(line + PrinterCommands.NEW_LINE);
      }
    } catch (PrinterException e) {
      throw new PrinterJobException(e.getMessage());
    }
    print();
  }

  @Override
  public PrinterJob printSeparator() throws PrinterJobException {
    try {
      printer.write(config.getSeparator() + config.getSeparatorSpacing());
    } catch (PrinterException e) {
      throw new PrinterJobException(e.getMessage());
    }
    return this;
  }

  @Override
  public PrinterJob setSeparator(String separator) {
    config.setSeparator(separator);
    return this;
  }

  @Override
  public PrinterJob setSeparatorSpacing(int spacing) {
    config.setSeparatorSpacing(spacing);
    return this;
  }

  @Override
  public PrinterJob setAlignment(PrinterCommands.Align align) {
    config.setAlignment(align);
    return this;
  }

  @Override
  public PrinterJob setFont(PrinterCommands.Font font) {
    config.setFont(font);
    return this;
  }

  @Override
  public PrinterJob feed(PrinterCommands.FeedPaper feed) throws PrinterJobException {
    try {
      printer.feedPaper(feed);
    } catch (PrinterException e) {
      throw new PrinterJobException(e.getMessage());
    }
    return this;
  }

  @Override
  public PrinterJob setConfig(PrintConfig config) {
    this.config = config;
    return this;
  }

  public PrintConfig getConfig() {
    return this.config;
  }
}
