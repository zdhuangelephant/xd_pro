package com.xiaodou.ms.web.controller.product;

import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.Base64Utils;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.ms.constants.XdmsConstant;
import com.xiaodou.ms.dao.mission.MissionProductCategoryRelationModelDao;
import com.xiaodou.ms.dao.product.ProductCategoryRelationDao;
import com.xiaodou.ms.dao.product.ProductDao;
import com.xiaodou.ms.enums.ProductCatagoryClassify;
import com.xiaodou.ms.model.major.MajorDataModel;
import com.xiaodou.ms.model.mission.MissionProductCategoryRelationModel;
import com.xiaodou.ms.model.product.ProductCategoryModel;
import com.xiaodou.ms.model.product.ProductCategoryRelation;
import com.xiaodou.ms.model.product.ProductModel;
import com.xiaodou.ms.model.product.RegionModel;
import com.xiaodou.ms.service.major.MajorDataService;
import com.xiaodou.ms.service.product.ProductCategoryService;
import com.xiaodou.ms.service.product.RegionService;
import com.xiaodou.ms.vo.mq.AddProductRelationEvent;
import com.xiaodou.ms.vo.mq.AddProductRelationEvent.TransferProductRelationData;
import com.xiaodou.ms.vo.mq.DeleteProductRelationEvent;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.request.product.ProductCategoryCreateRequest;
import com.xiaodou.ms.web.request.product.ProductCategoryEditRequest;
import com.xiaodou.ms.web.request.product.ProductCategoryQueryConditionReq;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IJoinQueryParam;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.JoinQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

/**
 * Created by zyp on 15/6/25.
 */
@Controller("productCategoryController")
@RequestMapping("/productCategory")
public class ProductCategoryController extends BaseController {

  @Resource
  ProductCategoryService productCategoryService;
  @Resource
  ProductDao productDao;
  @Resource
  ProductCategoryRelationDao productCategoryRelationDao;
  @Resource
  MissionProductCategoryRelationModelDao missionProductCategoryRelationModelDao;
  // @Resource
  // ProductModuleService productModuleService;
  @Resource
  MajorDataService majorDataService;
  @Resource
  RegionService regionService;


  /**
   * productCategory目录
   * 
   * @return
   * @throws Exception
   */
  @RequestMapping("/list")
  public ModelAndView itemList(String module /* String productLine */, String typeCode,
      String majorName, String examSchool, Integer page, Long catId, Integer classify)
      throws Exception {
    try {
      page = (null == page || 0 == page) ? 1 : page;
      ModelAndView modelAndView = new ModelAndView("/product/categoryList");
      ProductCategoryQueryConditionReq reqConds = new ProductCategoryQueryConditionReq();
      // if (StringUtils.isNotBlank(productLine)) {
      // reqConds.setProductLine(productLine);
      // }
      if (StringUtils.isNotBlank(module)) {
        reqConds.setModule(module);
      }
      if (StringUtils.isNotBlank(typeCode)) {
        reqConds.setTypeCode(URLDecoder.decode(new String(Base64Utils.decode(typeCode)), "utf8"));
      }
      if (StringUtils.isNotBlank(majorName)) {
        reqConds.setMajorName(URLDecoder.decode(new String(Base64Utils.decode(majorName)), "utf8"));
      }
      if (StringUtils.isNotBlank(examSchool)) {
        reqConds
            .setExamSchool(URLDecoder.decode(new String(Base64Utils.decode(examSchool)), "utf8"));
      }

      // ProductModuleModel module = productModuleService.findModuleById(catId);
      /*
       * List<ProductCategoryModel> subject = productCategoryService.getProductCategorylist(0L,
       * catId, classify);
       */

      List<RegionModel> queryAllRegion = regionService.queryAllRegion();

      Page<ProductCategoryModel> subject =
          productCategoryService.getProductCategorylist(reqConds, page, 0L, catId, classify);
      modelAndView.addObject("subjectList", subject);
      modelAndView.addObject("regionList", queryAllRegion);
      // modelAndView.addObject("regionId", reqConds.getRegionId());
      // modelAndView.addObject("catId", catId);
      modelAndView.addObject("module", reqConds.getModule());
      modelAndView.addObject("classify", classify);
      // modelAndView.addObject("productLine", reqConds.getProductLine());
      modelAndView.addObject("typeCode", reqConds.getTypeCode());
      modelAndView.addObject("majorName", reqConds.getMajorName());
      modelAndView.addObject("examSchool", reqConds.getExamSchool());
      if (null != classify) {
        modelAndView.addObject("classifyName", ProductCatagoryClassify.getByCode(classify)
            .getName());
      }
      return modelAndView;
    } catch (Exception e) {
      LoggerUtil.error("列表异常", e);
      throw e;
    }
  }

