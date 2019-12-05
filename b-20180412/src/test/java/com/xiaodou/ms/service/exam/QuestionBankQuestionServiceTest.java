package com.xiaodou.ms.service.exam;

import com.xiaodou.course.BaseUnitils;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import static org.junit.Assert.*;

public class QuestionBankQuestionServiceTest extends BaseUnitils {

  @SpringBean("questionBankQuestionService")
  QuestionBankQuestionService questionBankQuestionService;

  @Test
  public void testUpdateTest() throws Exception {
    questionBankQuestionService.updateTest();
  }
}
