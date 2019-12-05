package com.xiaodou.course.service.product;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import javax.annotation.Resource;
import com.xiaodou.summer.dao.pagination.Page;
import org.springframework.stereotype.Service;
import com.xiaodou.course.model.product.ModuleSlideModel;
import com.xiaodou.course.service.facade.ProductServiceFacade;

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
    Map<String, Object> output = new HashMap<>();
    output.put("id", "");
    output.put("description", "");
    output.put("imageUrl", "");
    output.put("linkUrl", "");
    output.put("listOrder", "");
    output.put("createTime", "");
    output.put("moduleId", "");
    Page<ModuleSlideModel> moduleSlideModelPage =
        productServiceFacade.queryModuleSlideListByCondWithOutPage(cond, output);
    return moduleSlideModelPage.getResult();
  }

}
