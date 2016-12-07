package com.come.restaurants.printer.printerlib;

import com.come.restaurants.printer.printerlib.util.PrintConfig;
import com.come.restaurants.printer.printerlib.util.PrinterCommands;

import java.util.List;

public interface IPrinterJob {


  /**
   * Method that initializes the printer to start a Job Session
   */
  IPrinterJob initializePrinter() throws PrinterJobException;

  /**
   * Method that prints the arg line using the PrinterJob and then resets the config
   * By default it prints a plain line
   *
   * @param line
   * @throws PrinterJobException
   */
  void printLine(String line) throws PrinterJobException;


  /**
   * Prints all lines passed with the same PrinterJob config and then resets the config
   *
   * @param lines
   * @throws PrinterJobException
   */
  void printAllLines(List<String> lines) throws PrinterJobException;

  /**
   * Method that prints a separator.
   * Default: ----------------
   *
   * @throws PrinterJobException
   */
  IPrinterJob printSeparator() throws PrinterJobException;

  /**
   * Allows to specify the separator to be used in the print
   *
   * @param separator
   * @return
   */
  IPrinterJob setSeparator(String separator);


  /**
   * Method that sets the number of newLines that goes after a separator
   * Default: 2 new lines
   *
   * @param spacing
   */
  IPrinterJob setSeparatorSpacing(int spacing);

  /**
   * Changes the alignment in the print
   *
   * @param align
   * @return
   */
  IPrinterJob setAlignment(PrinterCommands.Align align);

  /**
   * Changes the font to be used in the print
   *
   * @param font
   * @return
   */
  IPrinterJob setFont(PrinterCommands.Font font);

  /**
   * Feeds paper
   *
   * @param feed
   * @return
   * @throws PrinterJobException
   */
  IPrinterJob feedPaper(PrinterCommands.FeedPaper feed) throws PrinterJobException;

  /**
   * Retrieves the current configuration to be used on the print
   * WARNING: After a print, it returns the default configuration
   *
   * @return
   */
  PrintConfig getConfig();

  /**
   * Sets the parameter config to be used on the print
   *
   * @param config
   * @return
   */
  IPrinterJob setConfig(PrintConfig config);
}
