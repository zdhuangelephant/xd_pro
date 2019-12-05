package com.xiaodou.wallet.dao;

import org.springframework.stereotype.Repository;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.summer.dao.BaseDao;
import com.xiaodou.wallet.model.AccountWallet;

/**
 * @name @see com.xiaodou.wallet.dao.AccountWalletDao.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月2日
 * @description 账户钱包Dao
 * @version 1.0
 */
@Repository("accountWalletDao")
public class AccountWalletDao extends BaseDao<AccountWallet> {

  public AccountWallet findEntityBy3d4Update(AccountWallet wallet) {
    if (StringUtils.isOrBlank(wallet.getUserId(), wallet.getAccountClassify())
        || null == wallet.getBusinessType())
      throw new IllegalArgumentException(
          "AccountWalletDao.findEntityBy3d4Update#用户ID/业务类型/账户分类不能为空");
    return (AccountWallet) getSqlSession().selectOne("AccountWallet.findEntityBy3d4Update", wallet);
  }

  public AccountWallet findEntityById4Update(AccountWallet wallet) {
    if (null == wallet.getId())
      throw new IllegalArgumentException("AccountWalletDao.findEntityById4Update#主键ID不能为空");
    return (AccountWallet) getSqlSession().selectOne("AccountWallet.findEntityById4Update", wallet);
  }

}
