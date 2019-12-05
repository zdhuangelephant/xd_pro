package com.xiaodou.ms.service.resources;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.ms.dao.knowledge.ForumAccountDao;
import com.xiaodou.ms.model.knowledge.ForumAccount;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

@Service("forumAccountService")
public class ForumAccountService {
  @Resource
  ForumAccountDao forumAccountDao;

  public Page<ForumAccount> findEntityByCond(String signature, String platformType) {
    IQueryParam param = new QueryParam();
    param.addInput("signature", signature);
    param.addInput("platform", platformType);
    param.addOutputs(CommUtil.getAllField(ForumAccount.class));
    return forumAccountDao.findEntityListByCond(param, null);
  }
  
  
}
