package com.xiaodou.ms.web.controller.product;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.xiaodou.ms.model.exam.QuestionBankQuestionTypeModel;
import com.xiaodou.ms.model.product.ProductModel;
import com.xiaodou.ms.model.product.ProductQuestionModel;
import com.xiaodou.ms.service.course.CourseChapterService;
import com.xiaodou.ms.service.exam.QuestionBankQuestionTypeService;
import com.xiaodou.ms.service.product.ProductItemService;
import com.xiaodou.ms.service.product.ProductQuestionService;
import com.xiaodou.ms.service.product.ProductService;

/**
 * Created by zyp on 15/8/11.
 */
@Controller("productQuestionController")
@RequestMapping("/productQuestion")
public class ProductQuestionController {

  @Resource
  ProductQuestionService productQuestionService;

  @Resource
  ProductItemService productItemService;

  @Resource
  ProductService productService;

  @Resource
  CourseChapterService courseChapterService;

  @Resource
  QuestionBankQuestionTypeService questionBankQuestionTypeService;

  /**
   * 产品题库
   * @param productId
   * @return
   */
  @RequestMapping("/index")
  public ModelAndView productQuestion(Long productId){
    ModelAndView modelAndView = new ModelAndView("/product/productQuestion");
    String parentTemplate =
      "<a  onclick=\"showQuestion(${node.data.productId},${node.id},this)\" style=\"cursor: pointer;\" class=\"folder\">${node.data.chapterId}[${node.name}]</a>";
    String childTemplate =
      "<a  onclick=\"showQuestion(${node.data.productId},${node.id},this)\" style=\"cursor: pointer;\" class=\"file\">${node.data.chapterId}[${node.name}]</a>";
    String chapterTree = productItemService.jqueryChapterTree(productId, parentTemplate, childTemplate);
    modelAndView.addObject("chapterTree",chapterTree);

    List<QuestionBankQuestionTypeModel> typeList = questionBankQuestionTypeService.typeList();
    modelAndView.addObject("typeList",typeList);

    return modelAndView;
  }

  /**
   *
   * @param productId
   * @return
   */
  @RequestMapping("/questionAdd")
  public ModelAndView productQuestionAdd(Long productId){
    ModelAndView modelAndView = new ModelAndView("/product/productQuestionAdd");
    String chapterTree = productItemService.resourceAddSelectTree(productId, "");
    modelAndView.addObject("chapterTree",chapterTree);
    ProductModel product = productService.findSubjectById(productId);
    Long courseId = product.getResourceSubject();
    String courseChapterTree = courseChapterService.ChapterSelectTree(courseId,0L,"");
    modelAndView.addObject("courseChapterTree",courseChapterTree);
    modelAndView.addObject("productId",productId);
    modelAndView.addObject("courseId",courseId);

    List<QuestionBankQuestionTypeModel> typeList = questionBankQuestionTypeService.typeList();
    modelAndView.addObject("typeList",typeList);
    return modelAndView;
  }

  /**
   * 题目列表
   * @param productId
   * @param chapterId
   * @return
   */
  @RequestMapping("/list")
  @ResponseBody
  public String questionList(Long productId,Long chapterId){
    if (chapterId==null){
      chapterId = 0L;
    }
    List<ProductQuestionModel> questionList =
      productQuestionService.queryQuestionListByChapterId(productId, chapterId);
    return JSON.toJSONString(questionList);
  }

  /**
   * 删除题目
   * @param id
   * @return
   */
  @RequestMapping("/delete")
  @ResponseBody
  public String deleteQuestion(Integer id){
    productQuestionService.deleteQuestion(id);
    return "true";
  }

  /**
   * 增加题目
   * @param questionId
   * @param chapterId
   * @param productId
   * @return
   */
  @RequestMapping("/add")
  @ResponseBody
  public String addQuestionId(Long questionId,Long chapterId,Long productId){
    ProductQuestionModel productQuestionModel =
      productQuestionService.addQuestion(questionId, chapterId, productId);
    return JSON.toJSONString(productQuestionModel);
  }

}
