package com.xiaodou.userCenter.web.controller.batch;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.summer.web.BaseController;
import com.xiaodou.userCenter.constant.UcenterModelConstant;
import com.xiaodou.userCenter.model.http.AdmissionCardCodeRequest;
import com.xiaodou.userCenter.model.http.AdmissionCardCodeRequest.AdmissionCardCodeRequestDTO;
import com.xiaodou.userCenter.model.http.AdmissionCardCodeResponse;
import com.xiaodou.userCenter.model.http.AdmissionCardCodeResponse.AdmissionCardCodeResponseDTO;
import com.xiaodou.userCenter.model.http.StudentRequest;
import com.xiaodou.userCenter.model.http.StudentRequest.StudentRequestDTO;
import com.xiaodou.userCenter.model.http.StudentResponse;
import com.xiaodou.userCenter.model.http.StudentResponse.StudentResponseDTO;
import com.xiaodou.userCenter.module.selfTaught.request.StOfficialInfoRegist;
import com.xiaodou.userCenter.response.BaseResultInfo;
import com.xiaodou.userCenter.response.ImportMemberResponse;
import com.xiaodou.userCenter.response.resultype.UcenterResType;
import com.xiaodou.userCenter.service.UcenterService;

@Controller("importStudentController")
@RequestMapping("/quartz")
public class ImportStudentController extends BaseController {

  @Resource
  UcenterService ucenterService;

  /**
   * 0.导入用户
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/quartz_student", method = RequestMethod.POST, headers = {"content-type=application/json;charset=utf-8"})
  @ResponseBody
  public String QuartzStudent(@RequestBody StudentRequest request) {
    StudentResponse response = new StudentResponse(ResultType.SUCCESS);
    List<StudentResponseDTO> srpList = Lists.newArrayList();
    for (StudentRequestDTO sqd : request.getListStudentRequest()) {
      StOfficialInfoRegist req = new StOfficialInfoRegist();
      req.setAdmissionCardCode(sqd.getAdmissionCardCode());
      req.setRealName(sqd.getRealName());
      req.setOfficialGender(sqd.getGender());
      req.setIdentificationCardCode(sqd.getIdentificationCardCode());
      req.setMerchant(sqd.getPilotUnitName());
      req.setPhoneNum(sqd.getTelephone());
      req.setPassword(sqd.getTelephone().substring(5));
      req.setGender(Integer.valueOf(sqd.getGender()));
      req.setMajor(sqd.getTypeCode());
      req.setOfficialStatus(UcenterModelConstant.OFFICIALINFO_IMPORT);
      req.setRegion(sqd.getRegionId());
      req.setRegionName(sqd.getRegionName());
      ImportMemberResponse rep = ucenterService.importMember(req);
      StudentResponseDTO srp = new StudentResponseDTO();
      srp.setStudentId(sqd.getStudentId());
      srp.setTelephone(sqd.getTelephone());
      if (rep.getRetcode().equals(ResultType.SUCCESS.getCode())) {
        srp.setStudentStatus(UcenterModelConstant.SUCCESS_REGISTER);
        srp.setUserId(rep.getUserId());
      } else if (rep.getRetcode().equals(UcenterResType.HasRegisterd.getCode())) {
        srp.setStudentStatus(UcenterModelConstant.FAIL_REGISTER);
      } else {
        srp.setStudentStatus(UcenterModelConstant.ERROR_REGISTER);
      }
      srpList.add(srp);
    }
    response.setListStudentResponse(srpList);
    return response.toString0();
  }

  /**
   * 0.同步准考证号
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/quartz_admissionCardCode", method = RequestMethod.POST, headers = {"content-type=application/json;charset=utf-8"})
  @ResponseBody
  public String quartzAdmissionCardCode(@RequestBody AdmissionCardCodeRequest request) {
    AdmissionCardCodeResponse response = new AdmissionCardCodeResponse(ResultType.SUCCESS);
    if (null == request || null == request.getListAdmissionCardCodeRequestDTO())
      return new AdmissionCardCodeResponse(ResultType.SYSFAIL).toString0();
    List<AdmissionCardCodeResponseDTO> accrdtoList = Lists.newArrayList();
    for (AdmissionCardCodeRequestDTO sqd : request.getListAdmissionCardCodeRequestDTO()) {
      // if(null == sqd || StringUtils.isBlank(sqd.getAdmissionCardCode())) break;
      if (null == sqd) break;
      BaseResultInfo rep = ucenterService.modifyAdmissionCardCode(sqd);
      AdmissionCardCodeResponseDTO accrdto = new AdmissionCardCodeResponseDTO();
      accrdto.setStudentId(sqd.getStudentId());
      accrdto.setTelephone(sqd.getTelephone());
      accrdto.setUserId(sqd.getUserId());
      if (rep.getRetcode().equals(ResultType.SUCCESS.getCode())) {
        accrdto.setStudentStatus(UcenterModelConstant.SUCCESS_IMPORT);
      } else {
        accrdto.setStudentStatus(UcenterModelConstant.SUCCESS_REGISTER);
      }
      accrdtoList.add(accrdto);
    }
    response.setListAdmissionCardCodeResponse(accrdtoList);
    return response.toString0();
  }

}
