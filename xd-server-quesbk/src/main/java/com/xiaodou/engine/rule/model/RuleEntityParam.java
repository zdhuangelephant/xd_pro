package com.xiaodou.engine.rule.model;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.domain.product.QuesbkQuesType;
import com.xiaodou.engine.rule.iterface.IRuleEntityParam;
import com.xiaodou.manager.facade.QuesOperationFacade;
import com.xiaodou.summer.sceduling.concurrent.SummerTaskExecutor;

public class RuleEntityParam implements IRuleEntityParam {
  public RuleEntityParam(QuesOperationFacade service, SummerTaskExecutor excutor,
      List<QuesbkQuesType> quesTypeList, String uid, String courseId, String chapterId,
      String itemId, String priorQues, String lastQuesId) {
    this.uid = uid;
    this.courseId = courseId;
    this.chapterId = chapterId;
    this.itemId = itemId;
    this.priorQues = priorQues;
    this.quesTypeList.addAll(quesTypeList);
    this.service = service;
    this.excutor = excutor;
    this.lastQuesId = lastQuesId;
  }

  private String uid;
  private String courseId;
  private String chapterId;
  private String itemId;
  private String priorQues;
  private String lastQuesId;
  private List<QuesbkQuesType> quesTypeList = Lists.newArrayList();
  private QuesOperationFacade service;
  private SummerTaskExecutor excutor;

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getLastQuesId() {
    return lastQuesId;
  }

  public void setLastQuesId(String lastQuesId) {
    this.lastQuesId = lastQuesId;
  }

  public String getPriorQues() {
    return priorQues;
  }

  public void setPriorQues(String priorQues) {
    this.priorQues = priorQues;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getChapterId() {
    return chapterId;
  }

  public void setChapterId(String chapterId) {
    this.chapterId = chapterId;
  }

  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  public List<QuesbkQuesType> getQuesTypeList() {
    return quesTypeList;
  }

  public void setQuesTypeList(List<QuesbkQuesType> quesTypeList) {
    this.quesTypeList = quesTypeList;
  }

  public QuesOperationFacade getService() {
    return service;
  }

  public void setService(QuesOperationFacade service) {
    this.service = service;
  }

  public SummerTaskExecutor getExcutor() {
    return excutor;
  }

  public void setExcutor(SummerTaskExecutor excutor) {
    this.excutor = excutor;
  }

}
