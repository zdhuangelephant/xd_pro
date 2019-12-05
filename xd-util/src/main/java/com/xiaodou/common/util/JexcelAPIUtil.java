package com.xiaodou.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class JexcelAPIUtil {
  public static void main(String args[]) {
//     createExcel();
    readExcel();
  }

  /**
   * 生成Excel
   */
  public static void createExcel() {
    try {
      // 打开文件
      WritableWorkbook book = Workbook.createWorkbook(new File("/test3.xls"));
      // 生成名为“第一页”的工作表，参数0表示这是第一页
      WritableSheet sheet = book.createSheet(" 第一页 ", 0);
      // 在Label对象的构造子中指名单元格位置是第一列第一行(0,0)
      // 以及单元格内容为test
      Label label = new Label(0, 0, "a");
      // 将定义好的单元格添加到工作表中
      sheet.addCell(label);
      /**
       * 生成一个保存数字的单元格 必须使用Number的完整包路径，否则有语法歧义 单元格位置是第二列，第一行，值为789.123
       */
      jxl.write.Number number = new jxl.write.Number(1, 0, 111.12541);
      sheet.addCell(number);
      
      Label l2 = new Label(0, 1, "b");
      sheet.addCell(l2);
      jxl.write.Number n2 = new jxl.write.Number(1, 1, 222.12541);
      sheet.addCell(n2);
      
      // 写入数据并关闭文件
      book.write();
      book.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  /**
   * 读取Excel
   */
  public static void readExcel() {
    try {
      InputStream is = new FileInputStream("test3.xls");
      Workbook book = Workbook.getWorkbook(is);
      // 获得第一个工作表对象
      Sheet sheet = book.getSheet(0);
      /*
       * // 得到第一列第一行的单元格 Cell cell1 = sheet.getCell(0, 0); // 获取第一行，第二列的值 Cell c10 =
       * sheet.getCell(1, 0); String result00 = cell1.getContents(); String result10 =
       * c10.getContents(); System.out.print(result00); System.out.print(result10);
       */
      // j:列，i:行
      for (int i = 0; i <= 2; i++) {
        
        for (int j = 0; j <= 1; j++) {
         // System.out.println(j + ":" + i);
          Cell cell = sheet.getCell(j, i);
          String result = cell.getContents();
          System.out.println("-----");
          System.out.print(result+"  ");
        }
        System.out.println();
      }
      book.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  /**
   * 修改Excel
   */
  public static void updateExcel() {
    try {
      // Excel获得文件
      Workbook wb = Workbook.getWorkbook(new File(" test.xls "));
      // 打开一个文件的副本，并且指定数据写回到原文件
      WritableWorkbook book = Workbook.createWorkbook(new File(" test.xls "), wb);
      // 添加一个工作表
      WritableSheet sheet = book.createSheet(" 第二页 ", 1);
      sheet.addCell(new Label(0, 0, " 第二页的测试数据 "));
      book.write();
      book.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }

}
