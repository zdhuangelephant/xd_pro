package com.xiaodou.ms.web.controller.product;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.Base64Utils;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.ms.constants.XdmsConstant;
import com.xiaodou.ms.dao.product.ProductCategoryRelationDao;
import com.xiaodou.ms.enums.ExamRuleType;
import com.xiaodou.ms.model.course.CourseSubjectModel;
import com.xiaodou.ms.model.exam.QuestionBankExamRuleModel;
import com.xiaodou.ms.model.exam.QuestionBankQuestionType;
import com.xiaodou.ms.model.exam.QuestionBankQuestionTypeModel;
import com.xiaodou.ms.model.exam.QuestionBankSetting;
import com.xiaodou.ms.model.major.MajorCourseInfo;
import com.xiaodou.ms.model.major.MajorCourseModel;
import com.xiaodou.ms.model.major.MajorDataModel;
import com.xiaodou.ms.model.mission.MissionModel;
import com.xiaodou.ms.model.product.FinalExamModel;
import com.xiaodou.ms.model.product.ProductCategoryModel;
import com.xiaodou.ms.model.product.ProductCategoryRelation;
import com.xiaodou.ms.model.product.ProductItemModel;
import com.xiaodou.ms.model.product.ProductModel;
import com.xiaodou.ms.model.product.RegionModel;
import com.xiaodou.ms.model.robot.ChallengeRobotModel;
import com.xiaodou.ms.model.score.ScoreRuleModel;
import com.xiaodou.ms.service.course.CourseSubjectService;
import com.xiaodou.ms.service.exam.QuestionBankExamRuleService;
import com.xiaodou.ms.service.exam.QuestionBankQuestionTypeService;
import com.xiaodou.ms.service.major.MajorCourseService;
import com.xiaodou.ms.service.major.MajorDataService;
import com.xiaodou.ms.service.mission.MissionService;
import com.xiaodou.ms.service.product.FinalExamService;
import com.xiaodou.ms.service.product.ProductCategoryService;
import com.xiaodou.ms.service.product.ProductItemService;
import com.xiaodou.ms.service.product.ProductService;
import com.xiaodou.ms.service.product.RegionService;
import com.xiaodou.ms.service.robot.RobotService;
import com.xiaodou.ms.service.scoreRule.ScoreRuleService;
import com.xiaodou.ms.vo.MissionVo;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.request.product.ProductCreateRequest;
import com.xiaodou.ms.web.request.product.ProductEditRequest;
import com.xiaodou.ms.web.request.product.ProductQueryConds;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.ms.web.response.product.SearchSubjectResponse;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

/**
 * Created by zyp on 15/4/19.
 */

/**
 * @author xd
 *
 */
@Controller("productController")
@RequestMapping("/product")
public class ProductController extends BaseController {

    /**
     * 产品
     */
    @Resource
    ProductService productService;

    /**
     * 产品分类
     */
    @Resource
    ProductCategoryService productCategoryService;

    /**
     * 题目类型
     */
    @Resource
    QuestionBankQuestionTypeService questionBankQuestionTypeService;

    /**
     * 课程
     */
    @Resource
    MajorCourseService majorCourseService;

    /**
     * 资源
     */
    @Resource
    CourseSubjectService courseSubjectService;

    @Resource
    MajorDataService majorDataService;

    /*
     * @Resource ProductModuleService productModuleService;
     */

    /**
     * 改造后的地域
     */
    @Resource
    RegionService regionService;

    /**
     * 计分规则
     */
    @Resource
    ScoreRuleService scoreRuleService;
    @Resource
    ProductCategoryRelationDao productCategoryRelationDao;

    /**
     * 机器人
     */
    @Resource
    RobotService robotService;

    /**
     * 期末测试
     */
    @Resource
    FinalExamService finalExamService;

    /**
    * 
    */
    @Resource
    ProductItemService productItemService;

    /**
     * 任务
     */
    @Resource
    MissionService missionService;

    /**
     * 命题蓝图
     */
    @Resource
    QuestionBankExamRuleService questionBankExamRuleService;

