package com.xiaodou.server.pay.service.facade;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.server.pay.dao.PayRecordDao;
import com.xiaodou.server.pay.dao.PayTokenDao;
import com.xiaodou.server.pay.model.PayRecord;
import com.xiaodou.server.pay.model.PayToken;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

/**
 * @name @see com.xiaodou.server.order.service.facade.OrderServiceFacadeImpl.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:lidehong@corp.51xiaodou.com">lidehong</a>
 * @date 2016年3月25日12:01:54
 * @description 支付领域服务层Facade实现
 * @version 1.0
 */
@Service("payServiceFacade")
public class PayServiceFacadeImpl implements IPayServiceFacade {
  @Resource
  private PayRecordDao payRecordDao;

  @Resource
  private PayTokenDao payTokenDao;


  @Override
  public PayRecord queryPayRecordByTradeNo4Update(String tradeNo) {
    return payRecordDao.findEntityByToken4Update(tradeNo);
  }

  @Override
  public PayRecord queryPayRecordByDcTradeNo4Update(String dcTradeNo) {
    return payRecordDao.findEntityByDcOrderNo4Update(dcTradeNo);
  }

  @Override
  public Page<PayRecord> queryPayRecordList(Map<String, Object> cond, Map<String, Object> briefInfo) {
    IQueryParam param = new QueryParam();
    param.addInputs(cond);
    param.addOutputs(briefInfo);
    return payRecordDao.findEntityListByCond(param, null);
  }

  @Override
  public PayRecord insertPayRecord(PayRecord model) {
    return payRecordDao.addEntity(model);
  }

  @Override
  public PayToken insertPayToken(PayToken token) {
    return payTokenDao.addEntity(token);
  }

  @Override
  public boolean updatePayRecord(PayRecord model) {
    return payRecordDao.updateEntityById(model);
  }

  @Override
  public Page<PayToken> queryPayToken(IQueryParam param) {
    return payTokenDao.findEntityListByCond(param, null);
  }

  @Override
  public boolean updatePayToken(PayToken token) {
    return payTokenDao.updateEntityById(token);
  }

}
