package com.xiaodou.ms.service.robot;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.ms.constants.XdmsConstant;
import com.xiaodou.ms.dao.major.MajorDataDao;
import com.xiaodou.ms.dao.product.ProductCategoryDao;
import com.xiaodou.ms.dao.product.ProductCategoryRelationDao;
import com.xiaodou.ms.dao.product.ProductDao;
import com.xiaodou.ms.dao.robot.RobotDao;
import com.xiaodou.ms.dao.user.UserDao;
import com.xiaodou.ms.model.major.MajorDataModel;
import com.xiaodou.ms.model.product.ProductCategoryModel;
import com.xiaodou.ms.model.product.ProductCategoryRelation;
import com.xiaodou.ms.model.product.ProductModel;
import com.xiaodou.ms.model.robot.ChallengeRobotModel;
import com.xiaodou.ms.model.user.UserModel;
import com.xiaodou.ms.service.product.ProductCategoryService;
import com.xiaodou.ms.util.IDGenerator;
import com.xiaodou.ms.util.RandomUtils;
import com.xiaodou.ms.web.request.robot.ChallengeRobotReqeuest;
import com.xiaodou.ms.web.request.user.UserReqeuest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.ms.web.response.robot.ChallengeRobotResponse;
import com.xiaodou.ms.web.response.user.UserResponse;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;
import com.xiaodou.summer.dao.param.QueryParam;

@Service("robotService")
public class RobotService {
	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
	@Resource
	private RobotDao robotDao;

	@Resource
	private MajorDataDao majorDataDao;

	@Resource
	private ProductDao productDao;

	@Resource
	private UserDao userDao;

	@Resource
	private ProductCategoryDao productCategoryDao;

	@Resource
	private ProductCategoryRelationDao productCategoryRelationDao;
	
	@Resource
    ProductCategoryService productCategoryService;

	

	public Page<ChallengeRobotModel> queryAll(Integer pageNo, String majorId,String courseId,String module) {
		Page<ChallengeRobotModel> page = new Page<ChallengeRobotModel>();
		page.setPageNo(pageNo);
		page.setPageSize(Page.DEFAULT_PAGESIZE);
		IQueryParam param = new QueryParam();
		if (StringUtils.isNotBlank(majorId)) {
			param.addInput("majorId", majorId);
		}
		if (StringUtils.isNotBlank(courseId)) {
			param.addInput("courseId", courseId);
		}
		if (StringUtils.isNotBlank(module)) {
			param.addInput("module", module);
		}
		// courseId为空的则不显示
		param.addInput("courseIdNotNull", " IS NOT NULL AND course_id <> -1 ");
		param.addOutputs(CommUtil.getAllField(ChallengeRobotModel.class));
		param.addSort("updateTime", Sort.DESC);
		param.addSort("createTime", Sort.DESC);
		Page<ChallengeRobotModel> dbPages = robotDao.findEntityListByCond(param, page);
		return dbPages;
	}

	/**
	 * 获取所有可用的专业信息
	 * 
	 * @return
	 */
	/**
	 * @return
	 */
	public Map<String, ProductCategoryModel> queryAllProductCategory() {
		HashMap<String, ProductCategoryModel> allProductCategoryInfos = Maps.newHashMap();
		IQueryParam param = new QueryParam();
		Map<String, Object> allField = CommUtil.getAllField(ProductCategoryModel.class);
		allField.remove("majorInfoModel");
		allField.remove("moduleName");
		allField.remove("majorName");
		allField.remove("majorInfo");
		param.addOutputs(allField);
		Page<ProductCategoryModel> page = productCategoryDao.findEntityListByCond(param, null);
		if (page == null || page.getResult().size() == 0)
			return null;
		for (ProductCategoryModel productCategoryModel : page.getResult()) {
			if (null == productCategoryModel) {
				continue;
			}
			
			IQueryParam param0 = new QueryParam();
			param0.addInput("id", productCategoryModel.getTypeCode());
			param0.addInput("region", productCategoryModel.getModule());
            param0.addOutputs(CommUtil.getGeneralField(MajorDataModel.class));
			Page<MajorDataModel> page0 = majorDataDao.findEntityListByCond(param0, null);
			if(null != page0 && page0.getResult().size() != 0) 
			  productCategoryModel.setName(page0.getResult().get(0).getName());
			if (!allProductCategoryInfos.containsKey(productCategoryModel.getId() + "")) {
				allProductCategoryInfos.put(productCategoryModel.getId() + "", productCategoryModel);
			}
		}
		return allProductCategoryInfos;
	}

