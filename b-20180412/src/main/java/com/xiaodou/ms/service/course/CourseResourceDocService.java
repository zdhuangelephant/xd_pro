package com.xiaodou.ms.service.course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xiaodou.ms.common.ResourceType;
import com.xiaodou.ms.dao.course.CourseResourceDocDao;
import com.xiaodou.ms.model.course.CourseChapterModel;
import com.xiaodou.ms.model.course.CourseKeywordModel;
import com.xiaodou.ms.model.course.CourseResourceDocModel;
import com.xiaodou.ms.service.product.ProductItemService;
import com.xiaodou.ms.vo.ResourceDescription;
import com.xiaodou.ms.web.request.course.ResourceDocCreateRequest;
import com.xiaodou.ms.web.request.course.ResourceDocEditRequest;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * Created by zyp on 15/4/19.
 */
@Service("courseResourceDocService")
public class CourseResourceDocService {

	@Resource
	CourseResourceDocDao courseResourceDocDao;

	@Resource
	CourseChapterService courseChapterService;

	@Resource
	CourseKeywordResourceService courseKeywordResourceService;

	@Resource
	ProductItemService productItemService;

	/**
	 * 更新关键词列表
	 * 
	 * @param resourceId
	 * @param keywordList
	 * @return
	 */
	public Boolean updateKeyPoint(Long resourceId,
			List<CourseKeywordModel> keywordList) {
		Map<String, Object> cond = new HashMap<>();
		cond.put("id", resourceId);
		CourseResourceDocModel docModel = new CourseResourceDocModel();
		docModel.setKeyPoint(JSON.toJSONString(keywordList));
		return courseResourceDocDao.updateEntity(cond, docModel);
	}

	/**
	 * 级联获取文档列表
	 * 
	 * @param courseId
	 * @param chapterId
	 * @return
	 */
	public Page<CourseResourceDocModel> cascadeQueryDocByChapterId(Long courseId, Long chapterId) {
		List<CourseChapterModel> courseChapterModels = courseChapterService
				.queryAllChildChapter(courseId, chapterId);
		List<Long> ids = new ArrayList<>();
		for (CourseChapterModel courseChapterModel : courseChapterModels) {
			ids.add(courseChapterModel.getId());
		}
		ids.add(chapterId);
		Map<String, Object> cond = new HashMap<>();
		cond.put("chapterIds", ids);
		Page<CourseResourceDocModel> courseResourceDocModelPage = courseResourceDocDao
				.cascadeQueryDoc(cond);
		return courseResourceDocModelPage;
	}

	/**
	 * 新增文档
	 * 
	 * @param entity
	 * @return
	 */
	public CourseResourceDocModel addDoc(CourseResourceDocModel entity) {
		return courseResourceDocDao.addEntity(entity);
	}

	/**
	 * 新增文档
	 * 
	 * @param request
	 * @return
	 */
	public CourseResourceDocModel addDoc(ResourceDocCreateRequest request) {
		CourseResourceDocModel courseResourceDocModel = new CourseResourceDocModel();
		courseResourceDocModel.setDetail(request.getDetail());
		courseResourceDocModel.setName(request.getName());
		courseResourceDocModel.setChapterId(request.getChapterId());
		courseResourceDocModel.setUrl(request.getUrl());
		courseResourceDocModel.setFileUrl(request.getFileUrl());
		courseResourceDocModel.setCourseId(request.getCourseId());
		CourseResourceDocModel result = this.addDoc(courseResourceDocModel);
		return result;
	}

	/**
	 * 删除文档
	 * 
	 * @param id
	 * @return
	 */
	public Boolean deleteResourceDoc(Integer id) {
		Map<String, Object> cond = new HashMap<>();
		cond.put("id", id);
		return courseResourceDocDao.deleteEntity(cond);
	}

	/**
	 * 更新文档
	 * 
	 * @param entity
	 * @return
	 */
	public Boolean editDoc(CourseResourceDocModel entity) {
		Map<String, Object> cond = new HashMap<>();
		cond.put("id", entity.getId());
		ResourceDescription description = new ResourceDescription();
		description.setUrl(entity.getUrl());
		description.setFileUrl(entity.getFileUrl());
		productItemService.updateItemResource(entity.getId(),
				ResourceType.DOC.getTypeId(), JSON.toJSONString(description));
		return courseResourceDocDao.updateEntity(cond, entity);
	}

	/**
	 * 更新文档
	 * 
	 * @param request
	 * @return
	 */
	public Boolean editDoc(ResourceDocEditRequest request) {
		CourseResourceDocModel courseResourceDocModel = new CourseResourceDocModel();
		courseResourceDocModel.setId(request.getId());
		courseResourceDocModel.setUrl(request.getUrl());
		courseResourceDocModel.setName(request.getName());
		courseResourceDocModel.setChapterId(request.getChapterId());
		courseResourceDocModel.setDetail(request.getDetail());
		courseResourceDocModel.setFileUrl(request.getFileUrl());
		return this.editDoc(courseResourceDocModel);
	}

	/**
	 * 根据主键查询
	 * 
	 * @param catId
	 * @return
	 */
	public CourseResourceDocModel findResourceDocById(Long catId) {
		CourseResourceDocModel entity = new CourseResourceDocModel();
		entity.setId(catId);
		return courseResourceDocDao.findEntityById(entity);
	}

	/**
	 * 根据主键查询
	 * 
	 * @param itemId
	 * @return
	 */
	public CourseResourceDocModel findFirstResourceDocById(Long itemId) {
		Map<String, Object> cond = new HashMap<>();
		cond.put("chapterId", itemId);
		Map<String, Object> output = new HashMap<>();
		output.put("id", "");
		output.put("courseId", "");
		output.put("chapterId", "");
		output.put("name", "");
		output.put("url", "");
		output.put("fileUrl", "");
		output.put("detail", "");
		output.put("keyPoint", "");
		output.put("chapterName", "");
		output.put("status", "");
		List<CourseResourceDocModel> list = courseResourceDocDao
		    .queryListByCond0(cond, output, null).getResult();
		if (list != null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	
	   /**
     * 根据chapterId查询
     * 
     * @param itemId
     * @return
     */
    public List<CourseResourceDocModel> findAllResourceDocById(Long itemId) {
        Map<String, Object> cond = new HashMap<>();
        cond.put("chapterId", itemId);
        Map<String, Object> output = new HashMap<>();
        output.put("id", "");
        output.put("courseId", "");
        output.put("chapterId", "");
        output.put("name", "");
        output.put("url", "");
        output.put("fileUrl", "");
        output.put("detail", "");
        output.put("keyPoint", "");
        output.put("chapterName", "");
        output.put("status", "");
        List<CourseResourceDocModel> list = courseResourceDocDao
            .queryListByCond0(cond, output, null).getResult();
        
        return list;
    }
	
}
