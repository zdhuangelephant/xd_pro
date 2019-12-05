package com.xiaodou.ms.web.controller.robot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.ms.model.major.MajorDataModel;
import com.xiaodou.ms.model.product.ProductCategoryModel;
import com.xiaodou.ms.model.product.ProductModel;
import com.xiaodou.ms.model.product.RegionModel;
import com.xiaodou.ms.model.robot.ChallengeRobotModel;
import com.xiaodou.ms.model.user.UserModel;
import com.xiaodou.ms.service.major.MajorDataService;
import com.xiaodou.ms.service.product.ProductCategoryService;
import com.xiaodou.ms.service.product.ProductService;
import com.xiaodou.ms.service.product.RegionService;
import com.xiaodou.ms.service.robot.RobotService;
import com.xiaodou.ms.service.user.UserService;
import com.xiaodou.ms.web.controller.BaseController;
import com.xiaodou.ms.web.request.product.ProductCategoryQueryConditionReq;
import com.xiaodou.ms.web.request.robot.ChallengeRobotReqeuest;
import com.xiaodou.ms.web.request.user.UserReqeuest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

@Controller("robotController")
@RequestMapping("/robot")
public class RobotController extends BaseController {
  @Resource
  RobotService robotService;

  @Resource
  MajorDataService majorDataService;

  @Resource
  ProductService productService;

  @Resource
  UserService userService;

  @Resource
  ProductCategoryService productCategoryService;

  /**
   * 改造后的地域 
   *  */
  @Resource
  RegionService regionService;
  /**
   * 机器人列表
   */
  @RequestMapping("/challengeRobotList")
  public ModelAndView challengeRobotList(Integer page,String module, String majorId, String courseId) {
    try {
      page = (null == page || 0 == page) ? 1 : page;
      ModelAndView mv = new ModelAndView("/robot/challengeRobotList");
      
      ProductCategoryModel catogory;
      if (majorId!=null && majorId.trim().length()>0) {
    	  catogory = productCategoryService.findCategoryById(Long.parseLong(majorId));
    	}else{
    		catogory=null;
    	}
      
      Page<ChallengeRobotModel> pageCRM = robotService.queryAll(page,catogory==null?null:catogory.getTypeCode(), courseId,module);
      if(null != pageCRM && !CollectionUtils.isEmpty(pageCRM.getResult())) {
        for (ChallengeRobotModel ele : pageCRM.getResult()) {
          if(ele == null || ele.getModule() == null || StringUtils.isBlank(ele.getModule())) continue;
          RegionModel model = regionService.findModuleNameByModuleCode(ele.getModule());
          if(null == model || StringUtils.isBlank(model.getModuleName())) continue;
          ele.setModuleName(model.getModuleName());
        }
      }
      Map<Long, ProductModel> allProductInfo = robotService.queryAllProduct();

      ArrayList<ProductCategoryModel> totalProductCategoryLists = Lists.newArrayList();
      Map<String, ProductCategoryModel> allProductCategoryInfo =
          robotService.queryAllProductCategory();

      if (null != allProductCategoryInfo && allProductCategoryInfo.size() > 0) {
        for (ProductCategoryModel model : allProductCategoryInfo.values()) {
          MajorDataModel major = majorDataService.findMajorByIdAndRegion(model.getTypeCode(),model.getModule());
          if (major!=null) {
        	  model.setMajorName(major.getName());
		}
          totalProductCategoryLists.add(model);
        }
      }

      Map<String, MajorDataModel> allMajorDataInfos = robotService.queryAllMajorDataInfos();
      if (null != pageCRM && pageCRM.getResult().size() != 0) {
        for (ChallengeRobotModel challengeRobotModel : pageCRM.getResult()) {
          if (null == challengeRobotModel) {
            continue;
          }

          // 获取专业名 xd_major_data(专业资源) xd_course_product_category(幕享现有的专业来源于前者)
          if (allMajorDataInfos.containsKey(challengeRobotModel.getMajorId())) challengeRobotModel
              .setMajorName(allMajorDataInfos.get(challengeRobotModel.getMajorId()).getName());
          // 获取课程名 xd_course_product
          if (allProductInfo.containsKey(challengeRobotModel.getCourseId())) challengeRobotModel
              .setCourseName(allProductInfo.get(challengeRobotModel.getCourseId()).getName());
        }
      }

      // 获取当前专业下的所有的课程
      List<ProductModel> totalProductOfCategorys = null;
      if (majorId != null && majorId.trim().length()>0) {
        totalProductOfCategorys =
            robotService.queryAllProductsOfProductCategory(majorId, "needTransfer");

        //获取新手课程的courseId
        ProductModel cond = new ProductModel();
        cond.setCourseCode("00000");
        if (catogory!=null) {
        	cond.setModule(catogory.getModule());
        	Page<ProductModel> rookieCourse = productService.findListByCond(cond);
        	if(rookieCourse != null && rookieCourse.getResult().size() > 0) {
        		totalProductOfCategorys.add(rookieCourse.getResult().get(0));
        	}
		}
      }
      //查找地域列表
      List<RegionModel> queryAllRegion = regionService.queryAllRegion();
      mv.addObject("regionList",queryAllRegion );
      mv.addObject("totalProductOfCategorys", totalProductOfCategorys);
      mv.addObject("totalProductCategoryLists", totalProductCategoryLists);
      mv.addObject("challengeRobotPage", pageCRM);
      mv.addObject("majorId", majorId);
      mv.addObject("courseId", courseId);
      mv.addObject("module", module);
      return mv;
    } catch (Exception e) {
      throw e;
    }
  }
 
