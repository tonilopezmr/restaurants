package com.come.restaurants.printer.printerlib.util;

public class PrintConfig {
  private PrinterCommands.Align alignment = PrinterCommands.Align.ALIGNMENT_LEFT;
  private PrinterCommands.Font font = PrinterCommands.Font.FONT_DEFAULT;
  private String separator = "--------------------------";
  private String separator_spacing = "" + PrinterCommands.NEW_LINE + PrinterCommands.NEW_LINE;

  public PrintConfig() {
  }

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

  public String getSeparator_spacing() {
    return separator_spacing;
  }

  public void setSeparator_spacing(int spacing) {
    separator_spacing = "";
    for (int i = 0; i < spacing; i++) {
      separator_spacing += PrinterCommands.NEW_LINE;
    }
  }

  public String getSeparator() {
    return separator;
  }

  public void setSeparator(String separator) {
    this.separator = separator;
  }
}
