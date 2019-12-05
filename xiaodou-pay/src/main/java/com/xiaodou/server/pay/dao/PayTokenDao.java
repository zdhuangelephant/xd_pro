package com.xiaodou.server.pay.dao;

import org.springframework.stereotype.Repository;

import com.xiaodou.server.pay.model.PayToken;
import com.xiaodou.summer.dao.BaseDao;

/**
 * @name @see com.xiaodou.server.pay.dao.PayTokenDao.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年5月27日
 * @description 支付Token Dao
 * @version 1.0
 */
@Repository("payTokenDao")
public class PayTokenDao extends BaseDao<PayToken> {}
