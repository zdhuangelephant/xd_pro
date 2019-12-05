package com.xiaodou.moofficial.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.xiaodou.moofficial.entity.AuthorModel;
import com.xiaodou.moofficial.entity.ForumModel;
import com.xiaodou.moofficial.service.AuthorService;
import com.xiaodou.moofficial.service.ForumService;
import com.xiaodou.moofficial.utils.RdUtil;
import com.xiaodou.moofficial.web.response.BaseResponse;
import com.xiaodou.moofficial.web.response.ResultType;

@Controller("forumController")
@RequestMapping("/")
public class ForumController {
	public static final Integer LOAD_MORN = 10;
	public static final String MSG = "ah-oh, 您查看的资源已经不存在了";
	public static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Resource
	ForumService forumService;

	@Resource
	AuthorService authurService;

	@RequestMapping("/")
	public ModelAndView index() {
		ModelAndView index = new ModelAndView("index");
		return index;
	}

	@RequestMapping("/moshare")
	public ModelAndView moapp() {
		ModelAndView index = new ModelAndView("mo-app");
		return index;
	}

	@RequestMapping("/mocooperate")
	public ModelAndView cooperate() {
		ModelAndView index = new ModelAndView("mo-cooperate");
		return index;
	}

	@RequestMapping("/moknowledge")
	public ModelAndView knowledgeIndex(Integer pageNo) {
		pageNo = null != pageNo && pageNo > 0 ? pageNo : 1;
		ModelAndView index = new ModelAndView("mo-knowledge");
		List<ForumModel> forumList = forumService.findForumList(null, 1,
				LOAD_MORN * pageNo);
		for (ForumModel forum : forumList) {
			AuthorModel author = authurService.findAuthorById(Long
					.parseLong(forum.getAuthorId()));
			forum.setAuthor(author);
			forum.setHtmlContent(RdUtil.getHtmlContent(forum.getForumContent(),
					70));
		}
		index.addObject("forumList", forumList);
		index.addObject("pageNo", pageNo);
		return index;
	}

	@RequestMapping("/moknowledge/{forumId}.html")
	public ModelAndView knowledgeDetail(@PathVariable String forumId) {
		ModelAndView index = new ModelAndView("mo-knowledgeDetail");
		ForumModel forum = forumService.findForumById(forumId);
		if (null == forum) {
			index.addObject("msg", MSG);
			return index;
		}
		forum.setHtmlContent(RdUtil.getHtmlContent(forum.getForumContent(), 120));
		index.addObject("forum", forum);
		return index;
	}

	@RequestMapping("/loadMore")
	@ResponseBody
	public String doLoadMoreForum(Integer pageNo) {
		List<ForumModel> forumList = forumService.findForumList(null, pageNo,
				LOAD_MORN);
		StringBuilder sb = new StringBuilder();
		for (ForumModel forumModel : forumList) {
			AuthorModel author = authurService.findAuthorById(Long
					.parseLong(forumModel.getAuthorId()));
			forumModel.setAuthor(author);
			String temp = "";
			if (forumModel.getAuthor() == null) {
				temp = "幕享公开课";
			} else {
				temp = forumModel.getAuthor().getName();
			}
			forumModel.setHtmlContent(RdUtil.getHtmlContent(
					forumModel.getForumContent(), 70));
			String appendData = "";
			appendData += "<div class=\"k-list-append\">" ;
			
			appendData += "<div class=\"k-list-box k-list-box2\">";
			appendData += "<div class=\"k-list-top\">";
			appendData += "<div class=\"k-list-top-img\">";
			appendData += "<a href=\"javascript:;\" onclick=\"toForumDeatil("
					+ forumModel.getForumId() + ");\"><img src=\""
					+ forumModel.getForumCover() + "\" ></a>";

			appendData += "<div class=\"k-lable\">";
			if (forumModel.getForumType().equals("1")) {
				appendData += "	<img src=\"/resources/images/k-course.png\">";
			} else if (forumModel.getForumType().equals("2")) {
				appendData += "	<img src=\"/resources/images/k-share.png\">";

			}
			appendData += "</div>";

			appendData += "</div>";
			appendData += "<div class=\"k-list-top-text\">";
			appendData += "<h3>";
			appendData += "<a href=\"javascript:;\" onclick=\"toForumDeatil("
					+ forumModel.getForumId() + ");\">"
					+ forumModel.getForumTitle() + "</a>";
			appendData += "</h3>";
			appendData += "<p><a href=\"javascript:;\" onclick=\"toForumDeatil("+forumModel.getForumId()+");\">" + forumModel.getHtmlContent() + "</a></p>";
			appendData += "</div>";
			appendData += "</div>";
			appendData += "<div class=\"k-list-bottom\">";
			appendData += "<p>作者：" + temp + " 发布时间："
					+ sdf.format(forumModel.getCreateTime()) + "</p>";
			appendData += "<span>赞（" + forumModel.getForumPraiserNum()
					+ "）</span>";
			appendData += "</div>";
			appendData += "</div>";
			appendData += "</div>";

			sb.append(appendData);
		}
		return sb.toString();
	}

	@RequestMapping("/thumbs")
	@ResponseBody
	public String doThumbs(Long id, Integer oper) {
		ForumModel toModel = new ForumModel();
		ForumModel forumModel = forumService.findForumById(String.valueOf(id));
		toModel.setForumId(forumModel.getForumId());
		if (forumModel.getForumPraiserNum() <= 100) {
			toModel.setForumPraiserNum(100);
		}
		if (oper != 1) {
			toModel.setForumPraiserNum(forumModel.getForumPraiserNum() - 1);
		} else {
			toModel.setForumPraiserNum(forumModel.getForumPraiserNum() + 1);
		}
		Boolean res = forumService.updateForum(toModel);
		if (res) {
			return JSON.toJSONString(new BaseResponse(ResultType.SUCCESS));
		} else {
			return JSON.toJSONString(new BaseResponse(ResultType.SYS_FAIL));
		}
	}
	
}
