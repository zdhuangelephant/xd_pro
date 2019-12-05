package com.xiaodou._2b_dashboard_clean;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.xiaodou.st.dataclean.web.batch.BatchController;

public class ApplyTest extends BaseUnitils {

//  @SpringBean("commonService")
//  CommonService commonService;
  @SpringBean("batchController")
  BatchController batchController;
  
  @Test
  public void bb() throws Exception {
//    Integer count = commonService.getPilotUnitStudentCount("142");
//    Integer count = commonService.getTaughtUnitStudentCount();
//    System.out.println(count);
//    System.out.println(RandomUtil.randomNumber(12));
    System.out.println(batchController.calculateEverydaySummaryActionJob()); 
  }
  
}
