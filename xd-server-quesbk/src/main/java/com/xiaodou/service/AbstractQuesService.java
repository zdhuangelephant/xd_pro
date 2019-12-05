package com.xiaodou.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.constant.QuesBaseConstant;
import com.xiaodou.domain.product.CourseProduct;
import com.xiaodou.domain.product.CourseProductItem;
import com.xiaodou.domain.product.QuesbkExamPaper;
import com.xiaodou.domain.product.QuesbkQues;
import com.xiaodou.manager.facade.QuesOperationFacade;
import com.xiaodou.util.DateUtils;
import com.xiaodou.vo.request.QuesBasePojo;

public class AbstractQuesService {

  @Resource
  protected QuesOperationFacade quesOperationFacade;
  
  @Resource
  QuesChallengeService quesChallengeService;

  @Resource
  QueueService queueService;

  /**
   * 检查课程ID合法性 判断用户是否购买过该产品，并判断课程的有效性
   * 
   * @param pojo 参数pojo
   * @param courseId 课程ID
   * @return
   */
  protected Boolean checkCourseId(QuesBasePojo pojo, String courseId) {
    // 判断用户是否购买过该产品
    Map<String, Object> params = Maps.newHashMap();
    params.put("userId", pojo.getUid());
    params.put("productId", courseId);
    List<CourseProduct> courseList = quesOperationFacade.queryBuyCourseByCond(params);
    if (null == courseList || courseList.size() == 0) return false;
    for (CourseProduct course : courseList) {
      if (null != course && courseId.equals(course.getId().toString())){
          // 判断课程的有效性
          if (null != course.getBeginApplyTime() && null != course.getEndApplyTime()
              && DateUtils.ifIsExp(course.getBeginApplyTime(), course.getEndApplyTime())) {
            if (pojo.getModule().equals(course.getModuleCourse()))
              pojo.setMajorId(QuesBaseConstant.COMMON_MAJOR_ID);
            else
              pojo.setMajorId(course.getCategoryId().toString());
            return true;
          }
        }
    }
    return false;
  }

  /**
   * 检查课程ID合法性 判断用户是否购买过该产品
   * 
   * @param pojo 参数pojo
   * @param courseId 课程ID
   * @return
   */
  protected Boolean checkOrderCourseId(QuesBasePojo pojo, String courseId) {
      return checkOrderCourseIdOverTypeCode(pojo, courseId);
  }
  
  protected Boolean checkOrderCourseIdOverTypeCode(QuesBasePojo pojo, String courseId) {
      // 判断用户是否购买过该产品
      Map<String, Object> params = Maps.newHashMap();
      params.put("userId", pojo.getUid());
      params.put("productId", courseId);
      List<CourseProduct> courseList = quesOperationFacade.selectBuyProductByCond0(params);
      if (null == courseList || courseList.size() == 0) return false;
      for (CourseProduct course : courseList) {
          if (null != course && courseId.equals(course.getId().toString())){
              return true;
          }
      }
      return false;
  }

  /**
   * 找出课程id下面的章id
   * 
   * @param courseId 课程ID
   * @return
   */
  protected List<String> queryChapterIdByCourseId(String courseId) {
    List<CourseProductItem> CourseProductItemList = quesOperationFacade.queryChapterList(courseId);
    List<String> chapterIdList = Lists.newArrayList();
    for (CourseProductItem courseProductItem : CourseProductItemList) {
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

  public CourseProduct queryProduct(String productId) {
    return quesOperationFacade.queryProduct(productId);
  }
}
