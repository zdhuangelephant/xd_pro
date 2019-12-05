package com.xiaodou.st.dashboard.service.product;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.st.dashboard.constants.Constants;
import com.xiaodou.st.dashboard.domain.product.RawDataProductCategoryDO;
import com.xiaodou.st.dashboard.domain.product.RawDataProductDO;
import com.xiaodou.st.dashboard.domain.staticinfo.StaticInfoDO;
import com.xiaodou.st.dashboard.service.facade.IStServiceFacade;
import com.xiaodou.st.dashboard.service.manage.ManageStaticInfoService;
import com.xiaodou.summer.dao.pagination.Page;

@Service
public class ProductService {

  @Resource
  IStServiceFacade stServiceFacade;
  @Resource
  ManageStaticInfoService manageStaticInfoService;

  /**
   * 查询专业列表
   * @return
   */
  public List<RawDataProductCategoryDO> listCategory() {
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("showStatus", Constants.SHOW_STATUS_YES);
    Page<RawDataProductCategoryDO> page =
        stServiceFacade.listRawDataProductCategory(inputs,
            CommUtil.getAllField(RawDataProductCategoryDO.class));
    if (null == page) return null;
    return page.getResult();
  }
  
  /**
   * 
   * @description 根据专业查询课程
   * @author 李德洪
   * @Date 2017年4月4日
   * @param catId
   * @return
   */
  public List<RawDataProductDO> listProduct(Integer catId) {
    if(null == catId) return null;
    Map<String, Object> inputs = Maps.newHashMap();
    inputs.put("productCategoryId", catId);
    StaticInfoDO staticInfo = manageStaticInfoService.staticInfo();
    inputs.put("examDate", staticInfo.getExamDate());
    inputs.put("showStatus", Constants.SHOW_STATUS_YES);
    Page<RawDataProductDO> page =
        stServiceFacade.listRawDataProduct(inputs, CommUtil.getAllField(RawDataProductDO.class));
    if (null == page) return null;
    return page.getResult();
  }
  
  /**
   * 
   * @description 考期列表
   * @author 李德洪
   * @Date 2017年4月5日
   * @return
   */
  public List<RawDataProductDO> listExamDate(){
    Page<RawDataProductDO> page = stServiceFacade.findExamDate();
    if(null == page) return null;
    return page.getResult();
  }
  
  public RawDataProductCategoryDO getRawDataProductCategory(Integer catId){
    return stServiceFacade.getRawDataProductCategory(catId);
  }
  
}
