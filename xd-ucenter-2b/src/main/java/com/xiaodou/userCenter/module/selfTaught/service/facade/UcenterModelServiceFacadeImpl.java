package com.xiaodou.userCenter.module.selfTaught.service.facade;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.userCenter.dao.BlankNoticeDao;
import com.xiaodou.userCenter.dao.ProductCategoryUtilDao;
import com.xiaodou.userCenter.dao.ProductUtilDao;
import com.xiaodou.userCenter.model.BlankNoticeModel;
import com.xiaodou.userCenter.model.ProductCategoryUtilModel;
import com.xiaodou.userCenter.model.ProductUtilModel;

@Service("stUcenterModelServiceFacade")
public class UcenterModelServiceFacadeImpl implements IUcenterModelServiceFacade {

  @Resource(name = "blankNoticeDao")
  private BlankNoticeDao blankNoticeDao;
  @Resource(name = "productCategoryUtilDao")
  private ProductCategoryUtilDao productCategoryUtilDao;
  @Resource(name = "productUtilDao")
  private ProductUtilDao productUtilDao;

  @Override
  public List<BlankNoticeModel> queryBlackNoticeList(Map<String, Object> queryCond,
      Map<String, Object> allInfo) {
    return blankNoticeDao.queryList(queryCond, allInfo);
  }

  @Override
  public List<ProductCategoryUtilModel> queryProductCategoryList(Map<String, Object> queryCond,
      Map<String, Object> allInfo) {
    return productCategoryUtilDao.queryList(queryCond, allInfo);
  }
  
  public List<ProductCategoryUtilModel> listProductCategoryJoinModuleInfo(Map<String, Object> queryCond,Map<String, Object> allInfo){
    IQueryParam param = new QueryParam();
    param.addInputs(queryCond);
    param.addOutputs(allInfo);
    return productCategoryUtilDao.findEntityListByCond(param, null).getResult();
  }

  @Override
  public Page<ProductUtilModel> queryCascadeProductByCond(Map<String, Object> cond,
      Map<String, Object> output) {
    return productUtilDao.cascadeQueryProduct(cond, output);
  }

}
