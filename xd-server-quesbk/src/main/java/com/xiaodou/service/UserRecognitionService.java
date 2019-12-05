package com.xiaodou.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.constant.QuesBaseConstant;
import com.xiaodou.constant.ResultType;
import com.xiaodou.domain.product.QuesbkExamPaper;
import com.xiaodou.enums.ExamType;
import com.xiaodou.facerecognition.FaceCompareResponse;
import com.xiaodou.facerecognition.FaceIdResponse;
import com.xiaodou.facerecognition.IFaceRecognitionApi;
import com.xiaodou.facerecognition.RetCode;
import com.xiaodou.vo.request.FaceRecognitionPojo;
import com.xiaodou.vo.response.FaceRecognitionResponse;

/**
 * @name UserRecognitionService
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月30日
 * @description 用户识别Service
 * @version 1.0
 */
@Service("userRecognitionService")
public class UserRecognitionService extends AbstractQuesService {

  @Resource
  IFaceRecognitionApi faceRecognitionApi;

  /**
   * 面部识别接口
   * 
   * @param pojo 面部识别参数类
   * @return
   */
  public FaceRecognitionResponse faceRecognition(FaceRecognitionPojo pojo) {
    // 判断是否上传基准头像
    if (StringUtils.isBlank(pojo.getSrcFaceId()))
      return new FaceRecognitionResponse(ResultType.MissSrcFace);
    // 判断是否已经采集过头像信息,缺失返回00700状态码
    if (StringUtils.isBlank(pojo.getSrcFaceId()))
      return new FaceRecognitionResponse(ResultType.MissSrcFace);
    // 1_校验课程的有效性
    if (!checkCourseId(pojo, pojo.getCourseId()))
      return new FaceRecognitionResponse(ResultType.UNVALIDCOURSEID);
    QuesbkExamPaper paper = null;
    if (QuesBaseConstant.FACE_RECOGNITION_ALARM.equals(pojo.getType())) {
      // 2_校验记录的有效性 记录是否存在
      paper = quesOperationFacade.queryExamPaper(pojo.getPaperId());
      if (null == paper) return new FaceRecognitionResponse(ResultType.NOPAPER);
      // 3_校验记录的有效性 课程、练习类型是否匹配
      if (paper.getCourseId() == null
          || !pojo.getCourseId().equals(paper.getCourseId().toString())
          || paper.getExamTypeId() == null
          || (!ExamType.CHAPTER_BREAKTHROUGH_PAPER.getCode().equals(
              paper.getExamTypeId().toString())
              && !ExamType.ITEM_BREAKTHROUGH_PAPER.getCode().equals(
                  paper.getExamTypeId().toString()) && !ExamType.FINAL_EXAM_PAPER.getCode().equals(
              paper.getExamTypeId().toString())))
        return new FaceRecognitionResponse(ResultType.PAPERWRONGMES);
    }
    FaceRecognitionResponse response = new FaceRecognitionResponse(ResultType.SUCCESS);
    FaceIdResponse face = faceRecognitionApi.addFace(pojo.getFaceUrl());
    if (!RetCode.Success.getCode().equals(face.getRetCode())) {
      response = new FaceRecognitionResponse(ResultType.SUCCESS);
      response.setRetdesc(face.getRetDesc());
      response.setIsSelf(QuesBaseConstant.NO);
      return response;
    }
    FaceCompareResponse compareRes =
        faceRecognitionApi.compare(pojo.getSrcFaceId(), face.getFaceId());
    if (!RetCode.Success.getCode().equals(compareRes.getRetCode())) {
      response = new FaceRecognitionResponse(ResultType.SUCCESS);
      response.setRetdesc(face.getRetDesc());
      response.setIsSelf(QuesBaseConstant.NO);
      // if (QuesBaseConstant.FACE_RECOGNITION_ALARM.equals(pojo.getType()))
      // sendCompareEvent(pojo, paper, 0d, QuesBaseConstant.NO);
      return response;
    }
    if (compareRes.getSimilarity() != null && compareRes.getSimilarity() >= 75d)
      response.setIsSelf(QuesBaseConstant.YES);
    else
      response.setIsSelf(QuesBaseConstant.NO);
    // if (QuesBaseConstant.FACE_RECOGNITION_ALARM.equals(pojo.getType()))
    // sendCompareEvent(pojo, paper, compareRes.getSimilarity(), response.getIsSelf());
    return response;
  }

  /**
   * 发送人脸识别数据清洗事件
   * 
   * @param paper
   * @param isSelf
   * 
   */
  // private void sendCompareEvent(FaceRecognitionPojo pojo, QuesbkExamPaper paper, Double
  // similarity,
  // String isSelf) {
  // FaceRecognitionEvent event = new FaceRecognitionEvent();
  // event.setModule(pojo.getModule());
  // FaceCompareResult model = new FaceCompareResult();
  // model.setUserId(pojo.getUid());
  // model.setDeviceId(pojo.getDeviceId());
  // model.setModule(pojo.getModule());
  // model.setPaperId(paper.getId());
  // model.setTypeCode(pojo.getTypeCode());
  // model.setProductId(paper.getCourseId().toString());
  // if (ExamType.FINAL_EXAM_PAPER.getCode().equals(paper.getExamTypeId().toString())) {
  // model.setChapterName(paper.getExamName());
  // } else {
  // CourseProductItem chapterInfo =
  // quesOperationFacade.queryChapter(paper.getCourseId().toString(), paper.getChapterId()
  // .toString());
  // if (null != chapterInfo) model.setChapterName(chapterInfo.getChapterId());
  // }
  // model.setFaceUrl(pojo.getFaceUrl());
  // model.setSimilarity(similarity);
  // model.setIsSelf(isSelf);
  // model.setRecordTime(new Timestamp(System.currentTimeMillis()));
  // event.setDataModel(model);
  // event.send();
  // }
}
