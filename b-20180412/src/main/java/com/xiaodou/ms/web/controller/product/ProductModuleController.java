//package com.xiaodou.ms.web.controller.product;
//
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.Errors;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.alibaba.fastjson.JSON;
//import com.xiaodou.common.util.log.LoggerUtil;
//import com.xiaodou.ms.model.product.ProductModel;
//import com.xiaodou.ms.model.product.ProductModuleModel;
//import com.xiaodou.ms.service.product.ProductService;
//import com.xiaodou.ms.web.controller.BaseController;
//import com.xiaodou.ms.web.request.product.ProductModuleCreateRequest;
//import com.xiaodou.ms.web.request.product.ProductModuleEditRequest;
//import com.xiaodou.ms.web.response.BaseResponse;
//import com.xiaodou.ms.web.response.ResultType;
//import com.xiaodou.summer.dao.pagination.Page;
//
///**
// * Created by zyp on 15/6/25.
// */
//@Controller("productModuleController")
//@RequestMapping("/productModule")
//public class ProductModuleController extends BaseController {
////
////  @Resource
////  ProductModuleService productModuleService;
//  
//  
//  @Resource
//  ProductService productService;
//
//  /**
//   * 资源目录
//   * 
//   * @return
//   */
//  @RequestMapping("/list")
//  public ModelAndView resourceModule() {
//    try {
//      ModelAndView modelAndView = new ModelAndView("/product/moduleList");
//      //List<ProductModuleModel> productModuleList = productModuleService.queryAllModule();
//      Page<ProductModel> courseSubjectList = productService.queryCourseByCode(null, null, "","1");
//      List<ProductModel> productList=courseSubjectList.getResult();
//      for(ProductModuleModel p:productModuleList){
//    	  for(ProductModel pt:productList){
//    		  if(p.getId().toString().equals(pt.getModuleCourse())){
//    			  p.setHasBeginnerCourse(pt.getModuleCourse());
//    			  p.setCourseId(pt.getId().toString());
//    		  }
//    	  }
//      }
//      modelAndView.addObject("productModuleList", productModuleList);
//      return modelAndView;
//    } catch (Exception e) {
//      LoggerUtil.error("列表异常", e);
//      throw e;
//    }
//  }
//
//  /**
//   * 目录添加页面
//   * 
//   * @return
//   */
//  @RequestMapping("/add")
//  public ModelAndView resourceModuleAdd() {
//    ModelAndView modelAndView = new ModelAndView("/product/moduleAdd");
//    return modelAndView;
//  }
//
//  /**
//   * 资源目录添加
//   * 
//   * @return
//   */
//  @RequestMapping("/doAdd")
//  public ModelAndView resourceModuleDoAdd(ProductModuleCreateRequest request, Errors errors)
//      throws Exception {
//    try {
//      BaseResponse response = null;
//      errors = request.validate();
//      if (errors.hasErrors()) {
//        response = new BaseResponse(ResultType.VALID_FAIL);
//        response.setRetDesc(this.getErrMsg(errors));
//      } else {
//        response = productModuleService.addModule(request);
//      }
//      if (response.getRetCode() == 0) {
//        return this.showMessage("添加成功", "", null, true);
//      } else {
//        return this.showMessage("添加失败", response.getRetDesc(), null, true);
//      }
//    } catch (Exception e) {
//      LoggerUtil.error("目录创建异常", e);
//      throw e;
//    }
//  }
//
//  /**
//   * 目录编辑
//   * 
//   * @param id
//   * @return
//   */
//  @RequestMapping("/edit")
//  public ModelAndView resourceModuleEdit(Long id) {
//    ModelAndView modelAndView = new ModelAndView("/product/moduleEdit");
//    ProductModuleModel productModule = productModuleService.findModuleById(id);
//    modelAndView.addObject("productModule", productModule);
//    return modelAndView;
//  }
//
//  /**
//   * 资源目录修改
//   * 
//   * @return
//   */
//  @RequestMapping("/doEdit")
//  public ModelAndView resourceModuleDoEdit(ProductModuleEditRequest request, Errors errors)
//      throws Exception {
//    try {
//      BaseResponse response = null;
//      errors = request.validate();
//      if (errors.hasErrors()) {
//        response = new BaseResponse(ResultType.VALID_FAIL);
//        response.setRetDesc(this.getErrMsg(errors));
//      } else {
//        response = productModuleService.editModule(request);
//      }
//      if (response.getRetCode() == 0) {
//        return this.showMessage("添加成功", "", "/productModule/edit?id=" + request.getId(), true);
//      } else {
//        return this.showMessage("添加失败", response.getRetDesc(),
//            "/productModule/edit?id=" + request.getId(), true);
//      }
//    } catch (Exception e) {
//      LoggerUtil.error("目录编辑异常", e);
//      throw e;
//    }
//  }
//
//  /**
//   * 资源目录删除
//   * 
//   * @return
//   */
//  @RequestMapping("/delete")
//  @ResponseBody
//  public String moduleDel(Integer id) {
//    try {
//      BaseResponse response = null;
//      Boolean aBoolean = productModuleService.deleteModule(id);
//      if (aBoolean) {
//        response = new BaseResponse(ResultType.SUCCESS);
//      } else {
//        response = new BaseResponse(ResultType.SYS_FAIL);
//      }
//      return JSON.toJSONString(response);
//    } catch (Exception e) {
//      LoggerUtil.error("目录删除异常", e);
//      throw e;
//    }
//  }
//
//}