  /**
   * 目录添加页面
   * 
   * @param parentId
   * @return
   */
  @RequestMapping("/add")
  public ModelAndView resourceCategoryAdd(Integer classify, String module) {
    ModelAndView modelAndView = new ModelAndView("/product/categoryAdd");
    // List<ProductModuleModel> productModuleList = productModuleService.queryAllModule();
    List<RegionModel> queryAllRegion = regionService.queryAllRegion();
    modelAndView.addObject("moduleList", queryAllRegion);// 地域列表
    String selectTree = productCategoryService.categorySelectTree(0L, classify, null);
    // modelAndView.addObject("productModuleList", productModuleList);
    modelAndView.addObject("selectTree", selectTree);
    modelAndView.addObject("classify", classify);
    modelAndView.addObject("module", module);
    return modelAndView;
  }

  /**
   * 添加课程
   * 
   * @param parentId
   * @return
   */
  @RequestMapping("/add_course")
  @ResponseBody
  public String resourceCategoryAddCourse(Long catId, String courseIdList) {
    BaseResponse response = null;
    try {
      StringBuffer message = new StringBuffer(200);
      response = new BaseResponse(ResultType.SUCCESS);
      Timestamp beginApplyTime = null, endApplyTime = null;
      ProductCategoryModel category = productCategoryService.findCategoryById(catId);
      IJoinQueryParam joinParam = new JoinQueryParam();
      joinParam.addInput("productCategoryId", catId);
      joinParam.addJoin("productCategoryId", catId);
      joinParam.addOutputs(CommUtil.getAllField(ProductModel.class));
      Page<ProductModel> productPage = productDao.cascadeQueryProductInCat(joinParam);
      if (null != productPage && null != productPage.getResult()
          && productPage.getResult().size() > 0) {
        beginApplyTime = productPage.getResult().get(0).getBeginApplyTime();
        endApplyTime = productPage.getResult().get(0).getEndApplyTime();
      }
      if (StringUtils.isJsonBlank(courseIdList) || null == category) {
        return new BaseResponse(ResultType.SYS_FAIL).toString();
      }

      Integer relationResult = 0;// 统计关联成功的条数
      // 按照id添加课程
      List<Long> _courseIdList =
          FastJsonUtil.fromJsons(courseIdList, new TypeReference<List<Long>>() {});
      for (Long courseId : _courseIdList) {
        if (null == courseId) {
          continue;
        }
        ProductModel product = new ProductModel();
        product.setId(courseId);
        product = productDao.findEntityById(product);
        if (null == product) {
          continue;
        }
        if (!product.getModule().equals(category.getModule())) {
          message.append("课程地域与专业地域不一致");
          continue;
        }
        // 判断时间
        if (null != beginApplyTime && !beginApplyTime.equals(product.getBeginApplyTime())) {
          message.append(String.format(XdmsConstant.APPLY_TIME_UNMATCH, product.getName()));
          continue;
        }
        if (null != endApplyTime && !endApplyTime.equals(product.getEndApplyTime())) {
          message.append(String.format(XdmsConstant.APPLY_TIME_UNMATCH, product.getName()));
          continue;
        }
        IQueryParam param = new QueryParam();
        param.addInput("productCategoryId", catId);
        param.addInput("productId", courseId);
        param.addOutputs(CommUtil.getAllField(ProductCategoryRelation.class));
        Page<ProductCategoryRelation> relationPage =
            productCategoryRelationDao.findEntityListByCond(param, null);
        // 判断重复添加
        if (null != relationPage && null != relationPage.getResult()
            && relationPage.getResult().size() > 0) {
          continue;
        }
        // 正式添加关联关系
        ProductCategoryRelation relation = new ProductCategoryRelation();
        relation.setProductCategoryId(new Long(catId));
        relation.setProductId(courseId);
        productCategoryRelationDao.addEntity(relation);
        MissionProductCategoryRelationModel productRelation =
            new MissionProductCategoryRelationModel();
        productRelation.setProductCategoryId(new Long(catId));
        productRelation.setProductId(courseId);
        missionProductCategoryRelationModelDao.addEntity(productRelation);

        AddProductRelationEvent event = new AddProductRelationEvent();
        event.setModule(product.getModule());
        event.setDataModel(new TransferProductRelationData(relation, product.getModule()));
        event.send();
        relationResult++;
      }
      // 更新courseCount
      IQueryParam param = new QueryParam();
      param.addInput("productCategoryId", catId);
      param.addOutputs(CommUtil.getAllField(ProductCategoryRelation.class));
      Page<ProductCategoryRelation> relationPage =
          productCategoryRelationDao.findEntityListByCond(param, null);
      category.setCourseCount(relationPage.getResult().size());
      productCategoryService.editCategory(category);
      String result = "选择" + _courseIdList.size() + "门课，" + "成功关联" + relationResult + "门";
      response.setRetDesc(result + ":" + message.toString());
    } catch (Exception e) {
      response = new BaseResponse(ResultType.SYS_FAIL);
      LoggerUtil.error("专业添加课程异常", e);
    }
    return response.toString();
  }

