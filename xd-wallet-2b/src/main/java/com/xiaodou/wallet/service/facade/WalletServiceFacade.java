package com.xiaodou.wallet.service.facade;

import java.util.Map;

import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.wallet.model.AccountWallet;
import com.xiaodou.wallet.model.AccountWalletBill;

/**
 * @name @see com.xiaodou.wallet.service.facade.WalletServiceFacade.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月2日
 * @description 钱包serviceFacade
 * @version 1.0
 */
public interface WalletServiceFacade {

  /**
   * 插入钱包实体
   */
  AccountWallet insertAccountWallet(AccountWallet wallet);

  /**
   * 根据ID更新钱包实体
   */
  boolean updateAccountWalletById(AccountWallet wallet);

  /**
   * 更新钱包实体
   * 
   * @param input
   * @param wallet
   * @return
   */
  boolean updateAccountWallet(Map<String, Object> input, AccountWallet wallet);

  /**
   * 根据主键查询钱包
   */
  AccountWallet queryAccountWalletById4Update(AccountWallet wallet);

  /**
   * 根据条件查询钱包
   */
  AccountWallet queryAccountWalletBy3d4Update(AccountWallet wallet);
  
  /**
   * 根据条件查询钱包
   */
  Page<AccountWallet> queryAccountWalletByCond(IQueryParam param);

  /**
   * 插入钱包账单
   */
  AccountWalletBill insertAccountWalletBill(AccountWalletBill bill);

  /**
   * 根据ID更新钱包账单
   */
  boolean updateAccountWalletBillById(AccountWalletBill bill);

  /**
   * 根据主键ID查询钱包账单
   */
  AccountWalletBill queryAccountWalletBillById4Update(AccountWalletBill bill);

  /**
   * 根据外部交易号查询钱包账单
   */
  AccountWalletBill queryAccountWalletBillByTradeNo4Update(AccountWalletBill bill);

  /**
   * 根据条件查询钱包账单
   */
  Page<AccountWalletBill> queryAccountWalletBillByCond(IQueryParam param,
      Page<AccountWalletBill> page);

}
