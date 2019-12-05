package com.xiaodou.ms.vo.mq;

import java.sql.Timestamp;

import lombok.Data;

import com.xiaodou.dataclean.core.event.DCCoreEvent;
import com.xiaodou.ms.model.score.ScoreRuleModel;
import com.xiaodou.ms.vo.mq.AddScoreRuleEvent.TransferScoreRuleData;

/**
 * @name AddScoreRuleEvent CopyRright (c) 2018 by <a
 *       href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * 
 * @author <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * @date 2018年4月26日
 * @description 添加计分规则事件
 * @version 1.0
 */
public class AddScoreRuleEvent extends DCCoreEvent<TransferScoreRuleData> {
  private static final String EVENT_NAME = "addScoreRule";

  public AddScoreRuleEvent() {
    setEventName(EVENT_NAME);
    // setModule(XdmsConstant.MODULE);
  }


  @Data
  public static class TransferScoreRuleData {
    public TransferScoreRuleData(ScoreRuleModel model, String module) {
      this.id = model.getId();
      this.module = module;
      this.ruleName = model.getRuleName();
      this.ruleDetail = model.getRuleDetail();
      this.ruleDesc = model.getRuleDesc();
      this.scope = model.getScope();
      this.createTime = model.getCreateTime();
      this.modifyTime = model.getModifyTime();
    }

    public TransferScoreRuleData(String id, String module) {
      this.id = id;
      this.module = module;
    }

    private String id;
    private String module;
    private String ruleName;
    private String ruleDetail;
    private String ruleDesc;
    private Integer scope;
    private Timestamp createTime;
    private Timestamp modifyTime;
  }
}
