package com.xiaodou.ms.push;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.xiaodou.ms.service.user.UserNoticeService;
import com.xiaodou.ms.web.response.BaseResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:conf/core/xdms-servlet.xml"})
public class PushTest {

    @Resource
    UserNoticeService userNoticeService;
    
    @Test
    public void test1() {
        BaseResponse response = userNoticeService.pushForum(1L, "4", 0, "xd_pre_release");
        Assert.notNull(response);
        System.out.println(response.toString());
    }
}
