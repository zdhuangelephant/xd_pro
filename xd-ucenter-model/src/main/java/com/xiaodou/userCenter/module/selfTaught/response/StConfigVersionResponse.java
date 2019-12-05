package com.xiaodou.userCenter.module.selfTaught.response;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.response.ConfigResponse;
import com.xiaodou.userCenter.response.resultype.UcenterResType;

public class StConfigVersionResponse extends
		ConfigResponse<StConfigVersionResponse.ConfigVersionData> {

	public StConfigVersionResponse(ResultType type) {
		super(type);
	}

	public StConfigVersionResponse(UcenterResType type) {
		super(type);
	}

	/**
	 * @description 小逗自考配置文件版本数据类
	 * @version 1.0
	 */
	public static class ConfigVersionData {
		/** major 专业信息 */
		private String major;

		/** thirdlogin 三方登录平台 */
		private String thirdlogin;
		/** shareplateform 分享平台 */
		private String shareplateform;
		/** blankNotice 系统公告和活动 */
		private String blankNotice;

		public String getMajor() {
			return major;
		}

		public void setMajor(String major) {
			this.major = major;
		}

		public String getBlankNotice() {
			return blankNotice;
		}

		public void setBlankNotice(String blankNotice) {
			this.blankNotice = blankNotice;
		}

		public String getThirdlogin() {
			return thirdlogin;
		}

		public void setThirdlogin(String thirdlogin) {
			this.thirdlogin = thirdlogin;
		}

		public String getShareplateform() {
			return shareplateform;
		}

		public void setShareplateform(String shareplateform) {
			this.shareplateform = shareplateform;
		}

	}

}
