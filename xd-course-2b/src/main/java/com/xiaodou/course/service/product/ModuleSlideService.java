package com.xiaodou.course.service.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.course.model.product.ModuleSlideModel;
import com.xiaodou.course.service.facade.ProductServiceFacade;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * Created by zyp on 15/8/16.
 */
@Service("moduleSlideService")
public class ModuleSlideService {

  @Resource
  ProductServiceFacade productServiceFacade;

  public List<ModuleSlideModel> moduleSlideList(Integer moduleId) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("moduleId", moduleId);
    Page<ModuleSlideModel> moduleSlideModelPage =
        productServiceFacade.queryModuleSlideListByCondWithOutPage(cond, 
          CommUtil.getAllField(ModuleSlideModel.class));
    return moduleSlideModelPage.getResult();
  }

}
