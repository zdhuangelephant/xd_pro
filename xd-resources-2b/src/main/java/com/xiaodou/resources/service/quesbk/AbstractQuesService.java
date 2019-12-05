package com.xiaodou.resources.service.quesbk;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.resources.model.product.ProductItemModel;
import com.xiaodou.resources.model.product.ProductModel;
import com.xiaodou.resources.model.quesbk.QuesbkExamPaper;
import com.xiaodou.resources.model.quesbk.QuesbkQues;
import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.resources.service.QueueService;
import com.xiaodou.resources.service.quesbk.facade.QuesOperationFacade;

public class AbstractQuesService {

  @Resource
  protected QuesOperationFacade quesOperationFacade;

  @Resource
  QueueService queueService;

  /**
   * 检查课程ID合法性 判断用户是否购买过该产品
   * 
   * @param pojo 参数pojo
   * @param courseId 课程ID
   * @return
   */
  protected Boolean checkCourseId(BaseRequest pojo, String courseId) {
    // 判断用户是否购买过该产品
    Map<String, Object> params = Maps.newHashMap();
    params.put("module", pojo.getModule());
    params.put("userId", pojo.getUid());
    if (StringUtils.isNotBlank(courseId)) params.put("id", courseId);
    List<ProductModel> course = quesOperationFacade.queryBuyCourseByCond(params);
    if (null == course || course.size() == 0) return false;
    return true;
  }

  /**
   * 找出课程id下面的章id
   * 
   * @param courseId 课程ID
   * @return
   */
  protected List<String> queryChapterIdByCourseId(String courseId) {
    List<ProductItemModel> CourseProductItemList = quesOperationFacade.queryChapterList(courseId);
    List<String> chapterIdList = Lists.newArrayList();
    for (ProductItemModel courseProductItem : CourseProductItemList) {
      chapterIdList.add(courseProductItem.getId().toString());
    }
    return chapterIdList;
  }

  /**
   * 设置试卷问题列表
   * 
   * @param paper
   * @param quesList
   */
  protected void setQuesIdLst(QuesbkExamPaper paper, List<Object> quesList) {
    if (quesList != null && quesList.size() > 0) {
      List<Long> quesIdLst = Lists.newArrayList();
      Integer quesCount = 0;
      for (Object ques : quesList) {
        if (ques instanceof QuesbkQues) {
          quesIdLst.add(((QuesbkQues) ques).getId());
          quesCount++;
        }
      }
      paper.setQuesNum(quesCount);
      paper.setQuesIds(FastJsonUtil.toJson(quesIdLst));
      return;
    }
    paper.setQuesIds(StringUtils.EMPTY);
  }

}