	public Map<Long, ProductModel> queryAllProduct() {
		HashMap<Long, ProductModel> allProductInfos = Maps.newHashMap();
		IQueryParam param = new QueryParam();
		Map<String, Object> allField = CommUtil.getAllField(ProductModel.class);
		allField.remove("majorCourseInfo");
		allField.remove("categoryName");
		allField.remove("relationState");
		allField.remove("relationTime");
		allField.remove("productCategoryId");
		param.addOutputs(allField);
		Page<ProductModel> page = productDao.findEntityListByCond(param, null);
		if (page == null || page.getResult().size() == 0)
			return null;
		for (ProductModel productModel : page.getResult()) {
			if (null == productModel)
				continue;
			if (!allProductInfos.containsKey(productModel.getId()))
				allProductInfos.put(productModel.getId(), productModel);
		}
		return allProductInfos;
	}

	public ChallengeRobotModel checkDumplic(ChallengeRobotReqeuest request) {
		ChallengeRobotModel entity = new ChallengeRobotModel();
		entity.setUserId(request.getUserId());
		entity.setCourseId(request.getCourseId());
		entity.setCategoryId(request.getCategoryId());
		entity.setModule(request.getModule());
		return robotDao.findEntityById(entity);
	}
	
    public ChallengeRobotModel findById(Long id) {
      ChallengeRobotModel entity = new ChallengeRobotModel();
      entity.setId(id);
      return robotDao.findEntityById(entity);
  } 

	public Boolean delete(Long id) {
		ChallengeRobotModel entity = new ChallengeRobotModel();
		entity.setId(id);
		return robotDao.deleteEntityById(entity);
	}

	public BaseResponse doAdd(ChallengeRobotReqeuest request) {
		
		BaseResponse response = new ChallengeRobotResponse(ResultType.SUCCESS);
		ChallengeRobotModel model = request.initModel();

		/*// 获取新手课程的courseId
		IQueryParam cond1 = new QueryParam();
		cond1.addInput("moduleCourse", "2");
		Page<ProductModel> rookieCourse = productDao.findEntityListByCond(cond1, null);
		if(rookieCourse != null && rookieCourse.getResult().size() > 0) {
		  model.setCourseId(rookieCourse.getResult().get(0).getId());
		}*/
		if (null != model && null == model.getCreateTime()) {
			model.setCreateTime(new Timestamp(System.currentTimeMillis()));
		}
		if (model.getTargetAbility() == null) {
			model.setTargetAbility(0);
		}
		// 判断是否已经添加过
		IQueryParam __param = new QueryParam();
		__param.addInput("userId", model.getUserId());
		__param.addInput("majorId", model.getMajorId());
		__param.addInput("courseId", model.getCourseId());
		__param.addOutputs(CommUtil.getGeneralField(ChallengeRobotModel.class));
		Page<ChallengeRobotModel> existsCourses = robotDao.findEntityListByCond(__param, null);
		if (existsCourses != null && existsCourses.getResult().size() != 0) {
			return new ChallengeRobotResponse(ResultType.DUPLICATE_MAJOR_COURSE);
		}
		
		model = robotDao.addEntity(model);
		return response;
	}

	public BaseResponse doEdit(ChallengeRobotReqeuest request) {
		BaseResponse response = new ChallengeRobotResponse(ResultType.SUCCESS);
		ChallengeRobotModel entity = request.initModel();
		robotDao.updateEntityById(entity);
		return response;
	}

	public BaseResponse robotDoAdd(UserReqeuest request) {
		BaseResponse response = new UserResponse(ResultType.SUCCESS);
		UserModel model = request.initModel();
		if (StringUtils.isBlank(model.getNickName())) {
			model.setNickName(String.format("Alexa%s", RandomUtils.generateLowerString(5)));
		}
		if (StringUtils.isBlank(model.getPortrait())) {
			model.setPortrait(XdmsConstant.PORTRAIT);
		}else {
		  model.setPortrait(request.getPortrait());
		}
		model.setGender(1 + "");
		model.setAge(23);
		model.setAddress("xiaodou-tech");
		model.setTokenTime(new Timestamp(System.currentTimeMillis()));
		model.setToken("-" + RandomUtils.generateMixString(8));
		model.setCreateTime(model.getTokenTime());
		model.setUsedDeviceId("-");
		model.setLatestDeviceIp("-");
		model.setUserType(-1);
		model.setXdUniqueId(sdf.format(new Date()));
		model.setId(Long.parseLong(IDGenerator.getSeqID()));
		model = userDao.addEntity(model);
		
		// 将数据添加到xd_challenge_robot
		ChallengeRobotModel crm = new ChallengeRobotModel();
		crm.setCreateTime(new Timestamp(System.currentTimeMillis()) );
		crm.setUserId(Long.valueOf(model.getId()));
		crm.setTargetAbility(0);
		crm.setMajorId("-1");
		crm.setCourseId(-1L);
		robotDao.addEntity(crm);
		return response;
	}

