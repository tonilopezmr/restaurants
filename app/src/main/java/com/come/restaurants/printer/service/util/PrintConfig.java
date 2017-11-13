package com.come.restaurants.printer.service.util;

public class PrintConfig {

  private PrinterCommands.Align alignment = PrinterCommands.Align.ALIGNMENT_LEFT;
  private PrinterCommands.Font font = PrinterCommands.Font.FONT_DEFAULT;
  private String separator = "--------------------------";
  private String whiteLines = "" + PrinterCommands.NEW_LINE + PrinterCommands.NEW_LINE;

  public PrinterCommands.Align getAlignment() {
    return alignment;
  }

  public void setAlignment(PrinterCommands.Align alignment) {
    this.alignment = alignment;
  }

  public PrinterCommands.Font getFont() {
    return font;
  }

  public void setFont(PrinterCommands.Font font) {
    this.font = font;
  }

  public String getWhiteLines() {
    return whiteLines;
  }

  public void setWhiteLines(int spacing) {
    whiteLines = "";
    for (int i = 0; i < spacing; i++) {
      whiteLines += PrinterCommands.NEW_LINE;
    }
  }

  public String getSeparator() {
    return separator;
  }

  public void setSeparator(String separator) {
    this.separator = separator;
  }
}
