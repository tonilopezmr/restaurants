package com.come.restaurants.printer.service;

import com.come.restaurants.printer.service.util.PrintConfig;
import com.come.restaurants.printer.service.util.PrinterCommands;

import java.util.List;

public interface PrinterJob {


  /**
   * Method that initializes the printer to start a Job Session
   */
  PrinterJob initializePrinter() throws PrinterJobException;

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
  void printLines(List<String> lines) throws PrinterJobException;

  /**
   * Method that prints a separator.
   * Default: ----------------
   *
   * @throws PrinterJobException
   */
  PrinterJob printSeparator() throws PrinterJobException;

  /**
   * Allows to specify the separator to be used in the print
   *
   * @param separator
   * @return
   */
  PrinterJob setSeparator(String separator);


  /**
   * Method that sets the number of newLines that goes after a separator
   * Default: 2 new lines
   *
   * @param spacing
   */
  PrinterJob setSeparatorSpacing(int spacing);

  /**
   * Changes the alignment in the print
   *
   * @param align
   * @return
   */
  PrinterJob setAlignment(PrinterCommands.Align align);

  /**
   * Changes the font to be used in the print
   *
   * @param font
   * @return
   */
  PrinterJob setFont(PrinterCommands.Font font);

  /**
   * Feeds paper
   *
   * @param feed
   * @return
   * @throws PrinterJobException
   */
  PrinterJob feed(PrinterCommands.FeedPaper feed) throws PrinterJobException;

  /**
   * Sets the parameter config to be used on the print
   *
   * @param config
   * @return
   */
  PrinterJob setConfig(PrintConfig config);

  /**
   * Retrieves the current configuration to be used on the print
   * WARNING: After a print, it returns the default configuration
   *
   * @return
   */
  PrintConfig getConfig();
}
