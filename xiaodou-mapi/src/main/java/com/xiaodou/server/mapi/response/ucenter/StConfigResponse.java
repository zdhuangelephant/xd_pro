package com.xiaodou.server.mapi.response.ucenter;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.response.ucenter.vo.RegionVO;
import com.xiaodou.server.mapi.vo.ucenter.model.ProductCategoryUtilModel;
import com.xiaodou.summer.vo.out.ResultType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class StConfigResponse extends BaseResponse {

	public StConfigResponse(ResultType type) {
		super(type);
	}

	public StConfigResponse() {
	}

	private ConfigData data = new ConfigData();

	public ConfigData getData() {
		return data;
	}

	public void setData(ConfigData data) {
		this.data = data;
	}

	@Data
	public static class ConfigData {
		/** thirdlogin 三方登录 */
		private List<String> thirdlogin = Lists.newArrayList();
		/** shareplateform 分享平台 */
		private List<String> shareplateform = Lists.newArrayList();
		/** blankNotice 系统公告和活动 */
		private BlankNotice blankNotice = new BlankNotice();
		/* feedBackCategoryList 意见反馈 类型列表 */
		private List<String> feedbackCategoryList = Lists.newArrayList();
		private SysCount sysCount = new SysCount();

		/** benkeList 本科专业列表 */
		private List<ProductCategoryUtilModel> benkeList = Lists.newArrayList();
		/** zhuankeList 专科专业列表 */
		private List<ProductCategoryUtilModel> zhuankeList = Lists
				.newArrayList();
		private String benkeCount = Integer.toString(0);
		private String zhuankeCount = Integer.toString(0);

		/** regionList 地域列表 */
		private List<RegionVO> regionList = Lists.newArrayList();
		private String regionCount = String.valueOf(0);
		public List<String> getThirdlogin() {
			return thirdlogin;
		}
		public void setThirdlogin(List<String> thirdlogin) {
			this.thirdlogin = thirdlogin;
		}
		public List<String> getShareplateform() {
			return shareplateform;
		}
		public void setShareplateform(List<String> shareplateform) {
			this.shareplateform = shareplateform;
		}
		public BlankNotice getBlankNotice() {
			return blankNotice;
		}
		public void setBlankNotice(BlankNotice blankNotice) {
			this.blankNotice = blankNotice;
		}
		public List<String> getFeedbackCategoryList() {
			return feedbackCategoryList;
		}
		public void setFeedbackCategoryList(List<String> feedbackCategoryList) {
			this.feedbackCategoryList = feedbackCategoryList;
		}
		public SysCount getSysCount() {
			return sysCount;
		}
		public void setSysCount(SysCount sysCount) {
			this.sysCount = sysCount;
		}
		public List<ProductCategoryUtilModel> getBenkeList() {
			return benkeList;
		}
		public void setBenkeList(List<ProductCategoryUtilModel> benkeList) {
			this.benkeList = benkeList;
		}
		public List<ProductCategoryUtilModel> getZhuankeList() {
			return zhuankeList;
		}
		public void setZhuankeList(List<ProductCategoryUtilModel> zhuankeList) {
			this.zhuankeList = zhuankeList;
		}
		public String getBenkeCount() {
			return benkeCount;
		}
		public void setBenkeCount(String benkeCount) {
			this.benkeCount = benkeCount;
		}
		public String getZhuankeCount() {
			return zhuankeCount;
		}
		public void setZhuankeCount(String zhuankeCount) {
			this.zhuankeCount = zhuankeCount;
		}
		public List<RegionVO> getRegionList() {
			return regionList;
		}
		public void setRegionList(List<RegionVO> regionList) {
			this.regionList = regionList;
		}
		public String getRegionCount() {
			return regionCount;
		}
		public void setRegionCount(String regionCount) {
			this.regionCount = regionCount;
		}
	}

	/**
	 * @author <a href="mailto:lidehong@corp.51xiaodou.com">lidehong</a>
	 * @description 首页弹出公告实体类
	 * @version 1.0
	 */
	@Data
	public static class BlankNotice {
		/** type 显示频次 0:每日首次， 1：每次启动 2：一次性 */
		private Short type;
		/** jumpUrl 跳转地址 http:// app:// */
		private String jumpUrl;
		/** title 开屏通知标题 */
		private String title;
		/** image 展示地址 http:// */
		private String image;
		/** isVisible 是否显示 */
		private Short isVisible;

		public BlankNotice() {
			super();
		}

		public BlankNotice(Short type, String jumpUrl, String title,
				String image, Short isVisible) {
			super();
			this.type = type;
			this.jumpUrl = jumpUrl;
			this.title = title;
			this.image = image;
			this.isVisible = isVisible;
		}

		public BlankNotice initBlankNotice(BlankNoticeResponse response) {
			return new BlankNotice(response.getType(), response.getJumpUrl(),
					response.getTitle(), response.getImage(),
					response.getIsVisible());
		}
	}

	@Data
	public static class SysCount {
		/** noticeCount 未读通知数量 */
		private String noticeCount = Integer.toString(0);
		/** sysMesCount 未读系统消息数量 */
		private String sysMesCount = Integer.toString(0);

		public String getNoticeCount() {
			return noticeCount;
		}

		public void setNoticeCount(String noticeCount) {
			this.noticeCount = noticeCount;
		}

		public String getSysMesCount() {
			return sysMesCount;
		}

		public void setSysMesCount(String sysMesCount) {
			this.sysMesCount = sysMesCount;
		}

	}
}
