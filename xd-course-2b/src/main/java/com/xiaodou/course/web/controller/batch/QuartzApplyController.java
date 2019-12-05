package com.xiaodou.course.web.controller.batch;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.xiaodou.course.model.http.ApplyRequest;
import com.xiaodou.course.model.http.ApplyRequest.ApplyRequestDTO;
import com.xiaodou.course.model.http.ApplyResponse;
import com.xiaodou.course.model.http.ApplyResponse.ApplyResponseDTO;
import com.xiaodou.course.service.order.ProductOmsService;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.summer.web.BaseController;

@Controller
@RequestMapping("quartz")
public class QuartzApplyController extends BaseController {

  @Resource
  ProductOmsService productOmsService;

  /**
   * 0.导入课程
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "quartz_apply", method = RequestMethod.POST, headers = {"content-type=application/json;charset=utf-8"})
  @ResponseBody
  public String QuartzApply(@RequestBody ApplyRequest request) {
    ApplyResponse response = new ApplyResponse(ResultType.SUCCESS);
    List<ApplyResponseDTO> arpList = Lists.newArrayList();
    for (ApplyRequestDTO arq : request.getListApplyRequest()) {
      if (null == arq || null == arq.getProductId() || null == arq.getStudentId()
          || null == arq.getUserId()) continue;
      ApplyResponseDTO arp = productOmsService.importProduct(arq);
      arpList.add(arp);
    }
    response.setListApplyResponse(arpList);
    return response.toString();
  }

}
