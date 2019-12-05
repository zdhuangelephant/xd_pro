package com.xiaodou.autopractise.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author XiongYC
 * @date 2017年11月2日
 * 
 */
public class ImportExcelUntil {

  /**
   * 拼装单个obj
   * 
   * @param obj
   * @param row
   * @return
   * @throws Exception
   */
  private static Map<String, String> dataObj(Object obj, HSSFRow row) throws Exception {
    Class<?> rowClazz = obj.getClass();
    Field[] fields = FieldUtils.getAllFields(rowClazz);
    if (fields == null || fields.length < 1) {
      return null;
    }

    // 容器
    Map<String, String> map = new HashMap<String, String>();

    // 注意excel表格字段顺序要和obj字段顺序对齐 （如果有多余字段请另作特殊下标对应处理）
    for (int j = 0; j < fields.length; j++) {
      map.put(fields[j].getName(), getVal(row.getCell(j)));
    }
    return map;
  }

  public static List<Map<String, String>> importExcel(MultipartFile file, Object obj)
      throws Exception {

    // 装载流
    POIFSFileSystem fs = new POIFSFileSystem(file.getInputStream());
    HSSFWorkbook hw = new HSSFWorkbook(fs);

    // 获取第一个sheet页
    HSSFSheet sheet = hw.getSheetAt(0);

    // 容器
    List<Map<String, String>> ret = new ArrayList<Map<String, String>>();

    // 遍历行 从下标第零行开始
    for (int i = 0; i < sheet.getLastRowNum(); i++) {
      HSSFRow row = sheet.getRow(i);
      if (row != null) {
        // 装载obj
        ret.add(dataObj(obj, row));
      }
    }
    return ret;
  }

  /**
   * 处理val（暂时只处理string和number，可以自己添加自己需要的val类型）
   * 
   * @param hssfCell
   * @return
   */
  public static String getVal(HSSFCell hssfCell) {
    if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
      return hssfCell.getStringCellValue();
    } else {
      return String.valueOf(hssfCell.getNumericCellValue());
    }
  }
}