    /**
     * 题库设置
     * 
     * @param productId
     * @return
     */
    @RequestMapping("questionSetting")
    public ModelAndView questionBankSetting(Long productId) {
        ModelAndView modelAndView = new ModelAndView("/exam/setting");
        ProductModel productModel = productService.findSubjectById(productId);
        List<QuestionBankQuestionTypeModel> typeList = questionBankQuestionTypeService.typeList();
        QuestionBankSetting setting = new QuestionBankSetting();
        if (StringUtils.isNotBlank(productModel.getQuestionBankSetting())) {
            setting = JSON.parseObject(productModel.getQuestionBankSetting(), QuestionBankSetting.class);
        }
        Map<String, QuestionBankQuestionType> typeMap = new HashMap<>();
        if (setting.getTypeList() != null && setting.getTypeList().size() > 0) {
            for (QuestionBankQuestionType type : setting.getTypeList()) {
                typeMap.put(type.getId().toString(), type);
            }
        }
        modelAndView.addObject("setting", setting);
        modelAndView.addObject("typeList", typeList);
        modelAndView.addObject("productId", productId);
        modelAndView.addObject("typeMap", typeMap);
        return modelAndView;
    }

    /**
     * 题库设置
     * 
     * @return
     */
    @RequestMapping("editQuestionSetting")
    public ModelAndView editQuestionBankSetting(Long productId, HttpServletRequest request) {
        QuestionBankSetting setting = new QuestionBankSetting();
        String totalScore = request.getParameter("totalScore");
        if (StringUtils.isBlank(totalScore)) {
            setting.setTotalScore(100);
        } else {
            setting.setTotalScore(Integer.parseInt(totalScore));
        }
        String[] typeSelects = request.getParameterValues("typeSelect");
        if (typeSelects == null || typeSelects.length <= 0) {
            return this.showMessage("修改失败", "请先对复选框画勾", "/product/questionSetting?productId=" + productId, true);
        }
        List<QuestionBankQuestionType> typeList = new ArrayList<>();
        int sum = 0;
        for (String typeId : typeSelects) {
            QuestionBankQuestionType type = new QuestionBankQuestionType();
            type.setTypeName(request.getParameter("typeName" + typeId));
            type.setAnswerType(request.getParameter("answerType" + typeId));
            type.setId(Integer.parseInt(typeId));
            if (StringUtils.isNotBlank(request.getParameter("typeOrder" + typeId))) {
                type.setListOrder(Integer.parseInt(request.getParameter("typeOrder" + typeId)));
            } else {
                type.setListOrder(0);
            }
            if (StringUtils.isNotBlank(request.getParameter("questionNum" + typeId))) {
                type.setQuestionNum(Integer.parseInt(request.getParameter("questionNum" + typeId)));
            } else {
                type.setQuestionNum(0);
            }
            if (StringUtils.isNotBlank(request.getParameter("typeScore" + typeId))) {
                type.setScore(Integer.parseInt(request.getParameter("typeScore" + typeId)));
            } else {
                type.setScore(0);
            }
            typeList.add(type);
            sum = sum + type.getScore() * type.getQuestionNum();
        }
        setting.setTypeList(typeList);
        // 校验分数

        if (sum != setting.getTotalScore()) {
            return this.showMessage("修改失败", "总分数与各项分数之和不符", "/product/questionSetting?productId=" + productId, true);
        }

        ProductModel product = new ProductModel();
        product.setId(productId);
        product.setQuestionBankSetting(JSON.toJSONString(setting));
        productService.editSubject(product);
        return this.showMessage("修改成功", null, "/product/questionSetting?productId=" + productId, true);
    }

