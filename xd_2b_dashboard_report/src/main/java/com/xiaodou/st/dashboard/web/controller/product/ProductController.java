package com.xiaodou.st.dashboard.web.controller.product;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.st.dashboard.constants.Constants;
import com.xiaodou.st.dashboard.domain.apply.ApplyDO;
import com.xiaodou.st.dashboard.domain.product.RawDataProductDO;
import com.xiaodou.st.dashboard.service.apply.ApplyService;
import com.xiaodou.st.dashboard.service.product.ProductService;
import com.xiaodou.st.dashboard.web.controller.BaseController;

@Controller("productController")
@RequestMapping("/product")
public class ProductController extends BaseController {

  @Resource
  ProductService productService;
  @Resource
  ApplyService applyService;
  
  
  @RequestMapping("/category_list")
  @ResponseBody
  public String categoryList() {
    return FastJsonUtil.toJson(productService.listCategory());
  }
  
  /**
   * 
   * @description 查看专业下面的课程
   * @author 李德洪
   * @Date 2017年4月4日
   * @param catId
   * @return
   */
  @RequestMapping("/product_list")
  @ResponseBody
  public String productList(Integer catId) {
    return FastJsonUtil.toJson(productService.listProduct(catId));
  }

  /**
   * 
   * @description 查询学生课程报名情况
   * @author 李德洪
   * @Date 2017年4月4日
   * @param catId
   * @param studentId
   * @param resp
   * @return
   * @throws UnsupportedEncodingException
   */
  @RequestMapping(value = "/student_product_list")
  @ResponseBody
  public String findProductList(Integer catId, Integer studentId){
    if (null == catId || null == studentId || catId == 0 || studentId == 0) return null;
    List<RawDataProductDO> productList = productService.listProduct(catId);
    if (null == productList || productList.size() < 1) return null;
    List<ApplyDO> applyList = applyService.listApplyBySid(studentId);
    List<Integer> productIdList = Lists.transform(applyList, new Function<ApplyDO, Integer>() {
      @Override
      public Integer apply(ApplyDO input) {
        if (null == input) return null;
        return input.getProductId();
      }
    });
    for (RawDataProductDO product : productList) {
      if (productIdList.contains(product.getId()))
        product.setApplyStatus(Constants.HAS_APPLY);// 已報名
      else
        product.setApplyStatus(Constants.NO_APPLY);// 未报名
    }
    return FastJsonUtil.toJson(productList);
  }
  
  
}