  @RequestMapping("/challengeRobotAdd")
  public ModelAndView challengeRobotAdd(Long userId, String majorName, String courseName) {
    ModelAndView mv = new ModelAndView("/robot/challengeRobotAdd");
    mv.addObject("userId", userId);
    ArrayList<ProductCategoryModel> totalProductCategoryLists = Lists.newArrayList();
    Map<String, ProductCategoryModel> allProductCategoryInfo =
        robotService.queryAllProductCategory();
    Assert.notEmpty(allProductCategoryInfo, "The allProductCategoryInfo must have elements");
    if (null != allProductCategoryInfo && allProductCategoryInfo.size() != 0) {
      for (ProductCategoryModel category : allProductCategoryInfo.values()) {
        if (category == null) {
          continue;
        }
        MajorDataModel majorData = majorDataService.findMajorByIdAndRegion(category.getTypeCode(),category.getModule());
        if (majorData!=null) {
        	category.setMajorName(majorData.getName());
		}
        totalProductCategoryLists.add(category);
      }
    }
    mv.addObject("totalProductCategoryLists", totalProductCategoryLists);
    
    // 查询所有的机器人儿
    List<UserModel> robots = userService.queryAllRobot();
    //查找地域列表
    List<RegionModel> queryAllRegion = regionService.queryAllRegion();
    mv.addObject("regionList",queryAllRegion );
    mv.addObject("robots", robots);
    return mv;
  }

