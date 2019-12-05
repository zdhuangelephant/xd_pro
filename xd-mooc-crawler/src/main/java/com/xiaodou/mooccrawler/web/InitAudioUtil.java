package com.xiaodou.mooccrawler.web;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.mooccrawler.dao.ResourceInfoDao;
import com.xiaodou.mooccrawler.dao.TaskModelDao;
import com.xiaodou.mooccrawler.domain.ResourceInfo;
import com.xiaodou.mooccrawler.domain.TaskModel;
import com.xiaodou.mooccrawler.service.FileUtilService;
import com.xiaodou.mooccrawler.service.QiniuMediaUtilService;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.util.SpringWebContextHolder;

public class InitAudioUtil implements Runnable {
    public static long currentTime=System.currentTimeMillis();
	boolean flag = true;
	@Override
	public void run() {
		// TODO Auto-generated method stub
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
				if (flag && resourceInfo.getResourceName().substring(0, 2).equals("视频")) {
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
							currentTime=System.currentTimeMillis();
						}
					}
				}
			}
		}
	}

}
