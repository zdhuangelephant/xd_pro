package com.xiaodou.server.mapi.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.service.selftaught.SelfTaughtService;
import com.xiaodou.summer.vo.out.ResultType;

@Controller("toolController")
@RequestMapping("tool")
public class ToolController extends BaseMapiController {

  @Resource
  SelfTaughtService selfTaughtService;

  BaseResponse baseResponse = new BaseResponse(ResultType.SUCCESS);

  /**
   * 导入用户接口。 · url /selftaught/official_user_info
   * 
   * @param pojo
   */
  // @ResponseBody
  // @RequestMapping("official_user_info")
  // public String officialUserInfo(MapiBaseRequest pojo) {
  // BaseResponse response = new BaseResponse(ResultType.SUCCESS);
  // String message = StringUtils.EMPTY;
  // jxlUtils.createExcel();
  // List<OfficialInfo> lineList = jxlUtils.readExcelToOfficial();
  // if (null == lineList || lineList.size() < 1)
  // return (new BaseResponse(ResultType.VALFAIL)).toString();
  // for (OfficialInfo officialInfo : lineList) {
  // if (selfTaughtService.verifyOfficialInfo(officialInfo)) {
  // // 批量操作
  // // 1、 导入用户
  // ImportMemberResponse importResponse =
  // selfTaughtService.importMember(selfTaughtService.getStOfficialInfoRegist(officialInfo));
  // if (!importResponse.isRetOk() && !importResponse.getRetcode().equals("20310")) {
  // // PhoneNumError("20204", "手机号无效");
  // // HasRegisterd("20310", "您已经注册过，请直接登录");
  // if (importResponse.getRetcode().equals("20310")) {} else if (importResponse.getRetcode()
  // .equals("20204")) {
  // message += "手机号无效";
  // } else {
  // message += "导入失败用户手机号，phoneNum:" + officialInfo.getPhoneNum() + ",";
  // }
  // } else if (importResponse.isRetOk() || importResponse.getRetcode().equals("20310")) {
  // // 2、将导入的专业课程(根据导入数据中提示要开的课程)都质为已购买状态
  // Map<String, Object> params = Maps.newHashMap();
  // params.put("uid", importResponse.getUserId());
  // params.put("module", "2");
  // params.put("typeCode", officialInfo.getMajorCode());
  // params.put("courseCode", officialInfo.getCourseCode());
  // params.put("orderStatus", SelfTaughtConstant.ORDERSTATUS_NORMAL);
  // response = selfTaughtService.openProduct(params);
  // if (!response.isRetOk()) {
  // // response.setMessage("majorCode:" + officialInfo.getMajorCode() + "courseCode:"
  // // + officialInfo.getCourseCode());
  // message +=
  // officialInfo.getPhoneNum() + "开通课程失败：majorCode:" + officialInfo.getMajorCode()
  // + "courseCode:" + officialInfo.getCourseCode() + ",";
  // }
  // }
  //
  // }
  // }
  // response.setMessage(message);
  // return response.toString();
  // }
}
