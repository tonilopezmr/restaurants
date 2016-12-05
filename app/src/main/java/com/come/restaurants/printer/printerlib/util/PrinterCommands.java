package com.come.restaurants.printer.printerlib.util;

public class PrinterCommands {
  public static final byte ESC = 0x1B;
  public static final byte GS = 0x1D;
  public static final char NEW_LINE = '\n';

  public static final byte[] INITIALIZE = {ESC, '@'};


  private static final byte ALIGN = (byte) 'a';

  public enum Align {
    ALIGNMENT_LEFT(new byte[]{ESC, ALIGN, 0x00}),
    ALIGNMENT_CENTER(new byte[]{ESC, ALIGN, 0x01}),
    ALIGNMENT_RIGHT(new byte[]{ESC, ALIGN, 0x02});

    byte[] value;

    Align(byte[] value) {
      this.value = value;
    }

    public byte[] getValue() {
      return this.value;
    }
  }

  private static final byte EXCLM = (byte) '!';

  public enum Font {
    FONT_DEFAULT(new byte[]{GS, EXCLM, 0x00}),
    FONT_STYLE_B(new byte[]{GS, EXCLM, 0x01}),
    FONT_STYLE_C(new byte[]{GS, EXCLM, 0x11});

    byte[] value;

    Font(byte[] value) {
      this.value = value;
    }

    public byte[] getValue() {
      return this.value;
    }
  }


  public static final byte FEED_PAPER = 'J';

  public enum FeedPaper {
    FEED(new byte[] {ESC, FEED_PAPER, 0x00}), //??? NOT SURE WHAT DOES THIS DO, BUT K
    FEED_LINE(new byte[] {ESC, FEED_PAPER, 0x10}),
    FEED_END(new byte[] {ESC, FEED_PAPER, 0x70});

    byte[] value;

    FeedPaper(byte[] value) {
      this.value = value;
    }

    public byte[] getValue() {
      return this.value;
    }
  }
}
