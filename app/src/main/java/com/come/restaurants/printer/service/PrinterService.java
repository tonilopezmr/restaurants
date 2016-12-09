package com.come.restaurants.printer.service;

import com.come.restaurants.printer.service.util.PrintConfig;
import com.come.restaurants.printer.service.util.PrinterCommands;

import java.util.List;

public class PrinterService {

  private Printer printer;
  private PrintConfig config;

  //Implementation following the Builder pattern -> The product is each print
  public PrinterService(Printer printer) {
    this.printer = printer;
    this.config = new PrintConfig();
  }

  /**
   * Method that initializes the printer to start a Job Session
   */
  public void initializePrinter() throws PrinterException {
    printer.initialize();
    config = new PrintConfig();
  }

  /**
   * Method that prints the arg line using the PrinterJob and then resets the config
   * By default it prints a plain line
   *
   * @param line
   * @throws PrinterException
   */
  public void printLine(String line) throws PrinterException {
    try {
      printer.alignment(config.getAlignment());
      printer.font(config.getFont());
      printer.write(line + PrinterCommands.NEW_LINE);
    } catch (PrinterException e) {
      initializePrinter();
      throw e;
    }
  }

  /**
   * Prints all whiteLines passed with the same PrinterJob config and then resets the config
   *
   * @param lines
   * @throws PrinterException
   */
  public void printLines(List<String> lines) throws PrinterException {
    try {
      printer.alignment(config.getAlignment());
      printer.font(config.getFont());
      for (String line : lines) {
        printer.write(line + PrinterCommands.NEW_LINE);
      }
    } catch (PrinterException e) {
      initializePrinter();
      throw e;
    }
  }

  /**
   * Method that prints a separator.
   * Default: ----------------
   *
   * @throws PrinterException
   */
  public PrinterService printSeparator() throws PrinterException {
    printer.write(config.getSeparator() + config.getWhiteLines());
    return this;
  }

  /**
   * Allows to specify the separator to be used in the print
   *
   * @param separator
   * @return
   */
  public PrinterService setSeparator(String separator) {
    config.setSeparator(separator);
    return this;
  }

  /**
   * Method that sets the number of newLines that goes after a separator
   * Default: 2 new whiteLines
   *
   * @param lines
   */
  public PrinterService whiteLines(int lines) {
    config.setWhiteLines(lines);
    return this;
  }

  /**
   * Changes the alignment in the print
   *
   * @param align
   * @return
   */
  public PrinterService alignment(PrinterCommands.Align align) {
    config.setAlignment(align);
    return this;
  }

  /**
   * Changes the font to be used in the print
   *
   * @param font
   * @return
   */
  public PrinterService font(PrinterCommands.Font font) {
    config.setFont(font);
    return this;
  }

  /**
   * Feeds paper
   *
   * @param feed
   * @return
   * @throws PrinterException
   */
  public PrinterService feed(PrinterCommands.FeedPaper feed) throws PrinterException {
    printer.feed(feed);
    return this;
  }

  /**
   * Sets the parameter config to be used on the print
   *
   * @param config
   * @return
   */
  public PrinterService withConfig(PrintConfig config) {
    this.config = config;
    return this;
  }

  /**
   * Retrieves the current configuration to be used on the print
   * WARNING: After a print, it returns the default configuration
   *
   * @return
   */
  public PrintConfig getConfig() {
    return this.config;
  }
}
