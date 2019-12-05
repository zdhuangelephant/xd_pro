package com.xiaodou.userCenter.module.selfTaught.service.facade;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.userCenter.dao.BlankNoticeDao;
import com.xiaodou.userCenter.dao.CourseDao;
import com.xiaodou.userCenter.dao.MajorDao;
import com.xiaodou.userCenter.model.BlankNoticeModel;
import com.xiaodou.userCenter.model.CourseModel;
import com.xiaodou.userCenter.model.MajorModel;
@Service("stUcenterModelServiceFacade")
public class UcenterModelServiceFacadeImpl implements
		IUcenterModelServiceFacade {

	@Resource
	private BlankNoticeDao blankNoticeDao;
	@Resource
	private MajorDao majorDao;
	@Resource
	private CourseDao courseDao;

	@Override
	public List<BlankNoticeModel> queryBlackNoticeList(
			Map<String, Object> queryCond, Map<String, Object> allInfo) {
		return blankNoticeDao.queryList(queryCond, allInfo);
	}

	@Override
	public List<MajorModel> queryMajorList(Map<String, Object> queryCond,
			Map<String, Object> allInfo) {
		return majorDao.queryList(queryCond, allInfo);
	}

	@Override
	public List<CourseModel> queryCourseList(Map<String, Object> queryCond,
			Map<String, Object> allInfo) {
		return courseDao.queryList(queryCond, allInfo);
	}

}
