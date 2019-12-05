package com.xiaodou.wallet.dao;

import org.springframework.stereotype.Repository;

import com.xiaodou.summer.dao.BaseDao;
import com.xiaodou.wallet.model.AccountWalletBill;

/**
 * @name @see com.xiaodou.wallet.dao.AccountWalletBillDao.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月2日
 * @description 账户钱包账单Dao
 * @version 1.0
 */
@Repository("accountWalletBillDao")
public class AccountWalletBillDao extends BaseDao<AccountWalletBill> {

  public AccountWalletBill findEntityById4Update(AccountWalletBill bill) {
    if (null == bill.getId())
      throw new IllegalArgumentException("AccountWalletBillDao.findEntityById4Update#主键ID不能为空");
    return (AccountWalletBill) getSqlSession().selectOne("AccountWalletBill.findEntityById4Update",
        bill);
  }

  public AccountWalletBill findEntityByTradeNo4Update(AccountWalletBill bill) {
    if (null == bill.getTradeNo())
      throw new IllegalArgumentException(
          "AccountWalletBillDao.findEntityByTradeNo4Update#外部交易号不能为空");
    return (AccountWalletBill) getSqlSession().selectOne(
        "AccountWalletBill.findEntityByTradeNo4Update", bill);
  }

}
