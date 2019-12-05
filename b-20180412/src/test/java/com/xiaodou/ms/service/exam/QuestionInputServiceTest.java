package com.xiaodou.ms.service.exam;

import com.xiaodou.course.BaseUnitils;
import com.xiaodou.ms.vo.NeedProcessCsv;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @name @see com.xiaodou.ms.service.exam.QuestionInputServiceTest.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月5日
 * @description 试题批量导入单元测试类
 * @version 1.0
 */
public class QuestionInputServiceTest extends BaseUnitils {

  @SpringBean("questionInputService")
  QuestionInputService questionInputService;

  @Test
  public void testInitCsvList() throws Exception {
    List<NeedProcessCsv> csvList = questionInputService.initCsvList();
  }

  @Test
  public void testPreProcess() throws Exception {
    try {
      questionInputService.preProcess();
    } catch (Exception e){
      System.out.print(e.getMessage());
    }
  }

  @Test
  public void testProcess() throws Exception {
    try {
      questionInputService.process();
    } catch (Exception e){
      System.out.print(e.getMessage());
    }
  }

  @Test
  public void testProcessKeyword() {
    try {
      questionInputService.processKeywordRelation();
    } catch (Exception e){
      System.out.print(e.getMessage());
    }
  }
}
