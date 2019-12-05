package com.xiaodou.ms.service.product;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.ms.dao.product.ModuleSlideDao;
import com.xiaodou.ms.model.product.ModuleSlideModel;
import com.xiaodou.ms.web.request.product.SlideAddRequest;
import com.xiaodou.ms.web.request.product.SlideEditRequest;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyp on 15/8/18.
 */
@Service("moduleSlideService")
public class ModuleSlideService {

	@Resource
	ModuleSlideDao moduleSlideDao;

	/**
	 * 根据Id查找
	 * 
	 * @param id
	 * @return
	 */
	public ModuleSlideModel findById(Integer id) {
		ModuleSlideModel slideModel = new ModuleSlideModel();
		slideModel.setId(id);
		return moduleSlideDao.findEntityById(slideModel);
	}

	/**
	 * 列表
	 * 
	 * @param moduleId
	 * @return
	 */
	public List<ModuleSlideModel> slideList(Integer moduleId) {
		Map<String, Object> cond = new HashMap<>();
		if (null != moduleId && moduleId != 0)
			cond.put("moduleId", moduleId);

		Map<String, Object> output = new HashMap<>();
		output.put("id", "");
		output.put("description", "");
		output.put("imageUrl", "");
		output.put("linkUrl", "");
		output.put("listOrder", "");
		output.put("moduleId", "");
		output.put("moduleName", "");
		Page<ModuleSlideModel> moduleSlideModelPage = moduleSlideDao.queryListByCond0(cond, output, null);
		return moduleSlideModelPage.getResult();
	}

	/**
	 * 添加幻灯片
	 * 
	 * @param slide
	 * @return
	 */
	public ModuleSlideModel addSlide(ModuleSlideModel slide) {
		return moduleSlideDao.addEntity(slide);
	}

	/**
	 * 添加幻灯片
	 * 
	 * @param request
	 * @return
	 */
	public ModuleSlideModel addSlide(SlideAddRequest request) {
		ArrayList<Integer> newArrayList = Lists.newArrayList();
		ModuleSlideModel slide = new ModuleSlideModel();
		if (null != request && StringUtils.isNotBlank(request.getCoalition())) {
			String[] split = request.getCoalition().split("&");
			slide.setModuleId(Integer.parseInt(split[0]));
			IQueryParam param = new QueryParam();
			param.addInput("moduleId", slide.getModuleId());
			param.addOutput("moduleName", "");
			Page<ModuleSlideModel> db_entity = moduleSlideDao.findEntityListByCond(param, null);
			if (db_entity != null && db_entity.getResult().size() != 0) {
				for (ModuleSlideModel msm : db_entity.getResult()) {
					if (msm == null)
						continue;
					if (!msm.getModuleName().equals(split[1])) {
						newArrayList.add(msm.getId());
					}
				}
			}
			if (newArrayList.size() > 0) {
				ModuleSlideModel msm = new ModuleSlideModel();
				msm.setModuleName(split[1]);
				HashMap<String, Object> input = Maps.newHashMap();
				input.put("moduleId", slide.getModuleId());
				moduleSlideDao.updateEntityByCond(input, msm);
			}
			slide.setModuleName(split[1]);
		}
		slide.setImageUrl(request.getImageUrl());
		slide.setCreateTime(new Timestamp(System.currentTimeMillis()));
		slide.setDescription(request.getDescription());
		slide.setLinkUrl(request.getLinkUrl());
		// slide.setModuleId(request.getModuleId());
		return this.addSlide(slide);
	}

	/**
	 * 编辑幻灯片
	 * 
	 * @param slide·
	 * @return
	 */
	public Boolean editSlide(ModuleSlideModel slide) {
		Map<String, Object> cond = new HashMap<>();
		cond.put("id", slide.getId());
		return moduleSlideDao.updateEntity(cond, slide);
	}

	/**
	 * 编辑幻灯片
	 * 
	 * @param request
	 * @return
	 */
	public Boolean editSlide(SlideEditRequest request) {
		ModuleSlideModel slide = new ModuleSlideModel();
		String coalition = request.getCoalition();

		ModuleSlideModel entity = new ModuleSlideModel();
		entity.setId(request.getId());
		ModuleSlideModel db_entity = moduleSlideDao.findEntityById(entity);

		// 修改
		if (StringUtils.isNotBlank(coalition) && coalition.contains("&") && db_entity != null) {
			String[] split = coalition.split("&");
			try {
				if (split[0].equals(db_entity.getModuleId() + "")) {
					if (!split[1].equals(db_entity.getModuleName() + "")) {
						// 说明要修改名称
						slide.setModuleName(split[1]);
						slide.setListOrder(request.getListOrder());
						slide.setLinkUrl(request.getLinkUrl());
						slide.setDescription(request.getDescription());
						slide.setImageUrl(request.getImageUrl());
						Map<String, Object> param = Maps.newHashMap();
						param.put("moduleId", db_entity.getModuleId());
						return moduleSlideDao.updateEntityByCond(param, slide);
					} else {
						// moduleId和moduleName都相等
						slide.setId(request.getId());
						slide.setListOrder(request.getListOrder());
						slide.setLinkUrl(request.getLinkUrl());
						slide.setDescription(request.getDescription());
						slide.setImageUrl(request.getImageUrl());
						HashMap<String, Integer> cond = Maps.newHashMap();
						cond.put("id", request.getId());
						return moduleSlideDao.updateEntity(cond, slide);
//						return moduleSlideDao.updateEntityById(slide);
					}
				} else {
					// 如果不等的话，说明是要进行添加操作
					slide.setCreateTime(new Timestamp(System.currentTimeMillis()));
					slide.setDescription(request.getDescription());
					slide.setImageUrl(request.getImageUrl());
					slide.setLinkUrl(request.getLinkUrl());
					slide.setListOrder(request.getListOrder());
					slide.setModuleId(Integer.parseInt(split[0]));
					slide.setModuleName(split[1]);
					return moduleSlideDao.addEntity(slide) != null ? true : false;
				}
			} catch (Exception e) {
				LoggerUtil.error("修改或者新增发生错误", e);
			}
		}
		return null;
	}

	/**
	 * 删除幻灯片
	 * 
	 * @param id
	 * @return
	 */
	public Boolean deleteSlide(Integer id) {
		Map<String, Object> cond = new HashMap<>();
		cond.put("id", id);
		return moduleSlideDao.deleteEntity(cond);
	}

	public List<ModuleSlideModel> getUniqueMoudle() {
		IQueryParam param = new QueryParam();
		return moduleSlideDao.findUniqueModule(param).getResult();
	}


}
