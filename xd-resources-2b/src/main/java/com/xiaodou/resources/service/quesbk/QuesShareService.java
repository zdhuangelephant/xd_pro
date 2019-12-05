package com.xiaodou.resources.service.quesbk;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.resources.request.quesbk.QuesSharePojo;
import com.xiaodou.share.response.ShareResponse;
import com.xiaodou.share.service.AbstractShareService;

/**
 * @name @see com.xiaodou.service.QuesShareService.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年9月7日
 * @description 题库分享service
 * @version 1.0
 */
@Service("quesShareService")
public class QuesShareService extends AbstractShareService<QuesSharePojo> {
  @Resource
  QuesBaseServices quesBaseServices;

  @Override
  public ShareResponse processResponse(QuesSharePojo pojo, ShareResponse response) {

    String shareType = pojo.getShareType();
    if (("3").equals(shareType)) {
      String content = String.format(response.getContent(), pojo.getScore());
      response.setContent(content);
      // 做题详情展示接口 -> url
      String url = String.format(response.getUrl(), pojo.getPaperId(), pojo.getCourseId(),pojo.getScore(),shareType);
      response.setUrl(url);
    }
    return response;
  }

//  public ExamDetailResponse getExamDetail(ExamDetailPojo pojo) {
//    UserTokenWrapper.getWrapper().setUserModel(new BaseUserModel() {
//      @Override
//      public String getId() {
//        return "-1";
//      }
//
//      @Override
//      protected void initModuleInfo(String modelInfo) {
//      }
//    });
//    List<Question> questionList = new ArrayList<Question>();
//    List<Question> questions = null;
//    ExamDetailResponse examDetailResponse = null;
//    try {
//      examDetailResponse = quesBaseServices.examDetail0(pojo);
//      if (null != examDetailResponse.getExamDetail()
//          && null != examDetailResponse.getExamDetail().getQuestionList()) {
//        questions = examDetailResponse.getExamDetail().getQuestionList();
//        if (null != questions && questions.size() > 0) for (Question question : questions) {
//          if (("1").equals(question.getQuesType())) {
//            questionList.add(question);
//          }
//          if (questionList.size() >= 5) break;
//        }
//        examDetailResponse.getExamDetail().setQuestionList(questionList);
//      }
//    } catch (Exception e) {
//      LoggerUtil.error("查题失败 : " + FastJsonUtil.toJson(pojo), e);
//    }
//    return examDetailResponse;
//  }

}
