package com.xiaodou.ms.plus;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.xiaodou.ms.service.major.MajorDataService;
import com.xiaodou.ms.util.IDGenerator;

@ContextConfiguration(locations = {"classpath:conf/core/xdms-servlet.xml"})
public class DoCopySubjectTest extends AbstractJUnit4SpringContextTests {

  @Autowired
  MajorDataService majorDataService;

  @Test
  public void testReadWord() throws Exception {
   String seqID = IDGenerator.getSeqID();
   System.out.println(seqID);
  }

  
}
