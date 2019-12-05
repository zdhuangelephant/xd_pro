package com.xiaodou.dashboard.dao.trace;

import org.springframework.stereotype.Repository;

import com.xiaodou.dashboard.dao.LogMonitorBaseDao;
import com.xiaodou.dashboard.model.trace.TraceModel;

@Repository("traceModelDao")
public class TraceModelDao extends LogMonitorBaseDao<TraceModel> {

}