  @Deprecated
  @RequestMapping("/challengeRobotDoAdd")
  public ModelAndView challengeRobotDoAdd(ChallengeRobotReqeuest request) {
    try {
      BaseResponse response = null;
      Errors errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
      	//将categoryId 转换成typeCode
    	ProductCategoryModel catogory = productCategoryService.findCategoryById(request.getCategoryId());
    	request.setMajorId(catogory.getTypeCode()); 
        response = robotService.doAdd(request);
      }
      if (response.getRetCode() == 0) {
        return this.showMessage("挂载成功", "", null, true);
      } else {
        return this.showMessage("挂载失败", response.getRetDesc(), null, true);
      }
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * 修改机器人
   */
  @RequestMapping("/challengeRobotEdit")
  public ModelAndView challengeRobotEdit(Long id) {
    List<ProductCategoryModel> totalProductCategoryLists = Lists.newArrayList();
    ChallengeRobotModel dbRobotChallenge = robotService.findById(id);

    // Map key=productCategoryId value=typeCode
    Map<String, ProductCategoryModel> allProductCategoryInfos =
        robotService.queryAllProductCategory();
    for (ProductCategoryModel category : allProductCategoryInfos.values()) {
      MajorDataModel majorData = majorDataService.findMajorByIdAndRegion(category.getTypeCode(),category.getModule());
      if (majorData!=null) {
    	  category.setMajorName(majorData.getName());
	  }
      totalProductCategoryLists.add(category);
      if (dbRobotChallenge.getMajorId().equals(category.getTypeCode())) {
        dbRobotChallenge.setMajorName(category.getMajorName());
      }
    }

/*	//强行将typeCode转换为categoryId，但是有问题
	Map<String, ProductCategoryModel> cleanTypeCode = robotService.queryAllProductCategory();
	for (ProductCategoryModel productCategory : cleanTypeCode.values()) {
		if(dbRobotChallenge.getMajorId().equals(productCategory.getTypeCode())) {
			dbRobotChallenge.setMajorId(productCategory.getId()+""); 
			break;
		}
	}*/
    
    ModelAndView mv = new ModelAndView("robot/challengeRobotEdit");

    List<ProductModel> productsOfProductCategory = robotService
        .queryAllProductsOfProductCategory(String.valueOf(dbRobotChallenge.getCategoryId()), "needTransfer");
    
    //获取新手课程的courseId
    if (dbRobotChallenge.getCategoryId() != null && dbRobotChallenge.getCategoryId()>0) {
    	 ProductModel cond = new ProductModel();
   	     ProductCategoryModel findCategoryById = productCategoryService.findCategoryById(dbRobotChallenge.getCategoryId());
    	 cond.setCourseCode("00000");
    	 cond.setModule(findCategoryById.getModule());
    	 Page<ProductModel> rookieCourse = productService.findListByCond(cond);
	    if(rookieCourse != null && rookieCourse.getResult().size() > 0) {
	      productsOfProductCategory.add(rookieCourse.getResult().get(0));
	    }   
	}
    
    
    mv.addObject("totalProductCategoryLists", totalProductCategoryLists);
    mv.addObject("productsOfProductCategory", productsOfProductCategory);
    mv.addObject("challengeRobot", dbRobotChallenge);
    
    //查找地域列表
    List<RegionModel> queryAllRegion = regionService.queryAllRegion();
    mv.addObject("regionList",queryAllRegion );
    

    return mv;
  }

  @RequestMapping("/challengeRobotDoEdit")
  public ModelAndView challengeRobotDoEdit(ChallengeRobotReqeuest request) {
    try {
      BaseResponse response = null;
      Errors errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
    	  
//    	ChallengeRobotModel findById = robotService.checkDumplic(request);
    	ChallengeRobotModel findById = robotService.findById(request.getId());
    	  
        if (findById != null) {
        	response = robotService.doEdit(request);
		}else{
          response = new BaseResponse(ResultType.VALID_FAIL);
//          response.setRetDesc("该机器人已经挂载过该专业下的该课程，不允许重复挂载");
          response.setRetDesc("该条数据无效、请删除该条数据后、重新挂载");
        }
      }
      if (response.getRetCode() == 0) {
        return this.showMessage("编辑成功", "", "", true);
      } else {
        return this.showMessage("编辑失败", response.getRetDesc(),
            "/robot/challengeRobotEdit?id=" + request.getId(), true);
      }
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * 刪除机器人
   * 
   * @return
   */
  @RequestMapping("/challengeRobotDelete")
  @ResponseBody
  public String challengeRobotDelete(Long id) {
    try {
      BaseResponse response = null;
      Boolean aBoolean = robotService.delete(id);

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


  @RequestMapping("/robotList")
  public ModelAndView robotList(Integer page) {
    page = (null == page || 0 == page) ? 1 : page;
    ModelAndView mv = new ModelAndView("/robot/robotList");
    Page<UserModel> allRobot = userService.queryAllRobotWithPage(page);
    mv.addObject("allRobot", allRobot);
    return mv;
  }

  @RequestMapping("/robotAdd")
  public ModelAndView robotAdd() {
    ModelAndView mv = new ModelAndView("/robot/robotAdd");
    List<RegionModel> queryAllRegion = regionService.queryAllRegion();
    mv.addObject("regionList",queryAllRegion );
    return mv;
  }

  @RequestMapping("/robotDoAdd")
  public ModelAndView robotDoAdd(UserReqeuest request) {
    try {
      BaseResponse response = null;
      Errors errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        response = robotService.robotDoAdd(request);
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
   * 修改机器人
   */
  @RequestMapping("/robotEdit")
  public ModelAndView robotEdit(Long id) {
    ModelAndView mv = new ModelAndView("robot/robotEdit");
    UserModel robot = userService.findUserById(id);
    List<RegionModel> queryAllRegion = regionService.queryAllRegion();
    mv.addObject("regionList",queryAllRegion );
    mv.addObject("robot", robot);
    return mv;
  }

  @RequestMapping("/robotDoEdit")
  public ModelAndView robotDoEdit(UserReqeuest request) {
    try {
      BaseResponse response = null;
      Errors errors = request.validate();
      if (errors.hasErrors()) {
        response = new BaseResponse(ResultType.VALID_FAIL);
        response.setRetDesc(this.getErrMsg(errors));
      } else {
        response = userService.doEdit(request);
      }
      if (response.getRetCode() == 0) {
        return this.showMessage("编辑成功", "", "", true);
      } else {
        return this.showMessage("编辑失败", response.getRetDesc(),
            "/robot/robotEdit?id=" + request.getId(), true);
      }
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * 刪除机器人
   * 
   * @return
   */
  @RequestMapping("/robotDelete")
  @ResponseBody
  public String robotDelete(Long id) {
    try {
      BaseResponse response = null;
      Boolean aBoolean = userService.delete(id);

      // 删除挂载所有的记录
      IQueryParam param = new QueryParam();
      param.addInput("userId", id);
      param.addOutputs(CommUtil.getGeneralField(ChallengeRobotModel.class));
      Boolean flag = robotService.deleteBatch(param);

      if (aBoolean && flag) {
        response = new BaseResponse(ResultType.SUCCESS);
      } else {
        response = new BaseResponse(ResultType.SYS_FAIL);
      }
      return JSON.toJSONString(response);
    } catch (Exception e) {
      throw e;
    }
  }

  
  @RequestMapping("/getAllProductCategoryOfModule")
  @ResponseBody
  public String getAllProductCategoryOfModule(String module) {
    if (StringUtils.isBlank(module)) return null;
    ProductCategoryQueryConditionReq req = new ProductCategoryQueryConditionReq();
    req.setModule(module);
    Page<ProductCategoryModel> catPage = productCategoryService.queryAllCategory(req, null, null, 1);
    if(null != catPage && catPage.getResult().size() > 0)
      return FastJsonUtil.toJson(catPage.getResult());
    return null;
  }
  

  @RequestMapping("/getAllProductsOfProductCategory")
  @ResponseBody
  public String getAllProductsOfProductCategory(Long userId, String typeCode,String module,Integer needModule) {
    if (StringUtils.isBlank(typeCode)) return null;
    if (StringUtils.isBlank(module) && needModule==0) return null;//从list来的直接返回
    // 获取到categoryId
    List<ProductModel> products =
        robotService.queryAllProductsOfProductCategory(typeCode, "needTransfer");
  //获取新手课程的courseId
    ProductModel cond = new ProductModel();
    cond.setCourseCode("00000");
    cond.setModule(module);
    Page<ProductModel> rookieCourse = productService.findListByCourseCode(cond);
    if(rookieCourse != null && rookieCourse.getResult().size() > 0) {
      products.add(rookieCourse.getResult().get(0));
    }
    return FastJsonUtil.toJson(products);
  }
  
  @RequestMapping("/getAllCategoryOfModule")
  @ResponseBody
  public String getAllCategoryOfModule(Integer module) {
    if (module==null){
    	return null;
    }
    Page<ProductCategoryModel> cascadeQueryChannelByModule 
    		= productCategoryService.cascadeQueryChannelByModule(module);
		return FastJsonUtil.toJson(cascadeQueryChannelByModule.getResult());
  }
  
  
}
