package com.xiaodou.logmonitor.bolt;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.jmsg.entity.DefaultMessage;
import com.xiaodou.logmonitor.bean.ApplicationManager;
import com.xiaodou.logmonitor.constant.Constant;
import com.xiaodou.logmonitor.dao.JmsgMessageDao;
import com.xiaodou.logmonitor.domain.CommonModel;
import com.xiaodou.logmonitor.domain.JmsgMessageModel;

public class JmsgMessageBolt extends BaseBolt {

  /** serialVersionUID */
  private static final long serialVersionUID = -7634914196957195170L;

  OutputCollector collector;

  @Override
  public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
    this.collector = collector;
  }

  @Override
  public void execute(Tuple input) {
    CommonModel model = (CommonModel) input.getValue(0);
    if (Constant.jmsgmessage.equals(model.getLogName())) {
      String messageBody = model.getActionModel();
      if (StringUtils.isJsonBlank(messageBody)) return;
      DefaultMessage msg = null;
      try {
        msg = FastJsonUtil.fromJson(messageBody, DefaultMessage.class);
      } catch (Exception e) {
        return;
      }
      if (null == msg) return;
      JmsgMessageModel messageModel = new JmsgMessageModel();
      messageModel.setMessageName(msg.getMessageName());
      messageModel.setCustomTag(msg.getCustomTag());
      messageModel.setMessageBody(msg);
      try {
        JmsgMessageDao dao = ApplicationManager.getContext().getBean(JmsgMessageDao.class);
        dao.addEntity(messageModel);
        collector.ack(input);
      } catch (Exception e) {
        LoggerUtil.error("JmsgMessageBolt保存日志信息失败", e);
        collector.fail(input);
      }
    }
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare(new Fields("filter-stock"));
  }

}
