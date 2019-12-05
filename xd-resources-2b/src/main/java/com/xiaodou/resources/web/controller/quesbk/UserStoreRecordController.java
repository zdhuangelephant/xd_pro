package com.xiaodou.resources.web.controller.quesbk;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.resources.request.quesbk.StoreQuesDetailPojo;
import com.xiaodou.resources.request.quesbk.StoreQuesListPojo;
import com.xiaodou.resources.request.quesbk.StoreQuesPojo;
import com.xiaodou.resources.request.quesbk.StoreQuesRequest;
import com.xiaodou.resources.service.quesbk.QuesBaseServices;

@Controller("userStoreRecordController")
@RequestMapping("/quesbk")
public class UserStoreRecordController {
	@Resource
	QuesBaseServices quesBaseServices;

	/**
	 *  收藏题目 ·说明 收藏题目到个人收藏列表。 ·url /quesbk/store_ques
	 * 
	 * @param pojo
	 */
	@ResponseBody
	@RequestMapping("store_ques")
	public String storeQues(StoreQuesPojo pojo) {
		return quesBaseServices.storeQues(pojo).toString();
	}

	/**
	 * 获取收藏列表 ·说明 根据用户ID和课程ID获取个人收藏列表。 ·url /quesbk/store_ques_list
	 * 
	 * @param pojo
	 */
	@ResponseBody
	@RequestMapping("store_ques_list")
	public String storeQuesList(StoreQuesListPojo pojo) {
		return quesBaseServices.storeQuesList(pojo).toString();
	}

	/**
	 *  收藏详情展示 ·说明 根据用户ID/课程ID/章ID展示用户收藏详细信息。 ·url /quesbk/store_ques_detail
	 * 
	 * @param pojo
	 */
	@ResponseBody
	@RequestMapping("store_ques_detail")
	public String storeQuesDetail(StoreQuesDetailPojo pojo) {
		return quesBaseServices.storeQuesDetail(pojo).toString();
	}

	/**
	 * 更改收藏题目 更改收藏题目到个人收藏列表。。 · url /selftaught/change_store_ques
	 * 
	 * @param pojo
	 */
	@ResponseBody
	@RequestMapping("change_store_ques")
	public String changeStoreQues(StoreQuesRequest pojo) {
		return quesBaseServices.changeStoreQues(pojo).toString();
	}
}
