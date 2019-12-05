package com.xiaodou.rdonline.web.controller.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.rdonline.constants.Constants;
import com.xiaodou.rdonline.domain.activity.ActivityModel;
import com.xiaodou.rdonline.domain.student.StudentModel;
import com.xiaodou.rdonline.domain.tutormajor.TutorMajorModel;
import com.xiaodou.rdonline.service.activity.ActivityService;
import com.xiaodou.rdonline.service.student.StudentService;
import com.xiaodou.rdonline.service.tutormajor.TutorMajorService;

/**
 * @author zdh:
 * @date 2017年6月10日
 *
 */
@Controller("loadMoreController")
@RequestMapping("/loadMore")
public class LoadMoreController {
	@Resource
	TutorMajorService tutorMajorService;

	@Resource
	ActivityService activityService;

	@Resource
	StudentService studentService;

	private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
	
	@RequestMapping("/activity")
	@ResponseBody
	public String doLoadMoreActivity(Long id, Short typeId, Integer pageNo) {
		List<ActivityModel> activityList = activityService.findActivityList(null, pageNo, Constants.LOAD_MORN);
		StringBuilder sb = new StringBuilder();
		for (ActivityModel activityModel : activityList) {
			String appendData = "<a class=\"dis-a\" href='javascript:;' onclick=\"toActivityDeatil("
					+ activityModel.getId() + ");\">";
			//
			appendData += "<div class=\"cont-left-cont\">";
			appendData += "<div class=\"cont-main\">";
			appendData += "<div class=\"cont-main-img\"><img src=\"/resources/images/phone.png\" width=\"210\" height=\"116\"></div>";
			appendData += "<div class=\"cont-main-sub\">";
			appendData += "<h3>" + activityModel.getTitle() + "</h3>";
			appendData += "<p>" + activityModel.getSubtitle() + "</p>";
			appendData += "</div>";
			appendData += "</div>";
			appendData += "<div class=\"cont-info\">";
			appendData += "<div class=\"cont-info-left\">";
			appendData += "<span>活动时间：" + sdf.format(activityModel.getActivityTime()) + "</span> <span>活动地点："
					+ activityModel.getActivityPlace() + "</span>";
			appendData += "</div>";
			appendData += "<div class=\"cont-info-right\">";
			appendData += " <div class=\"act-btn\">查看详情</div>";
			appendData += " </div>";
			appendData += "</div>";
			appendData += "</div>";
			appendData += " </a> ";
			sb.append(appendData);
		}
		return sb.toString();
	}

	/**
	 * 专业
	 * 
	 * @param id
	 * @param typeId
	 * @param pageNo
	 * @return
	 */
	@RequestMapping("/tutorMajor")
	@ResponseBody
	
	public String doLoadMoreTutorMajor(Long id, String typeId, Integer pageNo) {
		List<TutorMajorModel> tutorMajorList = null;
		if (typeId.equals("1_tutor")) {
			// 导师
			tutorMajorList = tutorMajorService.findTutorMajorList((short) 1, pageNo, Constants.LOAD_MORN);
		} else if (typeId.equals("1_major")) {
			// 专业
			tutorMajorList = tutorMajorService.findTutorMajorList((short) 2, pageNo, Constants.LOAD_MORN);
		}
		StringBuilder sb = new StringBuilder();
		for (TutorMajorModel tutorMajor : tutorMajorList) {
			String appendData = "";
			if (typeId.equals("1_major")) {
				appendData += "<a class=\"dis-a\" href='javascript:;' onclick=\"toMajorDeatil(" + tutorMajor.getId()
						+ ");\">";
				appendData += "<div class=\"cont-left-cont\">";
				appendData += "<div class=\"cont-main\">";
				appendData += "<div class=\"cont-main-img\"><img src=" + tutorMajor.getImage()+ " width=\"210\" height=\"116\"></div>";
				appendData += "<div class=\"cont-main-sub\">";
				appendData += "<h3>" + tutorMajor.getTitle() + "</h3>";
				appendData += "<p>" + tutorMajor.getSubtitle() + "</p>";
				appendData += "</div>";
				appendData += "</div>";
				appendData += "<div class=\"cont-info\">";
				appendData += "<div class=\"cont-info-left\">";
				appendData += "<span>作者：" + tutorMajor.getAuthor() + "</span> <span>发布时间:";
				appendData += "" + sdf.format(tutorMajor.getPublishTime()) + "</span>";
				appendData += "</div>";
				appendData += "<div class=\"cont-info-right\">";
				appendData += "<span>赞(" + tutorMajor.getThumbNums() + ")</span>";
				appendData += "</div>";
				appendData += "<div class=\"cont-info-right\"></div>";
				appendData += "</div>";
				appendData += "</div>";
				appendData += "</a>";

			} else if (typeId.equals("1_tutor")) {
				appendData += "<a class=\"dis-a\" href='javascript:;' onclick=\"toTutorDeatil(" + tutorMajor.getId()
				+ ");\">";
				appendData += "<div class=\"tour-cont-left\">";
				appendData += "<div class=\"tour-left-img\">";
				appendData += "<img src=" + tutorMajor.getImage() + " width=\"165\" height=\"220\">";
				appendData += "</div>";

				appendData += "<div class=\"tour-info-right\">";

				appendData += "<div class=\"cont-main-sub tour-main-sub\">";
				appendData += "<h3>" + tutorMajor.getAuthor() + " ：" + tutorMajor.getTitle() + " </h3>";
				appendData += "<p>" + tutorMajor.getSubtitle() + "</p>";
				appendData += "</div>";
				appendData += "<div class=\"cont-info tour-cont-info\">";
				appendData += "<div class=\"cont-info-left tour-info-left\">";
				appendData += "<span>作者：瑞德官方</span> <span>" + sdf.format(tutorMajor.getPublishTime()) + "</span>";
				appendData += "</div>";
				appendData += "<div class=\"cont-info-right\">";
				appendData += "<span>赞(" + tutorMajor.getThumbNums() + ")</span>";
				appendData += "</div>";
				appendData += "</div>";
				appendData += "</div>";
				appendData += "</div>";
				appendData += "</a>";
			}
			sb.append(appendData);
		}
		return sb.toString();
	}
}
