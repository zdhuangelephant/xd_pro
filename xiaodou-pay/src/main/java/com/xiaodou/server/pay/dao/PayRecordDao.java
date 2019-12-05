package com.xiaodou.server.pay.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.xiaodou.server.pay.model.PayRecord;
import com.xiaodou.summer.dao.BaseDao;

/**
 * @name @see com.xiaodou.server.pay.dao.PayRecordDao.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年5月30日
 * @description 支付记录Dao
 * @version 1.0
 */
@Repository("payRecordDao")
public class PayRecordDao extends BaseDao<PayRecord> {

  public PayRecord findEntityByToken4Update(String tradeNo) {
    PayRecord record = new PayRecord();
    record.setTradeNo(tradeNo);
    return (PayRecord) getSqlSession().selectOne("PayRecord.findEntityByToken4Update", record);
  }

  public PayRecord findEntityByDcOrderNo4Update(String dcOrderNo) {
    PayRecord record = new PayRecord();
    record.setDcTradeNo(dcOrderNo);
    return (PayRecord) getSqlSession().selectOne("PayRecord.findEntityByDcOrderNo4Update", record);
  }
  
}
