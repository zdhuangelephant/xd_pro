package com.xiaodou.ms.util;

import au.com.bytecode.opencsv.CSVParser;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.util.List;

/**
 * Created by zyp on 15/7/1.
 */
public class CsvUtil {

  /**
   * csv数据读取
   * @param csvFile
   * @return
   */
  public static List<String[]> getAllData(String csvFile) {
    try {
      File file = new File(csvFile);
      FileReader fReader = new FileReader(file);
      CSVReader csvReader =
        new CSVReader(fReader, CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_QUOTE_CHARACTER, 0);
      List<String[]> list = csvReader.readAll();
      csvReader.close();
      return list;
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * csv写数据
   * @param rows
   * @param fileUrl
   */
  public static void writeData(List<String[]> rows,String fileUrl){
    try {
      File file = new File(fileUrl);
      Writer writer = new FileWriter(file);
      CSVWriter csvWriter = new CSVWriter(writer, ',');
      for (String[] row:rows){
        csvWriter.writeNext(row);
      }
      csvWriter.close();
    } catch (Exception e){
    }
  }

}
