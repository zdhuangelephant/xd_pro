package com.xiaodou.ms.source.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.ms.model.major.MajorCourseInfo;
import com.xiaodou.ms.model.major.MajorCourseModel;
import com.xiaodou.ms.model.major.MajorDataModel;
import com.xiaodou.ms.model.major.MajorInfo;
import com.xiaodou.ms.model.product.RegionModel;
import com.xiaodou.ms.service.major.MajorCourseService;
import com.xiaodou.ms.service.major.MajorDataService;
import com.xiaodou.ms.service.product.RegionService;
import com.xiaodou.summer.dao.pagination.Page;

@ContextConfiguration(locations = {"classpath:conf/core/xdms-servlet.xml"})
public class DeployMajorDataCourseTest extends AbstractJUnit4SpringContextTests {
  // majordata 66  majorcourse 677
  private static final String BLANK = "\\s+";
  private static final String TAB_BLANK = "###";
  private static final String DIVISION = "高等教育自学考试开考专业考试计划表";
  private static final String GZ = "贵州";
  private static final String ZYDM = "专业代码";
  private static final String ZYMC = "专业名称";
  private static final String KCDM = "课程代码";
  private static final String KCMC = "课程名称";
  private static final String EXAMSCHOOL = "主考院校";
  private static final String DISCARD = "选考原则：";
  private String region = null;

  @Autowired
  MajorDataService majorDataService;
  @Autowired
  MajorCourseService majorCourseService;
  @Autowired
  RegionService regionService;

  @Test
  public void testReadWord() throws Exception {
//    String line =
//        majorDataService.readWord("C:\\Users\\Administrator\\Desktop\\22.txt");
    String line2 = readTxtFile("C:\\Users\\Administrator\\Desktop\\22.txt");
    System.out.println(line2);
    resolve(line2);
    
    String line1 = readTxtFile("C:\\Users\\Administrator\\Desktop\\11.txt");
    resolve2(line1);
  }

