package com.xiaodou.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.service.QuesBaseServices;
import com.xiaodou.vo.request.RaiseWrongQuesRequest;
import com.xiaodou.vo.request.UnWrongQuesRequest;
import com.xiaodou.vo.request.WrongQuesDetailPojo;
import com.xiaodou.vo.request.WrongQuesListPojo;

@Controller("userWrongRecordController")
@RequestMapping("/quesbk")
public class UserWrongRecordController {
	@Resource
	QuesBaseServices quesBaseServices;

	/**
	 *  错题集 · 说明 获取用户错题记录集。 · url /quesbk/wrong_ques_list
	 * 
	 * @param pojo
	 */
	@ResponseBody
	@RequestMapping("wrong_ques_list")
	public String wrongQuesList(WrongQuesListPojo pojo) {
		return quesBaseServices.wrongQuesList(pojo).toString();
	}

	/**
	 *  错题详情展示 ·说明 根据题目ID展示用户错题详细信息。 ·url /quesbk/wrong_ques_detail
	 * 
	 * @param pojo
	 */
	@ResponseBody
	@RequestMapping("wrong_ques_detail")
	public String wrongQuesDetail(WrongQuesDetailPojo pojo) {
		return quesBaseServices.wrongQuesDetail(pojo).toString();
	}

	/**
	 * 修改错题状态。。 · url /selftaught/unwrong_ques
	 * 
	 * @param pojo
	 */
	@ResponseBody
	@RequestMapping("change_wrong_ques")
	public String changeWrongQues(UnWrongQuesRequest pojo) {
		return quesBaseServices.changeWrongQues(pojo).toString();
	}

	/**
	 * 举报错题题目 用户举报错误题目。。 · url /selftaught/raise_wrong_ques
	 * 
	 * @param pojo
	 */
	@ResponseBody
	@RequestMapping("raise_wrong_ques")
	public String raiseWrongQues(RaiseWrongQuesRequest pojo) {
		return quesBaseServices.insertRaiseWrongQues(pojo).toString();
	}
}
