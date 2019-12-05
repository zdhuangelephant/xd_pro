package com.xiaodou._2b_dashboard_report;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.Base64Utils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.st.dashboard.dao.student.StudentDao;
import com.xiaodou.userCenter.model.UserModel;



/**
 * 
 * userDao 测试类
 * 
 * @author weirong.li
 * @version 1.0
 * @since JDK1.7
 */
public class UserDaoTest extends Base64Utils {

  @SpringBean("studentDao")
  StudentDao  studentDao;

//  // /**
//  // *
//  // * @Title: addEntity
//  // * @Description: 添加文档
//  // */
//  // @Test
//  // public void addEntity() {
//  // try {
//  // UserModel entity = new UserModel();
//  // // entity.setId("1");
//  // entity.setNickName("小逗");
//  // // entity.setCreateTime(new Timestamp(0));
//  // entity.setPassword("3747474744433");
//  // entity.setPortrait("www.xiaodou.com/images/2015");
//  // entity.setSalt("23456");
//  // entity.setToken("32sdfds3456DFG");
//  // entity.setUsedDeviceId("DF3459988wert");
//  // // entity.setTokenTime(new Timestamp(0));
//  // entity.setUserName("18766654354");
//  // // userModelDao.addEntity(entity);
//  // } catch (Exception e) {
//  // throw e;
//  // }
//  // }
//
  /**
   * 
   * @Title: updateEntity
   * @Description: 修改文档
   */
  @Test
  public void updateEntity() {

    // List<UserModelTest> userModelTestList = readExcelToOfficial();
    List<HeadTest> headList = readExcelToOfficial();
    int i = 1;
    for (HeadTest headTest : headList) {
      UserModel entity = new UserModel();
      entity.setNickName(headTest.getName());
      entity.setPortrait(headTest.getUrl());
      Map<String, String> condition = new HashMap<String, String>();
      condition.put("id", i + "");
//      Integer isUpt = studentDao.updateEntity(condition, entity);
//      System.out.println(isUpt);
      i++;
    }

  }

  /**
   * 读取Excel->official_user_info
   */
  public static List<HeadTest> readExcelToOfficial() {
    List<HeadTest> lineList = Lists.newArrayList();
    try {
      String fileName = "head.xls";
      InputStream is = new FileInputStream(fileName);
      Workbook book = Workbook.getWorkbook(is);
      // 获得第一个工作表对象
      Sheet sheet = book.getSheet(0);
      String[] lineArray = {"name", "url"};
      // {"phoneNum", "realName", "gender", "identificationCardCode", "admissionCardCode",
      // "majorCode", "majorName", "courseCode", "courseName", "degreeLevel", "merchant"};
      // j:列，i:行
      // count+1=3
      for (int i = 1; i <= sheet.getRows() - 1; i++) {
        String rowStr = "{";
        for (int j = 0; j <= 1; j++) {
          // System.out.println(j + ":" + i);
          Cell cell = sheet.getCell(j, i);
          if (null == cell) break;
          String result = cell.getContents();
          // System.out.print(result + ";");
          rowStr += String.format("%s:\"%s\",", lineArray[j], result);
        }
        rowStr = rowStr.substring(0, rowStr.lastIndexOf(",")) + "}";
        System.out.println(rowStr);
        HeadTest headTest = FastJsonUtil.fromJson(rowStr, HeadTest.class);
        lineList.add(headTest);
      }
      book.close();
    } catch (Exception e) {
      System.out.println(e);
    }
    return lineList;
  }
}
