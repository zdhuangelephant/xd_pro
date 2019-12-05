package com.xiaodou.ms.service.major;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.ms.dao.major.MajorCourseDao;
import com.xiaodou.ms.model.major.MajorCourseInfo;
import com.xiaodou.ms.model.major.MajorCourseModel;
import com.xiaodou.ms.model.major.MajorDataModel;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

/**
 * Created by zhouhuan on 16/7/1.
 */
@Service("majorCourseService")
public class MajorCourseService {

  @Resource
  MajorCourseDao majorCourseDao;


  /**
   * 查询
   * 
   * @param id
   * @author zhouhaun
   * @return
   */
  @Deprecated
  public MajorCourseModel findCourseById(String id) {
    MajorCourseModel cond = new MajorCourseModel();
    cond.setId(id);
    return majorCourseDao.findEntityById(cond);
  }

  public MajorCourseModel findMajorByIdAndRegion(String id, String regionCode) {
	    IQueryParam param = new QueryParam();
	    param.addInput("id", id);
	    param.addInput("region", regionCode);
	    param.addOutput("name", "");
	    param.addOutput("id", "");
	    param.addOutput("region", "");
	    Page<MajorCourseModel> page = majorCourseDao.findEntityListByCond(param, null);
	    return (page == null || page.getResult().size() == 0) ? null : page.getResult().get(0);
	  }
  
  /**
   * 新增课程
   * 
   * @param entity
   * @author zhouhaun
   * @return
   */
  public MajorCourseModel addCourse(MajorCourseModel entity) {
    return majorCourseDao.addEntity(entity);
  }


  /**
   * 根据JSON获取实体类
   * 
   * @param majorCourseInfo
   * @author zhouhaun
   * @return
   */
  public MajorCourseInfo getMajorCourseInfo(String majorCourseInfo) {
    if (StringUtils.isJsonNotBlank(majorCourseInfo)) {
      MajorCourseInfo mInfo = FastJsonUtil.fromJson(majorCourseInfo, MajorCourseInfo.class);
      if (null != mInfo) {
        return mInfo;
      }
    }
    return null;
  }

  public List<MajorCourseModel> searchCourse(String courseName) {
    IQueryParam param = new QueryParam();
    param.addInput("nameLike", courseName);
    param.addOutputs(CommUtil.getAllField(MajorCourseModel.class));
    Page<MajorCourseModel> coursePage = majorCourseDao.findEntityListByCond(param, null);
    if (null == coursePage || null == coursePage.getResult() || coursePage.getResult().size() == 0)
      return null;
    return coursePage.getResult();
  }
  
  
  /**
   * 在之前的基础上添加地域
 * @param courseName
 * @param region
 * @return
 */
  	public List<MajorCourseModel> searchCourseWithRegion(String courseName,String region) {
	    IQueryParam param = new QueryParam();
	    param.addInput("nameLike", courseName);
	    param.addInput("region", region);
	    param.addOutputs(CommUtil.getAllField(MajorCourseModel.class));
	    Page<MajorCourseModel> coursePage = majorCourseDao.findEntityListByCond(param, null);
	    if (null == coursePage || null == coursePage.getResult() || coursePage.getResult().size() == 0)
	      return null;
	    return coursePage.getResult();
	  }
  /**
   * @param pageNo
   * @param courseName
   * 分页查询majorCourse列表
   * @return
   */
  public Page<MajorCourseModel> searchCoursePage(Integer pageNo,String courseName,String region) {
    IQueryParam param = new QueryParam();
    Page<MajorCourseModel> page = new Page<>();
    if(pageNo == null) { 
      page = null;
    }else {
      page.setPageNo(pageNo);
      page.setPageSize(20);
    }
    param.addInput("nameLike", courseName);
    param.addInput("region", region);
    param.addOutputs(CommUtil.getAllField(MajorCourseModel.class));
    
    Page<MajorCourseModel> coursePage = majorCourseDao.findEntityListByCond(param, page);
   
    return coursePage;
  }
  
  

  public Boolean deleteById(String id) {
    MajorCourseModel entity = new MajorCourseModel();
    entity.setId(id);;
    return majorCourseDao.deleteEntityById(entity);
  }

  /**
   * @param region 地域
   * @param id  courseId
   * @return
   */
  public Page<MajorCourseModel> queryMajorCourseByRegionAndCourseId(String region, String id) {
    IQueryParam param = new QueryParam();
    param.addInput("region", region);
    param.addInput("id", id);
    param.addOutput("id","");
    param.addOutput("name","");
    return majorCourseDao.findEntityListByCond(param, null);
  }
}
