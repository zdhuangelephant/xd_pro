package com.xiaodou.course.service.product;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.course.dao.product.CourseKeywordResourceDao;
import com.xiaodou.course.model.product.CourseKeywordModel;
import com.xiaodou.course.model.product.CourseKeywordResourceModel;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

@Service("courseKeywordResourceService")
public class CourseKeywordResourceService {
  @Resource
  CourseKeywordResourceDao courseKeywordResourceDao;
  
  
  @SuppressWarnings("unchecked")
  public List<CourseKeywordResourceModel> listKeypointByResourceIdAndResType(List<String> resourceIds, String resourceType) {
    IQueryParam param = new QueryParam();
    param.addInput("resourceIdList", resourceIds);
    param.addInput("resourceType", resourceType);
    param.addOutputs(CommUtil.getGeneralField(CourseKeywordModel.class));
    Page<CourseKeywordResourceModel> page = courseKeywordResourceDao.findEntityListByCond(param, null);
    return (null == page || page.getResult().size() == 0) ? Collections.EMPTY_LIST : page.getResult();
  }
}