	/**
	 * 根据专业ID获取该专业下的所有课程产品信息
	 * 
	 * @param majorId
	 * @return
	 */
	public List<ProductModel> queryAllProductsOfProductCategory(String productCategoryId,String flag) {
		
//		if(StringUtils.isNotBlank(flag) && flag.equals("needTransfer")) {
//			// Map  key=productCategoryId   value=typeCode
//			Map<String, ProductCategoryModel> allProductCategoryInfos = queryAllProductCategory();
//			for (ProductCategoryModel productCategory : allProductCategoryInfos.values()) {
//				if(productCategoryId.equals(productCategory.getTypeCode())) {
//					productCategoryId = productCategory.getId()+"";
//					break;
//				}
//			}
//		}
		ArrayList<ProductModel> totalProductsOfProductCategory = Lists.newArrayList();

		IQueryParam _param = new QueryParam();
		_param.addInput("productCategoryId", productCategoryId);
		_param.addInput("relationState", 1);
		_param.addOutput("productId", "");
		Page<ProductCategoryRelation> productCategoryRelation = 
				productCategoryRelationDao.findEntityListByCond(_param, null);
		if (null != productCategoryRelation && productCategoryRelation.getResult().size() > 0) {
			for (ProductCategoryRelation relation : productCategoryRelation.getResult()) {
				ProductModel cond = new ProductModel();
				cond.setShowStatus(1);
				cond.setId(relation.getProductId());
				ProductModel product = productDao.findEntityById(cond);
				if(product == null) {continue;}
				totalProductsOfProductCategory.add(product);
			}
		}
		
		// 每个专业下都有新手课程 TODO
		return totalProductsOfProductCategory;
	}

	public Map<String, MajorDataModel> queryAllMajorDataInfos() {
		Map<String, MajorDataModel> allMajorDataInfos = Maps.newHashMap();
		IQueryParam _param = new QueryParam();
		_param.addOutput("id", "");
		_param.addOutput("name", "");
		Page<MajorDataModel> page = majorDataDao.findEntityListByCond(_param, null);
		if (page != null && page.getResult().size() != 0) {
			for (MajorDataModel majorDataModel : page.getResult()) {
				if (majorDataModel == null) {
					continue;
				}
				if (!allMajorDataInfos.containsKey(majorDataModel.getId())) {
					allMajorDataInfos.put(majorDataModel.getId(), majorDataModel);
				}
			}
		}
		return allMajorDataInfos;
	}

	public List<ChallengeRobotModel> queryByProductId(Long proudctId) {
		if ( proudctId == null ) return null;
		IQueryParam param = new QueryParam();
		param.addInput("courseId", proudctId);
		Map<String, Object> allField = CommUtil.getAllField(ChallengeRobotModel.class);
		allField.remove("majorName");
		allField.remove("courseName");
		param.addOutputs(allField);
		Page<ChallengeRobotModel> page = robotDao.findEntityListByCond(param, null);
		return page == null ? null: page.getResult();
	}

	public ChallengeRobotModel queryChallengeRecordByUserIdAndMajorIdAndCourseId(ChallengeRobotReqeuest request) {
		if(request.getUserId() == null || StringUtils.isBlank(request.getMajorId()) || request.getCourseId() == null) {
			throw new IllegalArgumentException("userId, majorId, courseId 均不许为空或null");
		}
		IQueryParam param = new QueryParam();
		param.addInput("userId", request.getUserId());
		param.addInput("majorId", request.getMajorId());
		param.addInput("courseId", request.getCourseId());
		Map<String, Object> allField = CommUtil.getAllField(ChallengeRobotModel.class);
		allField.remove("majorName");
		allField.remove("courseName");
		param.addOutputs(allField);
		Page<ChallengeRobotModel> page = robotDao.findEntityListByCond(param, null);
		return (page == null || page.getResult().size() == 0) ? null: page.getResult().get(0);
	}

  public Boolean deleteBatch(IQueryParam param) {
    Page<ChallengeRobotModel> results = robotDao.findEntityListByCond(param, null);
    
    if(null != results && results.getResult().size() != 0) {
      for (ChallengeRobotModel entity : results.getResult()) {
        robotDao.deleteEntityById(entity);
      }
      return true;
    }else {
      return false;
    }
  }

}
