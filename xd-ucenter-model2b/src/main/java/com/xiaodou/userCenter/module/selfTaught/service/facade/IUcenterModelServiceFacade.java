package com.xiaodou.userCenter.module.selfTaught.service.facade;

import java.util.List;
import java.util.Map;

import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.userCenter.model.BlankNoticeModel;
import com.xiaodou.userCenter.model.ProductCategoryUtilModel;
import com.xiaodou.userCenter.model.ProductUtilModel;

public interface IUcenterModelServiceFacade {
  List<BlankNoticeModel> queryBlackNoticeList(Map<String, Object> queryCond,
      Map<String, Object> allInfo);

  List<ProductCategoryUtilModel> queryProductCategoryList(Map<String, Object> queryCond,
      Map<String, Object> allInfo);

  Page<ProductUtilModel> queryCascadeProductByCond(Map<String, Object> cond,
      Map<String, Object> output);

}
