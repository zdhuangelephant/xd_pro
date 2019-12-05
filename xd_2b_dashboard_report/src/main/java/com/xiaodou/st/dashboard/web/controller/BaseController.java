package com.xiaodou.st.dashboard.web.controller;

import org.springframework.security.core.context.SecurityContextHolder;

import com.xiaodou.st.dashboard.service.admin.AdminUser;


/**
 * 
 * @name BaseController CopyRright (c) 2017 by 李德洪
 * 
 * @author 李德洪
 * @date 2017年3月28日
 * @description TODO
 * @version 1.0
 */
public class BaseController {

  // @Resource
  // ClassService classService;
  // @Resource
  // ProductService productService;
  //
  // /**
  // * 获取班级列表
  // * @description TODO
  // * @author 李德洪
  // * @Date 2017年4月5日
  // * @return
  // */
  // public List<ClassDO> listClass() {
  // return classService.listClass();
  // }
  // /**
  // * 获取专业列表
  // *
  // * @param modelAndView
  // */
  // public void getProductCatList(ModelAndView modelAndView) {
  // // 专业
  // List<ProductCategoryModel> productCategoryList = productCategoryService.queryAllCategory();
  // modelAndView.addObject("productCategoryList", productCategoryList);
  // }
  //
  // /**
  // * 查询管理人员添加的班级
  // *
  // * @param adminId
  // * @return
  // */
  // public List<ClassModel> listClass(Long unitId) {
  // Map<String, Object> input = Maps.newHashMap();
  // input.put("unitId", unitId);
  // return classService.listClass(input);
  // }
  //
  // /**
  // * 获取考期列表
  // *
  // * @param view
  // * @param applyList
  // */
  // public void getExamDateList(ModelAndView modelAndView) {
  // List<ProductModel> examDateList = stServiceFacade.queryExamDateCat();
  // modelAndView.addObject("examDateList", examDateList);
  // }
  //
  // public List<ProductModel> getExamDateList() {
  // return stServiceFacade.queryExamDateCat();
  // }
  //


  /**
   * 获取当前登录用户
   */
  protected AdminUser getAdminUser() {
    return (AdminUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  @Override
  public String toString() {
    return super.toString();
  }

}
