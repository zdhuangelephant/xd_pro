package com.xiaodou.webfetch.web.service;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.webfetch.web.constants.Platform;
import com.xiaodou.webfetch.web.dao.ForumAccountDao;
import com.xiaodou.webfetch.web.domain.ForumAccount;

@Service("forumAccountService")
public class ForumAccountService {
  @Resource
  ForumAccountDao forumAccountDao;

  public Boolean save(String signature) {
      ForumAccount account = new ForumAccount();
      account.setCreateTime(new Timestamp(System.currentTimeMillis()));
      account.setPlatform(Short.parseShort(Platform.COLUMN_ZHIHU.getCode().toString()));
      account.setSignature(signature);
      forumAccountDao.addEntity(account);
      return true;
  }

  public Page<ForumAccount> findEntityListByCond(String signature,String platform,Short state){
    IQueryParam param = new QueryParam();
    if(org.apache.commons.lang.StringUtils.isNotBlank(signature)) {
      param.addInput("signature", signature);
    }
    if(org.apache.commons.lang.StringUtils.isNotBlank(platform)) {
      param.addInput("platform", platform);
    }
    param.addInput("state", state);
    param.addOutputs(CommUtil.getAllField(ForumAccount.class));
    Page<ForumAccount> accounts = forumAccountDao.findEntityListByCond(param, null);
    return accounts;
   }

 

  public Boolean updateByEntityById(ForumAccount fa) {
    return forumAccountDao.updateEntityById(fa);
  }
  
}