  /**
   * 删除课程
   * 
   * @return
   */
  @RequestMapping("/delete_course")
  @ResponseBody
  public String redourceCategoryDelCourse(Integer catId, String courseId) {
    BaseResponse response = null;
    try {
      IQueryParam param = new QueryParam();
      param.addInput("productCategoryId", catId);
      param.addInput("productId", courseId);
      param.addOutput("id", XdmsConstant.YES);
      Page<ProductCategoryRelation> relationPage =
          productCategoryRelationDao.findEntityListByCond(param, null);
      if (null != relationPage) {
        List<Long> idList = Lists.newArrayList();
        for (ProductCategoryRelation relation : relationPage.getResult()) {
          idList.add(relation.getId());
        }
        Map<String, Object> cond = Maps.newHashMap();
        cond.put("ids", idList);

        if (idList.isEmpty()) {
          response = new BaseResponse(ResultType.NO_REALTION_TO_DELETE);
          return JSON.toJSONString(response);
        }
        Boolean aBoolean = productCategoryRelationDao.deleteEntityByCond(cond);
        if (aBoolean) {
          // missionProductCategoryRelationModelDao.deleteEntityByCond(cond);
          for (Long id : idList) {
            ProductModel entity = new ProductModel();
            entity.setId(id);
            entity = productDao.findEntityById(entity);
            DeleteProductRelationEvent event = new DeleteProductRelationEvent();
            event.setModule(entity.getModule());
            event.setDataModel(new TransferProductRelationData(id, entity.getModule()));
            event.send();
          }
        }
      }
      // 修改courseCount
      IQueryParam queryParam = new QueryParam();
      queryParam.addInput("productCategoryId", catId);
      queryParam.addOutputs(CommUtil.getAllField(ProductCategoryRelation.class));
      Page<ProductCategoryRelation> relations =
          productCategoryRelationDao.findEntityListByCond(queryParam, null);
      ProductCategoryModel category = new ProductCategoryModel();
      category.setId(catId.longValue());
      category.setCourseCount(relations.getResult().size());
      productCategoryService.editCategory(category);

      response = new BaseResponse(ResultType.SUCCESS);
    } catch (Exception e) {
      response = new BaseResponse(ResultType.SYS_FAIL);
      LoggerUtil.error("专业删除课程异常", e);
    }
    return JSON.toJSONString(response);
  }

