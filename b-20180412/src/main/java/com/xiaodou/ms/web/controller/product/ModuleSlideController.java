package com.xiaodou.ms.web.controller.product;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.ms.model.product.ModuleSlideModel;
import com.xiaodou.ms.service.product.ModuleSlideService;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.request.product.SlideAddRequest;
import com.xiaodou.ms.web.request.product.SlideEditRequest;

/**
 * Created by zyp on 15/8/18.
 */
@Controller("moduleSlideController")
@RequestMapping("/slide")
public class ModuleSlideController extends BaseController {

  @Resource
  ModuleSlideService moduleSlideService;

 /* @Resource
  ProductModuleService productModuleService;*/

  /**
   * 列表
   * @return
   */
  @RequestMapping("/list")
  public ModelAndView slideList(Integer moduleId){
    ModelAndView modelAndView = new ModelAndView("/product/slideList");
    if (moduleId!=null){
        modelAndView.addObject("moduleId",moduleId);
    }
    List<ModuleSlideModel> slideList = moduleSlideService.slideList(moduleId);
    List<ModuleSlideModel> keywords = moduleSlideService.getUniqueMoudle();
    modelAndView.addObject("keywords",keywords);
    modelAndView.addObject("slideList",slideList);
    return modelAndView;
  }

  /**
   * 添加
   * @return
   */
  @RequestMapping("/add")
  public ModelAndView add(){
    ModelAndView modelAndView = new ModelAndView("/product/slideAdd");
    List<ModuleSlideModel> slideList = moduleSlideService.slideList(null);
    List<ModuleSlideModel> keywords = moduleSlideService.getUniqueMoudle();
    
    modelAndView.addObject("keywords",keywords);
    modelAndView.addObject("slideList",slideList);
    return modelAndView;
  }

  /**
   * 添加
   * @return
   */
  @RequestMapping("/doAdd")
  public ModelAndView doAdd(SlideAddRequest request){
    moduleSlideService.addSlide(request);
    return this.showMessage("成功",null,null,true);
  }

  /**
   * 编辑
   * @return
   */
  @RequestMapping("/edit")
  public ModelAndView edit(Integer id){
    ModelAndView modelAndView = new ModelAndView("/product/slideEdit");
    ModuleSlideModel slideModel = moduleSlideService.findById(id);
    modelAndView.addObject("slideModel",slideModel);
    List<ModuleSlideModel> keywords = moduleSlideService.getUniqueMoudle();
    modelAndView.addObject("keywords",keywords);
    
    return modelAndView;
  }

  /**
   * 编辑
   * @return
   */
  @RequestMapping("/doEdit")
  public ModelAndView doEdit(SlideEditRequest request){
    moduleSlideService.editSlide(request);
    return this.showMessage("成功",null,null,true);
  }

  /**
   * 删除
   * @return
   */
  @RequestMapping("/delete")
  @ResponseBody
  public String delete(Integer id){
    moduleSlideService.deleteSlide(id);
    return "true";
  }

  /**
   * 排序
   * @param orders
   * @return
   */
  @RequestMapping("/order")
  @ResponseBody
  public String order(String orders){
    String[] orderItems = orders.split(";");
    for (String orderItem:orderItems){
      String[] split = orderItem.split(":");
      ModuleSlideModel slideModel = new ModuleSlideModel();
      slideModel.setId(Integer.parseInt(split[0]));
      slideModel.setListOrder(Integer.parseInt(split[1]));
      moduleSlideService.editSlide(slideModel);
    }
    return "true";
  }

}
