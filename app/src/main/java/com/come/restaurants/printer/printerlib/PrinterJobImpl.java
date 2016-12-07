package com.come.restaurants.printer.printerlib;

import com.come.restaurants.printer.printerlib.util.PrintConfig;
import com.come.restaurants.printer.printerlib.util.PrinterCommands;

import java.util.List;

public class PrinterJobImpl implements IPrinterJob {

  private IPrinter printer;
  private PrintConfig config;

  //Implementation following the Builder pattern -> The product is each print
  public PrinterJobImpl(IPrinter printer) {
    this.printer = printer;
    this.config = new PrintConfig();
  }

  @Override
  public IPrinterJob initializePrinter() throws PrinterJobException {
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
    //After a print we reset the settings
    initializePrinter();
  }

  @Override
  public void printAllLines(List<String> lines) throws PrinterJobException {
    try {
      printer.setAlignment(config.getAlignment());
      printer.setFont(config.getFont());
      for (String line : lines) {
        printer.write(line + PrinterCommands.NEW_LINE);
      }
    } catch (PrinterException e) {
      throw new PrinterJobException(e.getMessage());
    }
    //After a print we reset the settings
    initializePrinter();
  }

  @Override
  public IPrinterJob printSeparator() throws PrinterJobException {
    try {
      printer.write(config.getSeparator() + config.getSeparator_spacing());
    } catch (PrinterException e) {
      throw new PrinterJobException(e.getMessage());
    }
    return this;
  }

  @Override
  public IPrinterJob setSeparator(String separator) {
    config.setSeparator(separator);
    return this;
  }

  @Override
  public IPrinterJob setSeparatorSpacing(int spacing) {
    config.setSeparator_spacing(spacing);
    return this;
  }

  @Override
  public IPrinterJob setAlignment(PrinterCommands.Align align) {
    config.setAlignment(align);
    return this;
  }

  @Override
  public IPrinterJob setFont(PrinterCommands.Font font) {
    config.setFont(font);
    return this;
  }

  @Override
  public IPrinterJob feedPaper(PrinterCommands.FeedPaper feed) throws PrinterJobException {
    try {
      printer.feedPaper(feed);
    } catch (PrinterException e) {
      throw new PrinterJobException(e.getMessage());
    }
    return this;
  }

  @Override
  public IPrinterJob setConfig(PrintConfig config) {
    this.config = config;
    return this;
  }

  public PrintConfig getConfig() {
    return this.config;
  }
}
