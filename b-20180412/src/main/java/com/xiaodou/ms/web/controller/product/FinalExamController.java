package com.xiaodou.ms.web.controller.product;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.xiaodou.ms.model.product.FinalExamModel;
import com.xiaodou.ms.service.product.FinalExamService;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.request.product.FinalExamRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * Created by zdh on 17/7/19.
 */

@Controller("finalExamController")
@RequestMapping("/finalExam")
public class FinalExamController extends BaseController {
  @Resource
  FinalExamService finalExamService;

  /**
   * 课程列表
   */
  @RequestMapping("/list")
  public ModelAndView examList(Long productId) {
    ModelAndView modelAndView = new ModelAndView("product/finalExamList");
    Page<FinalExamModel> examList = finalExamService.queryAllRecord(productId);
    if (productId != null) {
      modelAndView.addObject("productId", productId);
    }
    if (examList != null) {
      modelAndView.addObject("examList", examList.getResult());
    }
    return modelAndView;
  }

  /**
   * 添加活动
   * 
   * @return
   */
  @RequestMapping("/add")
  public ModelAndView finalExamAdd(Integer productId, Integer sort) {
    ModelAndView modelAndView = new ModelAndView("product/finalExamAdd");
    if (productId != null) {
      modelAndView.addObject("productId", productId);
    }
    modelAndView.addObject("sort", sort);
    return modelAndView;
  }

  @RequestMapping("/doAdd")
  public ModelAndView finalExamDoAdd(FinalExamRequest request) {
    try {
      BaseResponse response = null;
      Errors errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        response = finalExamService.doAdd(request);
      }
      if (response.getRetCode() == 0) {
        return this.showMessage("添加成功", "", null, true);
      } else {
        return this.showMessage("添加失败", response.getRetDesc(), null, true);
      }
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * 修改活动
   */
  @RequestMapping("/edit")
  public ModelAndView finalExamEdit(Long id) {
    ModelAndView modelAndView = new ModelAndView("product/finalExamEdit");
    FinalExamModel finalExam = finalExamService.findFinalExamById(id);

    modelAndView.addObject("finalExam", finalExam);
    return modelAndView;
  }

  @RequestMapping("/doEdit")
  public ModelAndView finalExamDoEdit(FinalExamRequest request) {
    try {
      BaseResponse response = null;
      Errors errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        response = finalExamService.doEdit(request);
      }
      if (response.getRetCode() == 0) {
        return this.showMessage("编辑成功", "", "", true);
      } else {
        return this.showMessage("编辑失败", response.getRetDesc(),
            "/finalExam/edit?id=" + request.getId(), true);
      }
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * 刪除期末测试
   * 
   * @param id
   * @return
   */
  @RequestMapping("/delete")
  @ResponseBody
  public String finalExamDelete(Long id) {
    try {
      BaseResponse response = null;
      Boolean aBoolean = finalExamService.delete(id);
      if (aBoolean) {
        response = new BaseResponse(ResultType.SUCCESS);
      } else {
        response = new BaseResponse(ResultType.SYS_FAIL);
      }
      return JSON.toJSONString(response);
    } catch (Exception e) {
      throw e;
    }
  }


  /**
   * 排序
   * 
   * @param orders
   * @return
   */
  @RequestMapping("/order")
  @ResponseBody
  public String order(String orders) {
    String[] orderItems = orders.split(";");
    for (String orderItem : orderItems) {
      String[] split = orderItem.split(":");
      FinalExamModel finalExam = new FinalExamModel();
      finalExam.setId(Long.parseLong(split[0]));
      finalExam.setSort(Integer.parseInt(split[1]));
      finalExamService.editFinalExam(finalExam);
    }
    return "true";
  }
}
