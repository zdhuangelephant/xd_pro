package com.xiaodou.course.service.product;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.course.dao.product.CourseKeywordDao;
import com.xiaodou.course.model.product.CourseKeywordModel;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

@Service("courseKeywordService")
public class CourseKeywordService {
  @Resource
  CourseKeywordDao courseKeywordDao;

  @SuppressWarnings("unchecked")
  public List<CourseKeywordModel> listKeypoint(List<Integer> keypoints) {
    if(CollectionUtils.isEmpty(keypoints)) return Collections.EMPTY_LIST;
    IQueryParam param = new QueryParam();
    param.addInput("idList", keypoints);
    param.addOutputs(CommUtil.getGeneralField(CourseKeywordModel.class));
    Page<CourseKeywordModel> page = courseKeywordDao.findEntityListByCond(param, null);
    return (null == page || page.getResult().size() == 0) ? Collections.EMPTY_LIST : page.getResult();

  }

  public CourseKeywordModel getCourseKeywordById(Integer keywordId) {
    CourseKeywordModel entity = new CourseKeywordModel();
    entity.setId(keywordId.longValue());
    return courseKeywordDao.findEntityById(entity);
  }


  
}
