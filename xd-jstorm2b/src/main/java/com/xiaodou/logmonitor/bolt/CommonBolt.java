package com.xiaodou.logmonitor.bolt;

import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.logmonitor.bean.ApplicationManager;
import com.xiaodou.logmonitor.dao.CommonDao;
import com.xiaodou.logmonitor.dao.TraceDao;
import com.xiaodou.logmonitor.domain.CommonModel;
import com.xiaodou.logmonitor.domain.TraceModel;


public class CommonBolt extends BaseBolt {
	private static final long serialVersionUID = 1804939402092249890L;
	OutputCollector collector;

	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;
	}

	@Override
	public void execute(Tuple input) {
		String[] s = input.getString(0).split("\\|");
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		try {
			String s1 = s[0].replace(",", ".");
			ts = Timestamp.valueOf(s1);
		} catch (Exception e) {
			e.printStackTrace();
			LoggerUtil.error("时间转换失败", e);
			collector.fail(input);
		}
		// 经典日志流
		if (s.length == 14 || s.length == 15) {
			this.packageCommonModel(input, s, ts);
		}

		// 业务方法流
		if (s.length == 19) {
			this.packageTraceModel(input, s, ts);
		}
	}

	private void packageCommonModel(Tuple input, String[] s, Timestamp ts) {
		CommonModel model = new CommonModel();
		try {
			model.setActionId(UUID.randomUUID().toString());
			model.setsLogTime(s[0]);
			model.setLogTime(ts);
			model.setClientIp(s[1]);
			model.setTraceId(s[2]);
			model.setTraceNumber(s[3]);
			model.setProjectName(s[4]);
			model.setExcutePoint(s[5]);
			model.setServerName(s[6]);
			model.setLocalIp(s[7]);
			model.setLocalMac(s[8]);
			model.setLogName(s[9]);
			model.setHasError(Boolean.valueOf(s[10]));
			if (s.length == 15 && StringUtils.isNotBlank(s[10])) {
				model.setErrorMessage(s[11]);
				model.setActionModel(s[12]);
				model.setStandby(s[13]);
				model.setUseTime(s[14]);
			}
			CommonDao dao = ApplicationManager.getContext().getBean(
					CommonDao.class);
			dao.addEntity(model);
			collector.ack(input);
		} catch (Exception e) {
			LoggerUtil.error("CommonBolt执行失败", e);
			collector.fail(input);
		}
		collector.emit(input, new Values(model));
	}

	private void packageTraceModel(Tuple input, String[] s, Timestamp ts) {
		// TODO Auto-generated method stub
		TraceModel model = new TraceModel();
		try {
			model.setActionId(UUID.randomUUID().toString());
			model.setsLogTime(s[0]);
			model.setLogTime(ts);
			model.setClientIp(s[1]);
			model.setProjectName(s[2]);
			model.setExcutePoint(s[3]);
			model.setServerName(s[4]);
			model.setLocalIp(s[5]);
			model.setLocalMac(s[6]);
			model.setLogName(s[7]);
			model.setStandBy(s[8]);
			model.setTraceId(s[9]);
			model.setProjectId(s[10]);
			model.setProcessId(s[11]);
			model.setExecutionTime(s[12]);
			model.setPersonalityData(s[13]);
			model.setParentId(s[14]);
			model.setMyId(s[15]);
			model.setEntryParameter(s[16]);
			model.setExcuteResult(s[17]);
			model.setErrorMsg(s[18]);
			TraceDao dao = ApplicationManager.getContext().getBean(
					TraceDao.class);
			dao.addEntity(model);
			collector.ack(input);
		} catch (Exception e) {
			LoggerUtil.error("TraceModel日志记录失败", e);
			collector.fail(input);
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("filter-stock"));
	}

	@Override
	public void alarm() {

	}
}
