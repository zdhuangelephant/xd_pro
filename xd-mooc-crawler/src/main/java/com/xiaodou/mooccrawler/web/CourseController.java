package com.xiaodou.mooccrawler.web;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.RandomUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.mooccrawler.dao.ChapterInfoDao;
import com.xiaodou.mooccrawler.dao.ResourceInfoDao;
import com.xiaodou.mooccrawler.dao.TaskModelDao;
import com.xiaodou.mooccrawler.domain.ChapterInfo;
import com.xiaodou.mooccrawler.domain.ResourceInfo;
import com.xiaodou.mooccrawler.domain.TaskModel;
import com.xiaodou.mooccrawler.domain.course.CourseChapterModel;
import com.xiaodou.mooccrawler.domain.course.CourseResourceAudioModel;
import com.xiaodou.mooccrawler.domain.course.CourseResourceDocModel;
import com.xiaodou.mooccrawler.domain.course.CourseSubjectModel;
import com.xiaodou.mooccrawler.service.AudioDetailUtil;
import com.xiaodou.mooccrawler.service.FileUtilService;
import com.xiaodou.mooccrawler.service.QiniuMediaUtilService;
import com.xiaodou.mooccrawler.service.course.CourseChapterService;
import com.xiaodou.mooccrawler.service.course.CourseResourceAudioService;
import com.xiaodou.mooccrawler.service.course.CourseResourceDocService;
import com.xiaodou.mooccrawler.service.course.CourseSubjectService;
import com.xiaodou.mooccrawler.util.NumberUtil;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.util.SpringWebContextHolder;

@Controller("courseController")
public class CourseController {

