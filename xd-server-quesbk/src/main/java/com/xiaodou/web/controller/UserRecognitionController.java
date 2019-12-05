package com.xiaodou.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.service.UserRecognitionService;
import com.xiaodou.vo.request.FaceRecognitionPojo;

/**
 * @name UserRecognitionController
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月31日
 * @description 用户识别控制器
 * @version 1.0
 */
@Controller("userRecognitionController")
@RequestMapping("/recognition")
public class UserRecognitionController extends QuesBaseController {

  @Resource
  UserRecognitionService userRecognitionService;

  /**
   * 人脸识别接口
   * 
   */
  @RequestMapping("/face_recognition")
  @ResponseBody
  public String faceRecognition(FaceRecognitionPojo pojo) {
    return userRecognitionService.faceRecognition(pojo).toString0();
  }

}
