package com.xiaodou.userCenter.module.tk.response;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.module.jz.response.JzConfigResponse.Advertisement;
import com.xiaodou.userCenter.response.BlankNoticeResponse;
import com.xiaodou.userCenter.response.ConfigResponse;
import com.xiaodou.userCenter.response.resultype.UcenterResType;

public class TkConfigResponse extends ConfigResponse<TkConfigResponse.ConfigData> {

	public TkConfigResponse(ResultType type) {
		super(type);
	}

	public TkConfigResponse(UcenterResType type) {
		super(type);
	}
	
	public static class ConfigData{
		/** advertisement 广告 */
		private List<Advertisement> advertisement = Lists.newArrayList();
		/** thirdlogin 三方登录 */
		private List<String> thirdlogin = Lists.newArrayList();
		/** shareplateform 分享平台 */
		private List<String> shareplateform = Lists.newArrayList();
		/** blankNotice 系统公告和活动 */
		private BlankNotice blankNotice;
		public List<Advertisement> getAdvertisement() {
			return advertisement;
		}
		public void setAdvertisement(List<Advertisement> advertisement) {
			this.advertisement = advertisement;
		}
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
		public void setBlankNotice(BlankNoticeResponse blankNoticeResponse) {
			BlankNotice blankNotice = new BlankNotice();
			this.blankNotice = blankNotice.setBlankNotice(blankNoticeResponse);
		}
	}
	
	/**
	 * @name @see com.xiaodou.userCenter.module.jz.response.Advertisement.java
	 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
	 * 
	 * @author <a href="mailto:lidehong@corp.51xiaodou.com">lidehong</a>
	 * @description 首页弹出公告实体类
	 * @date 2015年12月21日
	 * @version 1.0
	 */
	public static class BlankNotice {
		// 展示方式：0 每次都展示，1 只展示一次，2 不展示
		private String type;
		// 展示地址 http://
		private String showUrl;
		// 跳转地址 http:// app://
		private String jumpUrl;

		public BlankNotice() {
			super();
		}
		
		public BlankNotice(String type, String showUrl, String jumpUrl) {
			super();
			this.type = type;
			this.showUrl = showUrl;
			this.jumpUrl = jumpUrl;
		}

		public BlankNotice setBlankNotice(BlankNoticeResponse blankNoticeResponse) {
			return new BlankNotice(blankNoticeResponse.getType().toString(),
					blankNoticeResponse.getShowUrl(),
					blankNoticeResponse.getJumpUrl());
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getShowUrl() {
			return showUrl;
		}

		public void setShowUrl(String showUrl) {
			this.showUrl = showUrl;
		}

		public String getJumpUrl() {
			return jumpUrl;
		}

		public void setJumpUrl(String jumpUrl) {
			this.jumpUrl = jumpUrl;
		}

	}
}
