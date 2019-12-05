package com.xiaodou.server.pay.service.facade;

import java.util.Map;

import com.xiaodou.server.pay.model.PayRecord;
import com.xiaodou.server.pay.model.PayToken;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;

/**
 * @name @see com.xiaodou.server.order.service.facade.OrderServiceFacadeImpl.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:lidehong@corp.51xiaodou.com">lidehong</a>
 * @date 2016年4月3日12:01:54
 * @description 支付领域服务层Facade实现
 * @version 1.0
 */
public interface IPayServiceFacade {

  PayRecord queryPayRecordByTradeNo4Update(String tradeNo);
  
  PayRecord queryPayRecordByDcTradeNo4Update(String dcTradeNo);
  
  Page<PayRecord> queryPayRecordList(Map<String, Object> cond, Map<String, Object> briefInfo);

  PayRecord insertPayRecord(PayRecord model);

  boolean updatePayRecord(PayRecord model);

  /********************** pay token *************************/
  PayToken insertPayToken(PayToken token);

  Page<PayToken> queryPayToken(IQueryParam param);

  boolean updatePayToken(PayToken token);

}
