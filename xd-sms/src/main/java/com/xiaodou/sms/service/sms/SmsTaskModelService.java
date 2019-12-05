package com.xiaodou.sms.service.sms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.xiaodou.sms.model.SmsTaskModel;
import com.xiaodou.sms.service.facade.ISmsServiceFacade;
import com.xiaodou.sms.service.platform.SendMessageService;

/**
 * 任务表Service
 * 
 * @author wuyunkuo
 * 
 */
@Service("smsTaskModelService")
public class SmsTaskModelService {

  @Resource
  ISmsServiceFacade smsServiceFacade;

  @Resource
  SendMessageService sendMessageService;

  /**
   * 查询出要发送的短信，并取出6条进行处理
   * 
   * @return taskList
   */
  public List<SmsTaskModel> quryList(int maxMum) {
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("status", 0);
    List<SmsTaskModel> taskList = smsServiceFacade.querySmsTaskModelList(input, null);
    if (null == taskList || taskList.size() == 0)
      return null;
    // 每次处理maxNum条
    else if (taskList.size() > maxMum) for (int i = maxMum; i < taskList.size();) {
      taskList.remove(i);
    }
    return taskList;
  }

  /**
   * 根据Id删除任务表
   * 
   * @param id
   */
  public void deleteTask(long id) {
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("id", id);
    smsServiceFacade.deleteSmsTaskModelList(input);
  }


}
