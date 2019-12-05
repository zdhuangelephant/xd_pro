package com.xiaodou.ucenter;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.xiaodou.common.info.CommonInfoUtil;
import com.xiaodou.common.info.model.ModuleInfo;
import com.xiaodou.userCenter.request.BaseRequest;
import com.xiaodou.userCenter.request.ModifyMyInfoRequest;
import com.xiaodou.userCenter.response.UserInfoByTokenResponse;
import com.xiaodou.userCenter.service.UcenterService;
import com.xiaodou.userCenter.service.queue.QueueService;
import com.xiaodou.userCenter.service.queue.QueueService.PushLoginOutDTO;
import com.xiaodou.userCenter.util.CreditLogIDGenerator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:conf/core/xd-ucenter-servlet.xml"})
public class UecnterTest {

    @Resource
    UcenterService ucenterService;

    @Resource
    QueueService queueService;

    // @Test
    public void test() {
        // ModuleInfo mi = CommonInfoUtil.getDefauleModuleInfo();
        // System.out.println(mi.toString());
        ModuleInfo mi1 = CommonInfoUtil.getModuleInfoByModuleName("北京");
        System.out.println(mi1.toString());
    }

    @Test
    @Ignore
    public void aa() throws Exception {
        BaseRequest pojo = new BaseRequest();
        pojo.setClientIp("172.17.20.43");
        pojo.setClientType("123");
        pojo.setDeviceId("14:9d:09:c6:a6:e6A00000769D6E5D");
        pojo.setSessionToken("1a1a028c-c748-4760-850d-bd26563752b7");
        pojo.setTraceId("123");
        pojo.setVersion("1.5.0");
        UserInfoByTokenResponse a = ucenterService.checkToken(pojo,"");
        System.out.println(a.toJsonString());
    }

    @Test
    @Ignore
    public void bb() {
        ModifyMyInfoRequest pojo = new ModifyMyInfoRequest();
        pojo.setMajor("01A0505");
        pojo.setMajorId("11");
        pojo.setMajorName("");
        pojo.setRegion("2");
        pojo.setRegionName("西安");
        ucenterService.checkValidModifyUserInfo(pojo);
    }
    
    @Test
    @Ignore
    public void aaa() {
        PushLoginOutDTO pushLoginOutDTO = new PushLoginOutDTO();
        queueService.pushLoginOut(pushLoginOutDTO);
        while(true) {}
    }
    
    
    /**
     * @description TODO
     * @author <a href="mailto:lidehong@corp.51xiaodou.com">lidehong</a>
     * @Date 2018年5月31日
     */
    @Test
    @Ignore
    public void a() {
        Assert.isNull(null);
        Assert.isNull("123", "需要为空");
    }
    
    @Test
    public void b() {
    	String a = CreditLogIDGenerator.getSeqID();
    	System.out.println(a);
    }
}
