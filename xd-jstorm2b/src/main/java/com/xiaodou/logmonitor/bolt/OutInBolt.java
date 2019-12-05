package com.xiaodou.logmonitor.bolt;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.logmonitor.alarm.AlarmUtil;
import com.xiaodou.logmonitor.constant.Constant;
import com.xiaodou.logmonitor.domain.CommonModel;
import com.xiaodou.logmonitor.domain.OutInModel;
import com.xiaodou.logmonitor.statistic.OutInStatisticInfo;
import com.xiaodou.logmonitor.statistic.OverTimeStatisticInfo;

public class OutInBolt extends BaseBolt {
  private static final long serialVersionUID = 1804939402092249890L;
  OutputCollector collector;

  @Override
  public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
    this.collector = collector;
  }

  @Override
  public void execute(Tuple input) {
    CommonModel model = (CommonModel) input.getValue(0);
    collector.ack(input);
    if (Constant.outin.equals(model.getLogName())) {
      OutInModel outInModel = new OutInModel();
      outInModel.setOutinId(model.getActionId());
      outInModel.setLogTime(model.getLogTime());
      outInModel.setsLogTime(model.getsLogTime());
      outInModel.setLocalIp(model.getLocalIp());
      outInModel.setProjectName(model.getProjectName());
      outInModel.setExcutePoint(model.getExcutePoint());
      outInModel.setServerName(model.getServerName());
      outInModel.setLocalMac(model.getLocalMac());
      outInModel.setLogName(model.getLogName());
      outInModel.setClientIp(model.getClientIp());
      outInModel.setHasError(model.getHasError());
      outInModel.setErrorMessage(model.getErrorMessage());
      outInModel.setUseTime(model.getUseTime());
      OutInStatisticInfo info = new OutInStatisticInfo(outInModel);
      info.pushOutInStatistic();
      try {
        if (model.getUseTime() != null && !model.getUseTime().equals("null")
            && !model.getUseTime().equals("null\n")) {
          Long useTime;
          if (model.getUseTime().endsWith("\r\n")) {
            useTime = Long.valueOf(model.getUseTime().split("\r\n")[0].trim());
          } else if (model.getUseTime().endsWith("\n")) {
            useTime = Long.valueOf(model.getUseTime().split("\n")[0].trim());
          } else {
            useTime = Long.valueOf(model.getUseTime().trim());
          }
          if (useTime > Constant.OVERTIME) {
            OverTimeStatisticInfo overTime = new OverTimeStatisticInfo(outInModel);
            overTime.pushOutInStatistic();
            AlarmUtil.alarmOverTime(overTime, info);
          }
        }
      } catch (Exception e) {
        LoggerUtil.error("报警逻辑失败", e);
      }
      AlarmUtil.alarmOutIn(info);
    }
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare(new Fields("filter-stock"));
  }

}