  public void resolve(String line) {
//    region = getGuiZhouRegionCode();
    region = "3";
    MajorDataModel majorData = null;
    MajorInfo majorInfo = null;
    String majorLevel = "";
    List<String> lineLists = Lists.newArrayList(line.split("\n"));
    if (CollectionUtils.isEmpty(lineLists)) return;
    for (String ele : lineLists) {
      if (StringUtils.isBlank(ele) || "".equals(ele.trim()) || ele.contains(DISCARD)) continue;
      if (ele.contains(DIVISION)) {
        if (null != majorData && majorInfo != null) {
          majorData.setMajorInfo(JSON.toJSONString(majorInfo));
          try {
            majorDataService.addMajor(majorData);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }

        // 新的一个专业
        majorData = new MajorDataModel();
        majorInfo = new MajorInfo();
        continue;
      }

      if (majorData != null && StringUtils.isNotBlank(ele) && ele.contains(ZYDM)
          && ele.contains(ZYMC)) {
        List<String> splits = Lists.newArrayList(ele.split(BLANK));
        if (!CollectionUtils.isEmpty(splits) && splits.size() > 1) {
          // 专业代码
          List<String> subSplits0 = Lists.newArrayList(splits.get(0).split("："));
          String majorDataCode = subSplits0.get(1).trim();
          majorData.setId(majorDataCode);
          // 专业名称
          List<String> subSplits1 = Lists.newArrayList(splits.get(1).split("："));
          String majorDataName = "";
          try {
            majorDataName = subSplits1.get(1).trim();
          } catch (Exception e) {
            e.printStackTrace();
          }
          majorData.setName(majorDataName);
          try {
            majorLevel =
                majorDataName.substring(majorDataName.indexOf("(") + 1, majorDataName.indexOf(")"));
            if("".equals(majorLevel)) {
              majorLevel =
                  majorDataName.substring(majorDataName.indexOf("（") + 1, majorDataName.indexOf("）"));
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
          // 开考类型
          // List<String> subSplits2 = Lists.newArrayList(splits.get(2).split("："));
          // String examType = subSplits2.get(1).trim();
          // 地域
          majorData.setRegion(region);
          continue;
        }
      }
      if (ele.contains(EXAMSCHOOL)) {
        // 主考院校
        List<String> subList = Lists.newArrayList(ele.split("："));
        String examSchool = subList.get(1);
        majorInfo.setExamSchool(examSchool.trim());
        majorInfo.setMajorLevel(majorLevel);
        continue;
      }

      if (StringUtils.isNotBlank(ele) && ele.contains(KCDM) && ele.contains(KCMC)) {
        continue;
      }

      MajorCourseModel majorCourse = new MajorCourseModel();
      List<String> subList = Lists.newArrayList(ele.split(BLANK));
      try {
        majorCourse.setId(subList.get(1).trim());
      } catch (Exception e) {
        e.printStackTrace();
      }

      try {
        majorCourse.setName(subList.get(2).trim());
      } catch (Exception e) {
        e.printStackTrace();
      }
      MajorCourseInfo courseInfo = new MajorCourseInfo();
      try {
        courseInfo.setCredit(subList.get(3).trim());
      } catch (Exception e) {
        e.printStackTrace();
      }
      majorCourse.setMajorCourseInfo(JSON.toJSONString(courseInfo));
      majorCourse.setRegion(region);
      try {
        // 先判断是否存在课程
        
        Page<MajorCourseModel> page = majorCourseService.queryMajorCourseByRegionAndCourseId(region, majorCourse.getId());
        if(null == page || page.getResult().size() == 0) {
          majorCourseService.addCourse(majorCourse);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      
    }

    System.out.println("action complete!!!");
  }
  
  public void resolve2(String line) {
    region = getGuiZhouRegionCode();
    MajorDataModel majorData = null;
    MajorInfo majorInfo = null;
    String majorLevel = "";
    List<String> lineLists = Lists.newArrayList(line.split("\n"));
    if (CollectionUtils.isEmpty(lineLists)) return;
    for (String ele : lineLists) {
      if (StringUtils.isBlank(ele) || "".equals(ele.trim()) || ele.contains(DISCARD)) continue;
      if (ele.contains(DIVISION)) {
        if (null != majorData && majorInfo != null) {
          majorData.setMajorInfo(JSON.toJSONString(majorInfo));
          try {
            majorDataService.addMajor(majorData);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }

        // 新的一个专业
        majorData = new MajorDataModel();
        majorInfo = new MajorInfo();
        continue;
      }

      if (majorData != null && StringUtils.isNotBlank(ele) && ele.contains(ZYDM)
          && ele.contains(ZYMC)) {
        List<String> splits = Lists.newArrayList(ele.split(TAB_BLANK));
        if (!CollectionUtils.isEmpty(splits) && splits.size() > 1) {
          // 专业代码
          List<String> subSplits0 = Lists.newArrayList(splits.get(0).split("："));
          String majorDataCode = subSplits0.get(1).trim();
          majorData.setId(majorDataCode);
          // 专业名称
          List<String> subSplits1 = Lists.newArrayList(splits.get(1).split("："));
          String majorDataName = "";
          try {
            majorDataName = subSplits1.get(1).trim();
          } catch (Exception e) {
            e.printStackTrace();
          }
          majorData.setName(majorDataName);
          try {
            majorLevel =
                majorDataName.substring(majorDataName.indexOf("(") + 1, majorDataName.indexOf(")"));
            if("".equals(majorLevel)) {
              majorLevel =
                  majorDataName.substring(majorDataName.indexOf("（") + 1, majorDataName.indexOf("）"));
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
          // 开考类型
          // List<String> subSplits2 = Lists.newArrayList(splits.get(2).split("："));
          // String examType = subSplits2.get(1).trim();
          // 地域
          majorData.setRegion(region);
          continue;
        }
      }
      if (ele.contains(EXAMSCHOOL)) {
        // 主考院校
        List<String> subList = Lists.newArrayList(ele.split("："));
        String examSchool = subList.get(1);
        majorInfo.setExamSchool(examSchool.trim());
        majorInfo.setMajorLevel(majorLevel);
        continue;
      }

      if (StringUtils.isNotBlank(ele) && ele.contains(KCDM) && ele.contains(KCMC)) {
        continue;
      }

      MajorCourseModel majorCourse = new MajorCourseModel();
      List<String> subList = Lists.newArrayList(ele.split(TAB_BLANK));
      try {
        majorCourse.setId(subList.get(1).trim());
      } catch (Exception e) {
        e.printStackTrace();
      }

      try {
        majorCourse.setName(subList.get(2).trim());
      } catch (Exception e) {
        e.printStackTrace();
      }
      MajorCourseInfo courseInfo = new MajorCourseInfo();
      try {
        courseInfo.setCredit(subList.get(3).trim());
      } catch (Exception e) {
        e.printStackTrace();
      }
      majorCourse.setMajorCourseInfo(JSON.toJSONString(courseInfo));
      majorCourse.setRegion(region);
      try {
        // 先判断是否存在课程
        
        Page<MajorCourseModel> page = majorCourseService.queryMajorCourseByRegionAndCourseId(region, majorCourse.getId());
        if(null == page || page.getResult().size() == 0) {
          majorCourseService.addCourse(majorCourse);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      
    }

    System.out.println("action2 complete!!!");
  }

  private String getGuiZhouRegionCode() {
    RegionModel region = regionService.findCodeByRegion(GZ);
    if (null == region) return StringUtils.EMPTY;
    return region.getModule();
  }

  public String readTxtFile(String path) {
    StringBuilder sb = new StringBuilder();
    File file = new File(path);
    BufferedReader reader = null;
    String temp = null;
    int line = 1;
    try {
      reader = new BufferedReader(new FileReader(file));
      while ((temp = reader.readLine()) != null) {
        System.out.println("line" + line + ":" + temp);
        sb.append(temp).append("\n");
        line++;
      }
      return sb.toString();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    return temp;
  }
}
