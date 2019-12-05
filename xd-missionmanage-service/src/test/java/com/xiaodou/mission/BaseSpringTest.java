package com.xiaodou.mission;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @name @see com.xiaodou.mission.BaseSpringTest.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 基础测试类
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/conf/core/xd-missionmanage-service-servlet.xml"})
public class BaseSpringTest {
	@Test
	public void test(){
		System.out.println("basetest");
	}
}
