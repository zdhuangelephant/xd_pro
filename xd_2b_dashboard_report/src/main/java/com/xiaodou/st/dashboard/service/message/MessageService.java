package com.xiaodou.st.dashboard.service.message;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.jmsg.client.RabbitMQSender;
import com.xiaodou.st.dashboard.constants.Constants;
import com.xiaodou.st.dashboard.domain.apply.ApplyDO;
import com.xiaodou.st.dashboard.domain.message.StudentMessage;
import com.xiaodou.st.dashboard.domain.message.StudentMessage.StudentMessageBody;
import com.xiaodou.st.dashboard.domain.message.StudentMessage.StudentMessageBodyDTO;
import com.xiaodou.st.dashboard.domain.student.StudentDO;
import com.xiaodou.st.dashboard.service.facade.IStServiceFacade;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * 发消息
 * 
 * @name MessageService CopyRright (c) 2017 by 李德洪
 * 
 * @author 李德洪
 * @date 2017年4月20日
 * @description TODO
 * @version 1.0
 */

@Service
public class MessageService {

  @Resource
  IStServiceFacade stServiceFacade;

  public void sendStudentMessage(List<StudentMessageBodyDTO> stmList) {
    StudentMessageBody messageBody = new StudentMessageBody();
    messageBody.setMessageBody(stmList);
    RabbitMQSender.getInstance().send(new StudentMessage(messageBody));
  }

  public void quartzStudent() {
    List<StudentMessageBodyDTO> stmList = Lists.newArrayList();
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("studentStatus", Constants.NOT_REGISTER);
    //inputs.put("id", 303);//测试数据
    Page<StudentDO> page =
        stServiceFacade.listStudent(inputs, CommUtil.getAllField(StudentDO.class), null);
    if (null == page || null == page.getResult() || page.getResult().size() < 1) return;
    for (StudentDO sdo : page.getResult()) {
      Map<String, Object> input = Maps.newHashMap();
      inputs.put("studentId", sdo.getId());
      Page<ApplyDO> applyPage =
          stServiceFacade.listApply(input, CommUtil.getAllField(ApplyDO.class),null);
      if (null == applyPage || null == applyPage.getResult() || applyPage.getResult().size() < 1)
        break;
      StudentMessageBodyDTO stm = new StudentMessageBodyDTO();
      stm.setStudentId(sdo.getId());
      stm.setAdmissionCardCode(sdo.getAdmissionCardCode());
      stm.setGender(sdo.getGender());
      stm.setIdentificationCardCode(sdo.getIdentificationCardCode());
      stm.setPilotUnitName(sdo.getPilotUnitName());
      stm.setRealName(sdo.getRealName());
      stm.setTelephone(sdo.getTelephone());
      stmList.add(stm);
    }
    this.sendStudentMessage(stmList);
  }

}
