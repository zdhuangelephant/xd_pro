package com.xiaodou.st.dashboard.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.xiaodou.st.dashboard.domain.score.ImportScoreDTO;

public class ReadWriteExcelFile {



  public static void readXLSFile() throws IOException {
    String urlString = "D:/公司/小逗网络/excel/template20171023085206.xls";
    String excelType = urlString.substring(urlString.lastIndexOf(".")+1);
    System.out.println(excelType);
    InputStream ExcelFileToRead =
        new FileInputStream(urlString);
    HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

    HSSFSheet sheet = wb.getSheetAt(0);
    HSSFRow row;
    HSSFCell cell;

    Iterator rows = sheet.rowIterator();
    int a = 0;
    // 只取出有效行
    while (rows.hasNext()) {
      ++a;
      row = (HSSFRow) rows.next();
      System.out.println(row.getRowNum());
      Iterator cells = row.cellIterator();

      while (cells.hasNext()) {
        cell = (HSSFCell) cells.next();
        System.out.print(cell.getCellNum()+":");
        if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
          System.out.print(cell.getStringCellValue() + " ");
        } else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
          System.out.print(cell.getNumericCellValue() + " ");
        } else {
          // U Can Handel Boolean, Formula, Errors
          System.out.print("kong");
        }
      }
      System.out.println();
    }
    System.out.println(a);

    row = sheet.getRow(sheet.getFirstRowNum());
    Iterator cells = row.cellIterator();

    while (cells.hasNext()) {
      cell = (HSSFCell) cells.next();

      if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
        System.out.print(cell.getStringCellValue() + " ");
      } else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
        System.out.print(cell.getNumericCellValue() + " ");
      } else {
        // U Can Handel Boolean, Formula, Errors
      }
    }
    System.out.println(sheet.getFirstRowNum() + ".." + sheet.getLastRowNum());


  }

  public static void writeXLSFile() throws IOException {

    String excelFileName = "C:/Test.xls";// name of excel file

    String sheetName = "Sheet1";// name of sheet

    HSSFWorkbook wb = new HSSFWorkbook();
    HSSFSheet sheet = wb.createSheet(sheetName);

    // iterating r number of rows
    for (int r = 0; r < 5; r++) {
      HSSFRow row = sheet.createRow(r);

      // iterating c number of columns
      for (int c = 0; c < 5; c++) {
        HSSFCell cell = row.createCell((short) c);

        cell.setCellValue("Cell " + r + " " + c);
      }
    }

    FileOutputStream fileOut = new FileOutputStream(excelFileName);

    // write this workbook to an Outputstream.
    wb.write(fileOut);
    fileOut.flush();
    fileOut.close();
  }

  public static void readXLSXFile() throws IOException {
    String urlString = "D:/公司/小逗网络/excel/批量导入学生成绩.xlsx";
    String excelType = urlString.substring(urlString.lastIndexOf(".")+1);
    System.out.println(excelType);
    InputStream ExcelFileToRead = new FileInputStream(urlString);
    XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);

    XSSFWorkbook test = new XSSFWorkbook();

    XSSFSheet sheet = wb.getSheetAt(0);
    XSSFRow row;
    XSSFCell cell;

    Iterator rows = sheet.rowIterator();

    while (rows.hasNext()) {
      row = (XSSFRow) rows.next();
      Iterator cells = row.cellIterator();
      while (cells.hasNext()) {
        cell = (XSSFCell) cells.next();
        System.out.print(cell.getColumnIndex()+":");

        if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
          System.out.print(cell.getStringCellValue() + " ");
        } else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
          System.out.print(cell.getNumericCellValue() + " ");
        } else if(cell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
          System.out.print(cell.getCellType());
          // U Can Handel Boolean, Formula, Errors
        }
      }
      System.out.println();
    }

  }

  public static void writeXLSXFile() throws IOException {

    String excelFileName = "C:/Test.xlsx";// name of excel file

    String sheetName = "Sheet1";// name of sheet

    XSSFWorkbook wb = new XSSFWorkbook();
    XSSFSheet sheet = wb.createSheet(sheetName);

    // iterating r number of rows
    for (int r = 0; r < 5; r++) {
      XSSFRow row = sheet.createRow(r);

      // iterating c number of columns
      for (int c = 0; c < 5; c++) {
        XSSFCell cell = row.createCell(c);

        cell.setCellValue("Cell " + r + " " + c);
      }
    }

    FileOutputStream fileOut = new FileOutputStream(excelFileName);

    // write this workbook to an Outputstream.
    wb.write(fileOut);
    fileOut.flush();
    fileOut.close();
  }

  public static void main(String[] args) throws IOException {
    // writeXLSFile();
    //readXLSFile();
    //
    // writeXLSXFile();
     readXLSXFile();

    Field[] fields = ImportScoreDTO.class.getDeclaredFields();
    System.out.println(fields[0].getName());
  }
}