	// 课程章节
	@Resource
	CourseChapterService courseChapterService;
	@Resource
	CourseSubjectService courseSubjectService;
	@Resource
	CourseResourceDocService courseResourceDocService;
	@Resource
	CourseResourceAudioService courseResourceAudioService;
	@Resource
	TaskModelDao taskModelDao;
	@Resource
	ChapterInfoDao chapterInfoDao;
	@Resource
	ResourceInfoDao resourceInfoDao;
	@Resource
	FileUtilService fileUtilService;
	@Resource
	QiniuMediaUtilService qiniuMediaUtilService;
 /*
	*//**
	 * 批量处理视频 http://resource.51xiaodou.com/12321412341235.mp3?avinfo
	 * 
	 * @return
	 * @throws InterruptedException 
	 *//*
	@RequestMapping("/initAudio")
	@ResponseBody
	public void initAudio () throws InterruptedException {  
        ExecutorService exe = Executors.newFixedThreadPool(5);
        InitAudioUtil initAudioUtil = new InitAudioUtil();
		exe.execute(initAudioUtil);   
        while (true) { 
        	if (System.currentTimeMillis() > InitAudioUtil.currentTime + 20*60*1000){  
        		exe.execute(initAudioUtil); 
            }  
            Thread.sleep(5000);  
        }  
    }  
	*/
	/**
	 * 批量处理视频 http://resource.51xiaodou.com/12321412341235.mp3?avinfo
	 * 
	 * @return
	 * @throws InterruptedException 
	 */
	@RequestMapping("/initAudio2")
	@ResponseBody
	public String initAudio2()  {
		TaskModelDao taskModelDao = SpringWebContextHolder.getBean("taskModelDao");
		FileUtilService fileUtilService = SpringWebContextHolder.getBean("fileUtilService");
		QiniuMediaUtilService qiniuMediaUtilService = SpringWebContextHolder.getBean("qiniuMediaUtilService");
		ResourceInfoDao resourceInfoDao = SpringWebContextHolder.getBean("resourceInfoDao");
	    IQueryParam param = new QueryParam();
		List<TaskModel> taskList = taskModelDao.findEntityListByCond(param,null).getResult();
		for (TaskModel task : taskList) {
			param.addInput("courseId", task.getCourseId());
			param.addInput("trueUrl", "1");
			List<ResourceInfo> resourceList = resourceInfoDao.findEntityListByCond(param, null).getResult();
			for (ResourceInfo resourceInfo : resourceList) {
				if (resourceInfo.getResourceName().substring(0, 2).equals("视频")) {
					String baseUrl = "http://resource.51xiaodou.com/";
					String fileName = UUID.randomUUID().toString() + ".mp4";
					if (fileUtilService.downLoadByUrl(resourceInfo.getResourceUrl(), fileName)) {
						String finalFileName = qiniuMediaUtilService.qiNiuTransCodeToAny(fileName, "mp3");
						if (StringUtils.isNotBlank(finalFileName)) {
							ResourceInfo re=new ResourceInfo();
							re.setTrueUrl(baseUrl + finalFileName);
							Map<String, Object> cond = Maps.newHashMap();
							cond.put("cid", resourceInfo.getCid());
							resourceInfoDao.updateEntityByCond(cond,re);
						}
					}
				}
			}		
      }
		return "success"; 
	}
	
	
	/**
	 * 入口
	 * 
	 * @return
	 */
	@RequestMapping("/init")
	@ResponseBody
	public String init() {
		IQueryParam param = new QueryParam();
		List<TaskModel> taskList = taskModelDao.findEntityListByCond(param,null).getResult();
/*		param.addInput("courseId", "XJTU-46017");
		taskList = taskModelDao.findEntityListByCond(param,null).getResult();
		this.addSubject(taskList);	
		param.addInput("courseId", "SDU-199002");
		taskList = taskModelDao.findEntityListByCond(param,null).getResult();
		this.addSubject(taskList);	
		param.addInput("courseId", "XJTU-1001596004");
		taskList = taskModelDao.findEntityListByCond(param,null).getResult();
		this.addSubject(taskList);	
		param.addInput("courseId", "TONGJI-53004");
		taskList = taskModelDao.findEntityListByCond(param,null).getResult();
		this.addSubject(taskList);	
		param.addInput("courseId", "TONGJI-284001");
		taskList = taskModelDao.findEntityListByCond(param,null).getResult();
		this.addSubject(taskList);	
		param.addInput("courseId", "TONGJI-217012");
		taskList = taskModelDao.findEntityListByCond(param,null).getResult();
		this.addSubject(taskList);	
		param.addInput("courseId", "TONGJI-1001569002");
		taskList = taskModelDao.findEntityListByCond(param,null).getResult();
		this.addSubject(taskList);	
		param.addInput("courseId", "HZIC-1001701011");
		taskList = taskModelDao.findEntityListByCond(param,null).getResult();
		this.addSubject(taskList);	
		param.addInput("courseId", "NTHU-453006");
		taskList = taskModelDao.findEntityListByCond(param,null).getResult();
		this.addSubject(taskList);	
		param.addInput("courseId", "SWJTU-95001");
		taskList = taskModelDao.findEntityListByCond(param,null).getResult();
		this.addSubject(taskList);	
		param.addInput("courseId", "NUDT-17003");
		taskList = taskModelDao.findEntityListByCond(param,null).getResult();
		this.addSubject(taskList);	
		param.addInput("courseId", "SWJTU-410003");
		taskList = taskModelDao.findEntityListByCond(param,null).getResult();
		this.addSubject(taskList);	
		param.addInput("courseId", "JXUFE-1002056012");
		taskList = taskModelDao.findEntityListByCond(param,null).getResult();
		this.addSubject(taskList);	
		param.addInput("courseId", "XIDIAN-1001960018");
		taskList = taskModelDao.findEntityListByCond(param,null).getResult();
		this.addSubject(taskList);	
		param.addInput("courseId", "NEU-1001638003");
		taskList = taskModelDao.findEntityListByCond(param,null).getResult();
		this.addSubject(taskList);	
		param.addInput("courseId", "ZJU-232005");
		taskList = taskModelDao.findEntityListByCond(param,null).getResult();
		this.addSubject(taskList);	
		param.addInput("courseId", "HIT-309001");
		taskList = taskModelDao.findEntityListByCond(param,null).getResult();
		this.addSubject(taskList);	
		param.addInput("courseId", "HZAU-1001804013");
		taskList = taskModelDao.findEntityListByCond(param,null).getResult();
		this.addSubject(taskList);	
		param.addInput("courseId", "TONGJI-481001");
		taskList = taskModelDao.findEntityListByCond(param,null).getResult();
		this.addSubject(taskList);	
		param.addInput("courseId", "HNU-1001742001");
		taskList = taskModelDao.findEntityListByCond(param,null).getResult();
		this.addSubject(taskList);	
		param.addInput("courseId", "BIT-47012");
		taskList = taskModelDao.findEntityListByCond(param,null).getResult();
		this.addSubject(taskList);	
		param.addInput("courseId", "WHU-490001");
		taskList = taskModelDao.findEntityListByCond(param,null).getResult();*/
		this.addSubject(taskList);	
		return "success";
	}

	/**
	 * 资源课程增加
	 * 
	 * @return
	 */
	public void addSubject(List<TaskModel> taskList) {
		for (TaskModel task : taskList) {
			CourseSubjectModel course = new CourseSubjectModel();
			Integer courseId = Integer.valueOf(RandomUtil.randomNumber(8));
			course.setId(courseId);
			course.setDetail(task.getCourseId());
			course.setName(task.getCourseId()+task.getCourseName());
			course.setCreateTime(new Timestamp(System.currentTimeMillis()));
			course.setCategoryId(8888);
			course.setCategoryName("抓取专业");
			CourseSubjectModel courseSubject = courseSubjectService.addSubject(course);
			this.addChapter(courseSubject);
			courseChapterService.cleanTree(courseSubject.getId());
		}
	}

