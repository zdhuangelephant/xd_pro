package com.xiaodou.wallet.service.facade;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.wallet.dao.AccountWalletBillDao;
import com.xiaodou.wallet.dao.AccountWalletDao;
import com.xiaodou.wallet.model.AccountWallet;
import com.xiaodou.wallet.model.AccountWalletBill;

/**
 * @name @see com.xiaodou.wallet.service.facade.WalletServiceFacadeImpl.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月2日
 * @description 钱包serviceFacade实现
 * @version 1.0
 */
@Service("walletServiceFacade")
public class WalletServiceFacadeImpl implements WalletServiceFacade {

  @Resource
  AccountWalletDao accountWalletDao;

  @Resource
  AccountWalletBillDao accountWalletBillDao;

  @Override
  public AccountWallet insertAccountWallet(AccountWallet wallet) {
    return accountWalletDao.addEntity(wallet);
  }

  @Override
  public boolean updateAccountWalletById(AccountWallet wallet) {
    return accountWalletDao.updateEntityById(wallet);
  }

  @Override
  public boolean updateAccountWallet(Map<String, Object> input, AccountWallet wallet) {
    return accountWalletDao.updateEntityByCond(input, wallet);
  }

  @Override
  public AccountWallet queryAccountWalletBy3d4Update(AccountWallet wallet) {
    return accountWalletDao.findEntityBy3d4Update(wallet);
  }

  @Override
  public Page<AccountWallet> queryAccountWalletByCond(IQueryParam param) {
    return accountWalletDao.findEntityListByCond(param, null);
  }

  @Override
  public AccountWalletBill insertAccountWalletBill(AccountWalletBill bill) {
    return accountWalletBillDao.addEntity(bill);
  }

  @Override
  public Page<AccountWalletBill> queryAccountWalletBillByCond(IQueryParam param,
      Page<AccountWalletBill> page) {
    return accountWalletBillDao.findEntityListByCond(param, page);
  }

  @Override
  public AccountWallet queryAccountWalletById4Update(AccountWallet wallet) {
    return accountWalletDao.findEntityById4Update(wallet);
  }

  @Override
  public AccountWalletBill queryAccountWalletBillById4Update(AccountWalletBill bill) {
    return accountWalletBillDao.findEntityById4Update(bill);
  }

  @Override
  public AccountWalletBill queryAccountWalletBillByTradeNo4Update(AccountWalletBill bill) {
    return accountWalletBillDao.findEntityByTradeNo4Update(bill);
  }

  @Override
  public boolean updateAccountWalletBillById(AccountWalletBill bill) {
    return accountWalletBillDao.updateEntityById(bill);
  }

}
