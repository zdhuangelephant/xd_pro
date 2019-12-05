package com.xiaodou.ms.service.selftaught;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.ms.dao.selftaught.MajorDao;
import com.xiaodou.ms.model.selftaught.MajorModel;
import com.xiaodou.summer.dao.pagination.Page;

@Service("majorService")
public class MajorService {
	@Resource(name = "majorDao")
	MajorDao majorDao;

	/**
	 * 获取所有专业
	 * 
	 * @return
	 */
	public List<MajorModel> queryMajor() {
		Map<String, Object> inputArgument = new HashMap<String, Object>();
		Map<String, Object> outputField = new HashMap<>();
		outputField.put("majorId", "");
		outputField.put("majorName", "");
		outputField.put("majorImage", "");
		outputField.put("degree", "");
		outputField.put("module", "");
		Page<MajorModel> majorList = majorDao.queryListByCond0(inputArgument, outputField, null);
		return majorList.getResult();
	}

	/**
	 * 查询推送消息
	 * 
	 * @param majorId
	 */
	public MajorModel findMajorId(String majorId) {
		MajorModel majorModel = new MajorModel();
		majorModel.setMajorId(majorId);
		return majorDao.findEntityById(majorModel);
	}

	/**
	 * 更新专业
	 * 
	 * @param entity
	 * @return
	 */
	public Boolean editMajor(MajorModel entity) {
		Map<String, Object> cond = new HashMap<>();
		cond.put("majorId", entity.getMajorId());
		return majorDao.updateEntity(cond, entity);
	}

	/**
	 * 增加专业
	 * 
	 * @param majorModel
	 */
	public void addMajor(MajorModel majorModel) {
		this.majorDao.addEntity(majorModel);
	}

	/**
	 * 删除专业
	 * 
	 * @param majorId
	 */
	public Boolean deleteMajor(String majorId) {
		Map<String, Object> cond = new HashMap<>();
		cond.put("majorId", majorId);
		Boolean result = majorDao.deleteEntity(cond);
		return result;
	}

}
