package com.xiaodou.dao;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xiaodou.mqcr.service.MessageEntityService;
import com.xiaodou.service.QuesRecordService;
import com.xiaodou.vo.request.LearnRecordViewRequest;
import com.xiaodou.vo.response.BaseResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:conf/core/springmvc.xml"})
public class LearnRecordTest {

    @Resource
    QuesRecordService quesRecordService;

    @Resource
    MessageEntityService messageEntityService;

    @Test
    public void learnRecordView() {
        LearnRecordViewRequest pojo = new LearnRecordViewRequest();
        pojo.setCourseId("226653481");
        pojo.setModule("3");
        pojo.setTypeCode("01B0810");
        pojo.setUid("15663197");
        // RealSqlSessionKeyHolder.getHolder().setIsUsekey(true);
        // RealSqlSessionKeyHolder.getHolder().setRealSqlSessionKey("3");
        BaseResponse response = quesRecordService.learnRecordView(pojo);
        System.out.println(response.toString0());

    }

    @Test
    @Ignore
    public void a() {
        System.out.println(messageEntityService.canConsumMessage("123"));
    }


}