	public void addChapter(CourseSubjectModel courseSubject) {
		if (courseSubject != null && courseSubject.getId() != null
				&& StringUtils.isNotBlank(courseSubject.getDetail())) {
			IQueryParam param = new QueryParam();
			param.addInput("courseId", courseSubject.getDetail());
			param.addInput("type", "chapter");
			List<ChapterInfo> chapterList = chapterInfoDao.findEntityListByCond(param, null).getResult();
			Integer chapterIndex = 0;
			for (ChapterInfo chapterInfo : chapterList) {
				chapterIndex++;
				CourseChapterModel courseChapterModel = new CourseChapterModel();
				courseChapterModel.setName(chapterInfo.getChapterName());
				courseChapterModel.setSindex("第" + NumberUtil.getString(chapterIndex)+ "章");
				courseChapterModel.setDetail("");
				courseChapterModel.setParentId(0);
				courseChapterModel.setSubjectId(courseSubject.getId());
				courseChapterModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
				CourseChapterModel courseChapter = courseChapterService.addChapter(courseChapterModel);
				this.addItem(courseSubject, courseChapter, chapterInfo);
			}
		}
	}

	public void addItem(CourseSubjectModel courseSubject,
			CourseChapterModel courseChapter, ChapterInfo chapterInfo) {
		if (courseChapter != null && courseChapter.getId() != null) {
			IQueryParam param = new QueryParam();
			param.addInput("courseId", courseSubject.getDetail());
			param.addInput("type", "item");
			param.addInput("parentId", chapterInfo.getCid());
			List<ChapterInfo> itemList = chapterInfoDao.findEntityListByCond(param, null).getResult();
			Integer itemIntex = 0;
			for (ChapterInfo itemInfo : itemList) {
				itemIntex++;
				CourseChapterModel courseChapterModel = new CourseChapterModel();
				courseChapterModel.setName(itemInfo.getChapterName());
				courseChapterModel.setSindex("第" + NumberUtil.getString(itemIntex) + "节");
				courseChapterModel.setDetail("");
				courseChapterModel.setParentId(courseChapter.getId());
				courseChapterModel.setSubjectId(courseSubject.getId());
				courseChapterModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
				CourseChapterModel courseItem = courseChapterService.addChapter(courseChapterModel);
				this.addResource(courseSubject, courseItem, itemInfo);
			}
		}
	}

	public void addResource(CourseSubjectModel courseSubject,
			CourseChapterModel courseItem, ChapterInfo itemInfo) {
		if (courseItem != null && courseItem.getId() != null) {
			IQueryParam param = new QueryParam();
			param.addInput("courseId", courseSubject.getDetail());
			param.addInput("chapterId", itemInfo.getCid());
			List<ResourceInfo> resourceList = resourceInfoDao.findEntityListByCond(param, null).getResult();
			for (ResourceInfo resourceInfo : resourceList) {
				if (resourceInfo.getResourceName().substring(0, 2).equals("文档")) {
					CourseResourceDocModel courseResourceDocModel = new CourseResourceDocModel();
					courseResourceDocModel.setDetail("");
					courseResourceDocModel.setName(resourceInfo.getResourceName());
					courseResourceDocModel.setChapterId(courseItem.getId());
					courseResourceDocModel.setUrl(resourceInfo.getResourceUrl());
					courseResourceDocModel.setFileUrl(resourceInfo.getResourceUrl());
					courseResourceDocModel.setCourseId(courseSubject.getId());
					courseResourceDocService.addDoc(courseResourceDocModel);
				} else if (resourceInfo.getResourceName().substring(0, 2).equals("视频")) {
					CourseResourceAudioModel courseResourceAudioModel = new CourseResourceAudioModel();
					courseResourceAudioModel.setDetail("");
					courseResourceAudioModel.setName(resourceInfo.getResourceName());
					courseResourceAudioModel.setChapterId(courseItem.getId());
					courseResourceAudioModel.setUrl(resourceInfo.getTrueUrl());
					AudioDetailUtil.packageAudio(resourceInfo.getTrueUrl()+"?avinfo", courseResourceAudioModel);
					courseResourceAudioModel.setCourseId(courseSubject.getId());
					courseResourceAudioService.addAudio(courseResourceAudioModel);
				}
			}
		}
	}
}
