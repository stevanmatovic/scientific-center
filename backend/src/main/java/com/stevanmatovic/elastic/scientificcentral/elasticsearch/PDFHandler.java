package com.stevanmatovic.elastic.scientificcentral.elasticsearch;

import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class PDFHandler {

  public IndexUnit getIndexUnit(File file) {
    IndexUnit retVal = new IndexUnit();
    try {
      PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
      parser.parse();
      String text = getText(parser);
      retVal.setContent(text);
      retVal.setFilename(file.getName());
    } catch (IOException e) {
      System.out.println("Greksa pri konvertovanju dokumenta u pdf");
    }
    return retVal;
  }

  public String getText(File file) {
    try {
      PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
      parser.parse();
      PDFTextStripper textStripper = new PDFTextStripper();
      String text = textStripper.getText(parser.getPDDocument());
      parser.getPDDocument().close();
      return text;

    } catch (IOException e) {
      System.out.println("Greksa pri konvertovanju dokumenta u pdf");
    }
    return null;
  }

  public String getText(PDFParser parser) {
    try {
      PDFTextStripper textStripper = new PDFTextStripper();
      String text = textStripper.getText(parser.getPDDocument());

      return text;
    } catch (IOException e) {
      System.out.println("Greksa pri konvertovanju dokumenta u pdf");
    }
    return null;
  }

}
