package com.xiaodou.dao;

import java.util.List;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.xiaodou.domain.product.FinalExamModel;
import com.xiaodou.manager.facade.QuesOperationFacade;

public class FacadeTest extends BaseUnitils {

  @SpringBean("quesOperationFacade")
  QuesOperationFacade quesOperationFacade;

  @Test
  public void selectFinalExamByCond() {
    String courseId = "10289153";
    String userId = "1";
    try {
      List<FinalExamModel> list = quesOperationFacade.selectFinalExamByCond(courseId, userId);
      System.out.println(list.size());
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
