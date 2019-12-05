package com.xiaodou.message.service.sender.task;
import java.util.List;
import java.util.Map;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.enums.CallBackStatus;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AliyunWorker;
import com.xiaodou.sms.model.SmsTaskModel;
import com.xiaodou.sms.service.facade.ISmsServiceFacade;
import com.xiaodou.summer.util.SpringWebContextHolder;

/**
 * @name @see com.xiaodou.sms.service.sender.SmsSender.java
 * @CopyRright (c) 2015 by <a
 *             href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年7月31日
 * @description 短信发送worker
 * @version 1.0
 */
public class MsSender extends AliyunWorker {
    public MsSender() {
        super();
    }

    ISmsServiceFacade smsServiceFacade = null;

    public ISmsServiceFacade getSmsServiceFacade() {
        if (null == smsServiceFacade)
            smsServiceFacade = SpringWebContextHolder
                    .getBean("smsServiceFacade");
        return smsServiceFacade;
    }

    /** serialVersionUID */
    private static final long serialVersionUID = 5038846578846120960L;

    @Override
    public void domain(DefaultMessage message,
            final IMQCallBacker<DefaultMessage> callback) {
        IMQCallBacker<DefaultMessage> _callback = new IMQCallBacker<DefaultMessage>() {
            @Override
            public void onSuccess(DefaultMessage message) {
                SmsTaskModel task = FastJsonUtil.fromJson(
                        message.getMessageBodyJson(), SmsTaskModel.class);
                Map<String, Object> condition = Maps.newHashMap();
                condition.put("id", task.getId());
                SmsTaskModel _task = new SmsTaskModel();
                _task.setStatus(1);
                getSmsServiceFacade()
                        .updateSmsTaskModelEntity(condition, _task);
                callback.onSuccess(message);
            }

            @Override
            public void onFail(CallBackStatus staus, DefaultMessage message) {
                callback.onFail(staus, message);
            }
        };
        super.domain(message, _callback);
    }

    @Override
    public void domain(List<DefaultMessage> messageLst,
            final IMQCallBacker<List<DefaultMessage>> callback) {
        IMQCallBacker<List<DefaultMessage>> _callback = new IMQCallBacker<List<DefaultMessage>>() {
            @Override
            public void onSuccess(List<DefaultMessage> messageLst) {
                List<Long> idList = Lists.newArrayList();
                for (DefaultMessage message : messageLst) {
                    SmsTaskModel task = FastJsonUtil.fromJson(
                            message.getMessageBodyJson(), SmsTaskModel.class);
                    idList.add(task.getId());
                }
                Map<String, Object> condition = Maps.newHashMap();
                condition.put("idList", idList);
                SmsTaskModel _task = new SmsTaskModel();
                _task.setStatus(1);
                getSmsServiceFacade()
                        .updateSmsTaskModelEntity(condition, _task);
                callback.onSuccess(messageLst);
            }

            @Override
            public void onFail(CallBackStatus staus,
                    List<DefaultMessage> messageLst) {
                callback.onFail(staus, messageLst);
            }
        };

        super.domain(messageLst, _callback);
    }
}