  /**
   * 资源目录添加
   * 
   * @return
   */
  @RequestMapping("/doAdd")
  public ModelAndView resourceCategoryDoAdd(ProductCategoryCreateRequest request, Errors errors)
      throws Exception {
    try {
      BaseResponse response = null;
      errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        MajorDataModel md =
            majorDataService.findMajorByIdAndRegion(request.getTypeCode(), request.getModule());
        if (md == null) {
          response = new BaseResponse(ResultType.VALID_FAIL);
          response.setRetDesc("专业代码不存在");
        } else {
          md.initModuleInfo(md.getMajorInfo());
          request.setChiefAcademy(md.getExamSchool());
          response = productCategoryService.addCategory(request);
        }
      }
      if (response.getRetCode() == 0) {
        return this.showMessage("添加成功", "", null, true);
      } else {
        return this.showMessage("添加失败", response.getRetDesc(), null, true);
      }
    } catch (Exception e) {
      LoggerUtil.error("目录创建异常", e);
      throw e;
    }
  }

  /**
   * 目录编辑
   * 
   * @param categoryId
   * @return
   */
  @RequestMapping("/edit")
  public ModelAndView resourceCategoryEdit(String categoryId, Integer classify) {
    ModelAndView modelAndView = new ModelAndView("/product/categoryEdit");
    ProductCategoryModel productCategory =
        productCategoryService.findCategoryById(Long.parseLong(categoryId));
    // List<ProductModuleModel> productModuleList = productModuleService.queryAllModule();
    String selectTree =
        productCategoryService.categorySelectTree(0L, classify, productCategory.getParentId()
            .toString());
    List<RegionModel> queryAllRegion = regionService.queryAllRegion();
    modelAndView.addObject("moduleList", queryAllRegion);// 地域列表
    // modelAndView.addObject("productModuleList", productModuleList);
    modelAndView.addObject("productCategory", productCategory);
    modelAndView.addObject("selectTree", selectTree);
    return modelAndView;
  }

  /**
   * 资源目录修改
   * 
   * @return
   */
  @RequestMapping("/doEdit")
  public ModelAndView resourceCategoryDoEdit(ProductCategoryEditRequest request, Errors errors)
      throws Exception {
    try {
      BaseResponse response = null;
      errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        response = productCategoryService.editCategory(request);
      }
      if (response.getRetCode() == 0) {
        return this.showMessage("编辑成功", "", "/productCategory/edit?categoryId=" + request.getId(),
            true);
      } else {
        return this.showMessage("编辑失败", response.getRetDesc(), "/productCategory/edit?categoryId="
            + request.getId(), true);
      }
    } catch (Exception e) {
      LoggerUtil.error("目录编辑异常", e);
      throw e;
    }
  }

  /**
   * 资源目录删除
   * 
   * @return
   */
  @RequestMapping("/delete")
  @ResponseBody
  public String redourceCategoryDel(Long catId) {
    try {
      BaseResponse response = null;
      Boolean aBoolean = productCategoryService.deleteCategory(catId);
      if (aBoolean) {
        response = new BaseResponse(ResultType.SUCCESS);
      } else {
        response = new BaseResponse(ResultType.SYS_FAIL);
      }
      return JSON.toJSONString(response);
    } catch (Exception e) {
      LoggerUtil.error("目录删除异常", e);
      throw e;
    }
  }

  /**
   * 获取产品名称
   * 
   * @author zhouhuan
   * @author majorCode
   * @return
   */
  @ResponseBody
  @RequestMapping("/getMajorName")
  public String getMajorName(String majorCode, String module) {
    try {
      Map<String, String> map = new HashMap<String, String>();
      MajorDataModel md = majorDataService.findMajorByIdAndRegion(majorCode, module);
      if (md != null) {
        map.put("retCode", "0");
        map.put("majorName", md.getName());
      } else {
        map.put("retCode", "404");
      }
      return FastJsonUtil.toJson(map);
    } catch (Exception e) {
      throw e;
    }
  }


}
