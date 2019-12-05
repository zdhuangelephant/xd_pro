package com.xiaodou.ms.service.schedule;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.ms.constants.XdmsConstant;
import com.xiaodou.ms.dao.knowledge.PushRecordDao;
import com.xiaodou.ms.enums.Content;
import com.xiaodou.ms.model.knowledge.PushRecordModel;
import com.xiaodou.ms.model.user.UserModel;
import com.xiaodou.ms.service.common.PushService;
import com.xiaodou.ms.service.knowledge.ForumService;
import com.xiaodou.ms.service.knowledge.PushRecordService;
import com.xiaodou.ms.service.order.ProductOrderService;
import com.xiaodou.ms.service.product.ProductService;
import com.xiaodou.ms.service.user.UserService;
import com.xiaodou.ms.util.FileUtils;
import com.xiaodou.ms.util.RandomUtils;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.push.agent.enums.MessageType;
import com.xiaodou.push.agent.enums.SpreadRange;
import com.xiaodou.push.agent.enums.TargetType;
import com.xiaodou.push.agent.model.Message;
import com.xiaodou.push.agent.util.PushUtil;

/**
 * 
 * @author zdh:
 * @date 2017年7月14日
 * 
 */
@Service("crontabService")
public class CrontabService {

    @Resource
    ForumService forumService;
    @Resource
    ProductService productService;
    @Resource
    UserService userService;
    @Resource
    ProductOrderService productOrderService;
    @Resource
    PushRecordService pushRecordService;
    @Resource
    PushRecordDao pushRecordDao;

    public BaseResponse pushForum(String tag) {
        Integer exceeds =
                Integer.parseInt(FileUtils.CONFIG.getProperties("kechengdingyue.resource.push.exceeds").trim());
        if (exceeds == 0) {
            BaseResponse response = new BaseResponse();
            response.setRetCode(ResultType.SYS_FAIL.getRetCode());
            response.setRetDesc("推送次数设置为0次,已关闭该推送功能");
            return response;
        }
        // modified by zdh at 2017-07-13
        // 1: 资源管理 2:校园之声 3:定时任务
        HashMap<String, Object> inputs = Maps.newHashMap();
        inputs.put("typeId", 3);
        inputs.put("currentTime", XdmsConstant.sdf.format(new Date()));
        List<PushRecordModel> prm_lists = pushRecordService.findListByCond(inputs);
        if (prm_lists != null && prm_lists.size() > 0 && prm_lists.get(0).getIsPush() >= exceeds) {
            BaseResponse response = new BaseResponse();
            response.setRetCode(ResultType.SYS_FAIL.getRetCode());
            response.setRetDesc("一天只能推送" + exceeds + "次");
            return response;
        }

        // 该记录是空 的
        List<UserModel> lists = userService.getAvailiableEntityList();
        if (lists != null && lists.size() > 0) {
        	List<String> push_unique_ids = Lists.newArrayList();
            int count = 0;
            for (UserModel userModel : lists) {
                if (null != userModel && StringUtils.isNotBlank(userModel.getXdUniqueId())) {
                    push_unique_ids.add(userModel.getXdUniqueId());
                    count++;
                    if (count == 1000) {
                        pushForum(push_unique_ids, tag);
                        push_unique_ids.clear();
                        count = 0;
                    }
                }
            }
            pushForum(push_unique_ids, tag);

            // 进行标记录入
            PushRecordModel pushRecordModel = new PushRecordModel();
            pushRecordModel.setTypeId(Integer.parseInt("3"));
            pushRecordModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
            pushRecordModel.setIsPush(1);

            HashMap<String, Object> cond = Maps.newHashMap();
            cond.put("typeId", 3);
            cond.put("currentTime", XdmsConstant.sdf.format(new Date()));
            List<PushRecordModel> cond_lists = pushRecordService.findListByCond(cond);
            if (cond_lists != null && cond_lists.size() > 0) {
                PushRecordModel prm = cond_lists.get(0);
                prm.setIsPush(prm.getIsPush() + 1);
                pushRecordDao.updateEntityById(prm);
            } else {
                pushRecordDao.addEntity(pushRecordModel);
              	BaseResponse response = new BaseResponse();
                response.setRetCode(ResultType.SUCCESS.getRetCode());
                response.setRetDesc("操作成功");
                //response.setServerIp(ResultType.SUCCESS.getServerIp());
                return response;
            }
        } else {
        	BaseResponse response = new BaseResponse();
            response.setRetCode(ResultType.SYS_FAIL.getRetCode());
            response.setRetDesc("没有可推送的新增资源");
            //response.setServerIp(ResultType.VALFAIL.getServerIp());
            return response;
        }
        return new BaseResponse(ResultType.SUCCESS);
    }

    private void pushForum(List<String> push_unique_ids, String tag) {
    	BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    	// listPhone = Lists.newArrayList("13716977259");
        Message pushMessage = new Message();
        int count = RandomUtils.generateData(0, Content.values().length - 1);
        String msg = Content.getByCode(count).getContent();
        pushMessage.setMessageContent(msg);
        pushMessage.setNoticeContent(msg);
        pushMessage.setMessageType(MessageType.ALL);
        SpreadRange scope = SpreadRange.ALIAS_TAG;
        pushMessage.setScope(scope); // 原先是ALL，考虑加入TAG
        pushMessage.setTarget(TargetType.ALL);
        pushMessage.addReciever(push_unique_ids);
        Map<String, String> messageextras = new HashMap<String, String>();
        Map<String, String> noticeextras = new HashMap<String, String>();

        PushUtil.setRetCode(noticeextras, "10411");
        PushUtil.setRetDesc(noticeextras, "已发布的资源全网推送");

        PushUtil.setRetCode(messageextras, "10411");
        PushUtil.setRetDesc(messageextras, "已发布的资源全网推送");

        pushMessage.setMessageextras(messageextras); // 消息参数
        pushMessage.setNoticeextras(noticeextras); // 通知参数
        PushService.toTagPush(response,pushMessage, tag);

    }

}