    /**
     * 专业课程列表
     * 
     * @return
     */
    @RequestMapping("/list_by_cat")
    public ModelAndView courseList(ProductQueryConds reqConds, Integer page/* ,Long catId, String courseCode */) {
        page = (null == page || 0 == page) ? 1 : page;
        ModelAndView modelAndView = new ModelAndView("product/catProductList");
        ProductCategoryModel category = productCategoryService.findCategoryById(reqConds.getCatId());
        MajorDataModel majorData = majorDataService.findMajorByIdAndRegion(category.getTypeCode(),
                category.getModule());
        try {
            if (reqConds.getName() != null && reqConds.getName().trim().length() > 0) {
                reqConds.setName(URLDecoder.decode(new String(Base64Utils.decode(reqConds.getName())), "utf8"));
            }
            if (reqConds.getCourseCode() != null && reqConds.getCourseCode().trim().length() > 0) {
                reqConds.setCourseCode(
                        URLDecoder.decode(new String(Base64Utils.decode(reqConds.getCourseCode())), "utf8"));
                ;
            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Page<ProductModel> courseSubjectList = productService.cascadeQueryCourseByCatId(reqConds, page);
        modelAndView.addObject("category", category);
        if (StringUtils.isNotBlank(reqConds.getCourseCode())) {
            modelAndView.addObject("courseCode", reqConds.getCourseCode());
        }
//    List<ProductModel> productList = courseSubjectList.getResult();
        // 查询关联课程
        IQueryParam param = new QueryParam();
        param.addInput("productCategoryId", reqConds.getCatId());
        param.addInput("relationState", 1);

        param.addOutputs(CommUtil.getAllField(ProductCategoryRelation.class));
        Page<ProductCategoryRelation> relationPage = productCategoryRelationDao.findEntityListByCond(param, null);

        ArrayList<ProductModel> overDateList = Lists.newArrayList();
        ArrayList<ProductCategoryRelation> relationList = Lists.newArrayList();
        if (null != courseSubjectList) {
            for (ProductModel product : courseSubjectList.getResult()) {
                if (product != null && product.getProductCategoryId() != null
                        && (product.getProductCategoryId().longValue() == category.getId().longValue())) {

                    overDateList.add(product);

                }
                MajorCourseInfo majorCourseInfo = majorCourseService.getMajorCourseInfo(product.getCourseInfo());
                product.setMajorCourseInfo(majorCourseInfo);
            }
        }
        if (reqConds.getIsExpired().equals("1")) {
            courseSubjectList.setPageNo(page);
            courseSubjectList.setPageSize(Page.DEFAULT_PAGESIZE);
            courseSubjectList.setResult(overDateList);
            courseSubjectList.setTotalCount(overDateList.size());
            courseSubjectList.setTotalPage(
                    (overDateList.size() + courseSubjectList.getPageSize() - 1) / courseSubjectList.getPageSize());
            modelAndView.addObject("subjectList", courseSubjectList);
            for (ProductModel product : overDateList) {
                if (relationPage != null && relationPage.getResult() != null) {
                    for (ProductCategoryRelation re : relationPage.getResult()) {
                        if (re.getProductId().longValue() == product.getId().longValue()) {
                            relationList.add(re);
                        }
                    }
                }
            }
            modelAndView.addObject("hasAttachCounts", relationList.size());
        } else {
            modelAndView.addObject("subjectList", courseSubjectList);
            if (relationPage != null && relationPage.getResult() != null) {
                for (ProductModel product : courseSubjectList.getResult()) {
                    for (ProductCategoryRelation re : relationPage.getResult()) {
                        if (re.getProductId().longValue() == product.getId().longValue()) {
                            relationList.add(re);
                        }
                    }
                }
            }
            modelAndView.addObject("hasAttachCounts", relationList.size());
        }
        List<RegionModel> queryAllRegion = regionService.queryAllRegion();
        modelAndView.addObject("regionList", queryAllRegion);
        modelAndView.addObject("majorData", majorData);
        modelAndView.addObject("reqConds", reqConds);
        return modelAndView;
    }

    /**
     * 课程列表
     * 
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView courseList(ProductQueryConds req, String name, String courseCode, String courseId, Integer page,
            String examDate, String module) {
        page = (null == page || 0 == page) ? 1 : page;
        ModelAndView modelAndView = new ModelAndView("product/productList");

        if (StringUtils.isNotBlank(name)) {
            try {
                req.setName(URLDecoder.decode(new String(Base64Utils.decode(name)), "utf8"));
            } catch (UnsupportedEncodingException e) {
                req.setName(StringUtils.EMPTY);
            }
        }

        if (StringUtils.isNotBlank(courseCode)) {
            try {
                req.setCourseCode(URLDecoder.decode(new String(Base64Utils.decode(courseCode)), "utf8"));
            } catch (UnsupportedEncodingException e) {
                req.setCourseCode(StringUtils.EMPTY);
            }
        }

        if (StringUtils.isNotBlank(courseId)) {
            try {
                req.setCourseId(Long.parseLong(courseId));
            } catch (Exception e) {
                req.setCourseId(null);
            }
        }

        if (StringUtils.isNotBlank(module)) {
            req.setModule(module);
        }

        Page<ProductModel> courseSubjectList = productService.queryCourseByCode(req, page, examDate, "0");
        if (StringUtils.isNotBlank(examDate)) {
            modelAndView.addObject("examDate", examDate);
        }
//    List<ProductModel> productList = courseSubjectList.getResult();

        List<RegionModel> queryAllRegion = regionService.queryAllRegion();

        // 判断每一门课的所属地域
        if (null != courseSubjectList) {
            for (RegionModel region : queryAllRegion) {
                for (ProductModel product : courseSubjectList.getResult()) {
                    if (product.getModule().equals(region.getModule())) {
                        product.setModuleName(region.getModuleName());
                    }
                }
            }
        }

        if (null != courseSubjectList) {
            for (ProductModel product : courseSubjectList.getResult()) {
                MajorCourseInfo majorCourseInfo = majorCourseService.getMajorCourseInfo(product.getCourseInfo());
                product.setMajorCourseInfo(majorCourseInfo);
            }
        }

        modelAndView.addObject("regionList", queryAllRegion);
        modelAndView.addObject("module", req.getModule());
        modelAndView.addObject("subjectList", courseSubjectList);
        modelAndView.addObject("showStatus", req.getShowStatus());
        modelAndView.addObject("courseCode", req.getCourseCode());
        modelAndView.addObject("name", req.getName());
        modelAndView.addObject("courseId", req.getCourseId());
        modelAndView.addObject("isExpired", req.getIsExpired());

        return modelAndView;
    }

    /**
     * 增加课程界面
     * 
     * @return
     */
    @RequestMapping("add")
    public ModelAndView resourceCourseAdd(String courseCode) {
        ModelAndView modelAndView = new ModelAndView("product/productAdd");
        Page<CourseSubjectModel> courseSubjectModelPage = courseSubjectService.queryAllCourse();
        List<RegionModel> queryAllRegion = regionService.queryAllRegion();
        modelAndView.addObject("regionList", queryAllRegion);
        modelAndView.addObject("subjectList", courseSubjectModelPage.getResult());
        return modelAndView;
    }

    /**
     * 资源课程添加
     * 
     * @return
     */
    @RequestMapping("/doAdd")
    public ModelAndView resourceCourseDoAdd(ProductCreateRequest request, Errors errors) throws Exception {
        try {
            BaseResponse response = null;
            errors = request.validate();
            if (errors.hasErrors()) {
                response = new BaseResponse(ResultType.VALID_FAIL);
                response.setRetDesc(this.getErrMsg(errors));
            } else {
                MajorCourseModel model = majorCourseService.findMajorByIdAndRegion(request.getCourseCode(),
                        request.getModule());

                // 添加地域下的默认规则
                List<RegionModel> module = regionService.queryRegionWithModule(request.getModule());
                if (module != null) {
                    request.setRuleId(module.get(0).getRuleId());
                }

                if (model != null) {
                    response = productService.addSubject(request, 0);
                } else {
                    response = new BaseResponse(ResultType.VALID_FAIL);
                    response.setRetDesc("课程不存在，请验证课程码");
                }
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
     * 资源课程删除
     * 
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public String resourceCourseDel(Long id) {
      try {
        BaseResponse response = null;
        Boolean aBoolean = productService.deleteSubject(id);
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
     * 模糊查询资源标题列表
     * 
     * @return
     */
    @RequestMapping("/search_subject_name")
    @ResponseBody
    public String searchSubjectName(String resourceSubjectName) {
        SearchSubjectResponse res = new SearchSubjectResponse(ResultType.SUCCESS);
        Page<CourseSubjectModel> coursePage = courseSubjectService.searchMatchCourse(resourceSubjectName);
        if (null == coursePage || null == coursePage.getResult() || coursePage.getResult().size() == 0)
            return FastJsonUtil.toJson(res);
        for (CourseSubjectModel course : coursePage.getResult())
            res.getSubjectNameList().add(String.format("<p value=\"%s\">%s</p>", course.getId(), course.getName()));
        return FastJsonUtil.toJson(res);
    }

    /**
     * 编辑
     * 
     * @param id
     * @return
     */
    @RequestMapping("/edit")
    public ModelAndView resourceCourseEdit(Long id) {
        ModelAndView modelAndView = new ModelAndView("product/productEdit");
        ProductModel productModel = productService.findSubjectById(id);
        if (null != productModel && null != productModel.getResourceSubject()) {
            CourseSubjectModel subject = courseSubjectService.findSubjectById(productModel.getResourceSubject());
            if (null != subject) {
                productModel.setResourceSubjectName(subject.getName());
            }
        }
        List<RegionModel> queryAllRegion = regionService.queryAllRegion();
        modelAndView.addObject("regionList", queryAllRegion);
        modelAndView.addObject("productModel", productModel);
        // String selectTree =
        // productCategoryService
        // .categorySelectTree(0, null, productModel.getProductCategoryId().toString());
        // modelAndView.addObject("selectTree", selectTree);
        return modelAndView;
    }

    /**
     * 资源课程编辑
     * 
     * @return
     */
    @RequestMapping("/doEdit")
    public ModelAndView resourceCourseDoEdit(ProductEditRequest request, Errors errors) {
        try {
            BaseResponse response = null;
            errors = request.validate();
            if (errors.hasErrors()) {
                response = new BaseResponse(ResultType.VALID_FAIL);
                response.setRetDesc(this.getErrMsg(errors));
            } else {
                productService.editSubject(request);
                response = new BaseResponse(ResultType.SUCCESS);
            }
            if (response.getRetCode() == 0) {
                return this.showMessage("编辑成功", "", "/product/edit?id=" + request.getId(), true);
            } else {
                return this.showMessage("编辑失败", response.getRetDesc(), "/product/edit?id=" + request.getId(), true);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 获取课程名称
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping("/getCourseName")
    public String getCourseName(String courseCode, String region) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            MajorCourseModel model = majorCourseService.findMajorByIdAndRegion(courseCode, region);
            if (model != null) {
                map.put("retCode", "0");
                map.put("courseName", model.getName());
            } else {
                map.put("retCode", "404");
            }

            // 获取该课程下的已经存在的产品 xd_course_product
//      List<ProductModel> existsProducts = productService.queryProductByCourse(courseCode);
//      if (null != existsProducts && existsProducts.size() != 0) {
//        map.put("existsProducts", existsProducts);
//      }
            return FastJsonUtil.toJson(map);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 新手课程添加页面
     * 
     * @param parentId
     * @return
     */
    @RequestMapping("/addBeginner")
    public ModelAndView addBeginner(Long moduleId) {
        ModelAndView modelAndView = new ModelAndView("/product/productBeginnerAdd");
//    ProductModuleModel productModule =
//        productModuleService.findModuleById(productModuleId);
        RegionModel findRegionById = regionService.findRegionById(moduleId);
        modelAndView.addObject("regionModule", findRegionById);
        Page<CourseSubjectModel> courseSubjectModelPage = courseSubjectService.queryAllCourse();
        modelAndView.addObject("subjectList", courseSubjectModelPage.getResult());
        return modelAndView;
    }

    /**
     * 新手课程课程添加
     * 
     * @return
     */
    @RequestMapping("/doAddBeginner")
    public ModelAndView doAddBeginner(ProductCreateRequest request, Errors errors) throws Exception {
        try {
            BaseResponse response = null;
            request.setCourseCode("00000");// 新手课程
            request.setShowStatus(1);

            List<RegionModel> region = regionService.queryRegionWithModule(request.getModule());
            if (region.size() > 0) {
                // 创建新手课程计分规则
                String moduleRuleId = region.get(0).getRuleId();
                request.setRuleId(moduleRuleId);
                request.setName(region.get(0).getModuleName() + "新手课程");
            }
            response = productService.addSubject(request, 1);
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
     * 跳转一键复制页
     * 
     * @return
     */
    @RequestMapping("quickCopy")
    public ModelAndView quickCopy(Long productId) {
        ModelAndView modelAndView = new ModelAndView("product/productCopy");
        ProductModel productModel = productService.findSubjectById(productId);
        MajorCourseModel findCourseById = majorCourseService.findMajorByIdAndRegion(productModel.getCourseCode(),
                productModel.getModule());

        Page<CourseSubjectModel> courseSubjectModelPage = courseSubjectService.queryAllCourse();
        modelAndView.addObject("subjectList", courseSubjectModelPage.getResult());
        modelAndView.addObject("productModel", productModel);
        List<RegionModel> queryAllRegion = regionService.queryAllRegion();
        modelAndView.addObject("regionList", queryAllRegion);
        modelAndView.addObject("findCourseById", findCourseById);
        // String selectTree =
        // productCategoryService
        // .categorySelectTree(0, null, productModel.getProductCategoryId().toString());
        // modelAndView.addObject("selectTree", selectTree);
        // Page<CourseSubjectModel> courseSubjectModelPage =
        // courseSubjectService.queryAllCourse();
        // modelAndView.addObject("subjectList", courseSubjectModelPage.getResult());
        return modelAndView;
    }

    /**
     * 一键复制
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/doQuickCopy")
    public ModelAndView doQuickCopy(ProductCreateRequest request, Errors errors) {
        try {
            BaseResponse response = null;
            errors = request.validate();
            if (errors.hasErrors()) {
                response = new BaseResponse(ResultType.VALID_FAIL);
                response.setRetDesc(this.getErrMsg(errors));
            } else {
                MajorCourseModel model = majorCourseService.findMajorByIdAndRegion(request.getCourseCode(),
                        request.getModule());
                if (model != null) {
                    response = productService.doCopySubject(request);
                } else {
                    response = new BaseResponse(ResultType.VALID_FAIL);
                    response.setRetDesc("课程不存在，请验证课程码");
                }
            }
            if (response.getRetCode() == 0) {
                return this.showMessage("添加成功", "", null, true);
            } else {
                return this.showMessage("添加失败", response.getRetDesc(), null, true);
            }
        } catch (Exception e) {
            LoggerUtil.error("一键复制课程出错", e);
        }
        return null;
    }

    /**
     * 添加默认规则
     * 
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/addScoreRule")
    public BaseResponse addScoreRule(Long courseId) {
        BaseResponse response = null;
        Boolean flag = false;
        try {
            if (courseId != null) {
                ProductModel findSubjectById = productService.findSubjectById(courseId);
                if (findSubjectById != null) {
                    String ruleName = findSubjectById.getName() + "规则";
                    String ruleDesc = findSubjectById.getBriefInfo();
                    ScoreRuleModel addDefaultRule = scoreRuleService.addDefaultRule(2, ruleName, ruleDesc);
                    findSubjectById.setRuleId(addDefaultRule.getId());
                    flag = productService.editSubject(findSubjectById);
                }
            }
            if (flag) {
                response = new BaseResponse(ResultType.SUCCESS);
                response.setRetDesc("添加默认规则");
            } else {
                response = new BaseResponse(ResultType.SYS_FAIL);
                response.setRetDesc("添加默认失败");
            }
        } catch (Exception e) {
            LoggerUtil.error("添加默认规则出错", e);
        }
        return response;
    }

    /**
     * 发布
     * 
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/release")
    public ModelAndView releaseProduct(Long courseId) {
        BaseResponse response = null;
        ProductModel product = null; 
        if (courseId != null) {
            // 校验机器人数量是否>=10
            List<ChallengeRobotModel> robotList = robotService.queryByProductId(courseId);
            if (robotList == null || robotList.size() < XdmsConstant.NORMAL_BINDING_ROBOTE) {
                response = new BaseResponse(ResultType.VALID_FAIL);
                response.setRetDesc("暂未添加机器人或机器人数量不够");
                return this.showMessage("发布失败", response.getRetDesc(), null, true);
            }

            // 校验题库设置
            product = productService.findSubjectById(courseId);
            if (product != null) {
                if (product.getQuestionBankSetting() == null) {
                    response = new BaseResponse(ResultType.VALID_FAIL);
                    response.setRetDesc("暂未进行题库设置");
                    return this.showMessage("发布失败", response.getRetDesc(), null, true);
                }
            }

            // 校验命题蓝图
            ExamRuleType[] examRuleType = ExamRuleType.values();
            List<QuestionBankExamRuleModel> questionBankExamRuleModels = questionBankExamRuleService
                    .findExamRulesByProductId(courseId);
            if (questionBankExamRuleModels != null && questionBankExamRuleModels.size() >= XdmsConstant.DEFAULT_EXAMRULE_NUM) {
                Map<Integer, Boolean> checkExamRuleResultMap = Maps.newHashMap();
                for (int i = 0; i < examRuleType.length; i++) {
                    checkExamRuleResultMap.put(examRuleType[i].getExamRuleTypeValue(), false);
                }
                for (QuestionBankExamRuleModel questionBankExamRuleModel : questionBankExamRuleModels) {
                    for (Map.Entry<Integer,Boolean> entry : checkExamRuleResultMap.entrySet()) {
                        if (questionBankExamRuleModel.getExamTypeId() == (Integer) entry.getKey()) {
                            entry.setValue(true);
                        }
                    }
                }
                if (checkExamRuleResultMap.containsValue(false)) {
                    response = new BaseResponse(ResultType.VALID_FAIL);
                    response.setRetDesc("缺少默认命题蓝图");
                    return this.showMessage("发布失败", response.getRetDesc(), null, true);
                }
            }else {
                response = new BaseResponse(ResultType.VALID_FAIL);
                response.setRetDesc("请确认已包含所有的默认命题蓝图");
                return this.showMessage("发布失败", response.getRetDesc(), null, true);
            }
            
            // 校验期末测试
            Page<FinalExamModel> examPage = finalExamService.queryAllRecord(courseId);
            if (examPage == null || examPage.getResult() == null || examPage.getResult().size() < 3) {
                response = new BaseResponse(ResultType.VALID_FAIL);
                response.setRetDesc("期末测试数量小于3");
                return this.showMessage("发布失败", response.getRetDesc(), null, true);
            }

            // 校验标准任务
            List<MissionVo> standardMissionList = productItemService.queryAllItemMission(courseId);
            if (standardMissionList != null) {
                standardMissionList.addAll(finalExamService.queryAllRecordMission(courseId));
            } else {
                standardMissionList = finalExamService.queryAllRecordMission(courseId);
            }
            if (standardMissionList == null || standardMissionList.size() < 1) {
                response = new BaseResponse(ResultType.VALID_FAIL);
                response.setRetDesc("无标准任务");
                return this.showMessage("发布失败", response.getRetDesc(), null, true);
            }

            // 校验动态任务
            Page<MissionModel> missionlistPage = missionService.missionTableTree(null, null, null,
                    String.valueOf(courseId));
            if (missionlistPage == null || missionlistPage.getResult() == null
                    || missionlistPage.getResult().size() < 1) {
                response = new BaseResponse(ResultType.VALID_FAIL);
                response.setRetDesc("无动态任务");
                return this.showMessage("发布失败", response.getRetDesc(), null, true);
            }

            // 校验每一节下面是否有封面及资源，chapterItem表示节结点，resourceItem表示资源结点
            Map<Long, List<ProductItemModel>> chapterAndResourceMap = productItemService
                    .queryProductItemByCourseId(courseId);
            if (chapterAndResourceMap == null || chapterAndResourceMap.isEmpty()) {
                response = new BaseResponse(ResultType.VALID_FAIL);
                response.setRetDesc("请添加章节信息");
                return this.showMessage("发布失败", response.getRetDesc(), null, true);
            }
            for (Map.Entry<Long,List<ProductItemModel>> entry : chapterAndResourceMap.entrySet()) {
                Long id = (Long) entry.getKey();
                ProductItemModel chapterItem = productItemService.findItemById(id);
                if (chapterItem != null) {
                    ProductItemModel parent = productItemService.findItemById(chapterItem.getParentId());
                    if (parent != null) {
                        String locationName = parent.getChapterId() + chapterItem.getChapterId();
                        // 校验封面
                        if (chapterItem.getPictureUrl() == null || chapterItem.getPictureUrl() == "") {
                            response = new BaseResponse(ResultType.VALID_FAIL);
                            response.setRetDesc(locationName + "无封面");
                            return this.showMessage("发布失败", response.getRetDesc(), null, true);
                        }
                        // 校验资源
                        List<ProductItemModel> resourceList = (List<ProductItemModel>)entry.getValue();
                        boolean checkResourceResult = false;
                        for (ProductItemModel resourceItem : resourceList) {
                            if (resourceItem.getResourceType() == 8 || resourceItem.getResourceType() == 4) {
                                checkResourceResult = true;
                            }
                        }
                        if (!checkResourceResult) {
                            response = new BaseResponse(ResultType.VALID_FAIL);
                            response.setRetDesc(locationName + "请至少包含一个微课或者h5");
                            return this.showMessage("发布失败", response.getRetDesc(), null, true);
                        }
                    }
                }
            }
        }
        if(product != null) {
            product.setShowStatus(XdmsConstant.VISIBLEY);
            productService.editSubject(product);
            return this.showMessage("发布成功", null, null, true);
        }else {
            return this.showMessage("发布失败", null, null, true);
        }
    }
}
