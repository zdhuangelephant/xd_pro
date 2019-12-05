package com.xiaodou.ms.service.common;

import java.util.UUID;

import org.springframework.util.StringUtils;

import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.push.agent.client.PushClient;
import com.xiaodou.push.agent.enums.PushMessageProLine;
import com.xiaodou.push.agent.model.Message;

/**
 * @name @see com.xiaodou.ms.service.common.PushService.java
 * @CopyRright (c) 2018 by XiaoDou NetWork Technology
 *
 * @author <a href="mailto:lidehong@corp.51xiaodou.com">lidehong</a>
 * @date 2018年6月7日
 * @description 。。。
 * @version 1.0
 */
public class PushService {

    public static void toTagPush(BaseResponse response, Message pushMessage, String tag) {
        // pushMessage.addGroup(FileUtils.CONFIG.getProperties("xd.jpush.environment").trim());

        if (StringUtils.isEmpty(tag)) {
            response = new BaseResponse(ResultType.PUSH_TAG_NULL_ERROR);
        }
        pushMessage.addGroup(tag.trim());
        pushMessage.setAppMessageId(UUID.randomUUID().toString());
        pushMessage.setProductLine(PushMessageProLine.MIS_MESSAGE.name());
        PushClient.push(pushMessage);
    }

}
