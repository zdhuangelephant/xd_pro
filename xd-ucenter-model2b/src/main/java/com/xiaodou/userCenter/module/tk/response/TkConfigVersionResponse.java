package com.xiaodou.userCenter.module.tk.response;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.response.ConfigResponse;
import com.xiaodou.userCenter.response.resultype.UcenterResType;

public class TkConfigVersionResponse extends
		ConfigResponse<TkConfigVersionResponse.ConfigVersionData> {

	public TkConfigVersionResponse(ResultType type) {
		super(type);
	}
	public TkConfigVersionResponse(UcenterResType type){
		super(type);
	}
	
	public static class ConfigVersionData{
		/** iosVersion IOS版本号 */
	    private String iosVersion;
	    /** iosDownloadUrl ios下载地址 */
	    private String iosDownloadUrl;
	    /** androidVersion Android版本号 */
	    private String androidVersion;
	    /** androidDownloadUrl android下载地址 */
	    private String androidDownloadUrl;
	    /** thirdlogin 三方登录平台 */
	    private String thirdlogin;
	    /** shareplateform 分享平台 */
	    private String shareplateform;
	    /** blankNotice 系统公告和活动*/
	    private String blankNotice;
		public String getIosVersion() {
			return iosVersion;
		}
		public void setIosVersion(String iosVersion) {
			this.iosVersion = iosVersion;
		}
		public String getIosDownloadUrl() {
			return iosDownloadUrl;
		}
		public void setIosDownloadUrl(String iosDownloadUrl) {
			this.iosDownloadUrl = iosDownloadUrl;
		}
		public String getAndroidVersion() {
			return androidVersion;
		}
		public void setAndroidVersion(String androidVersion) {
			this.androidVersion = androidVersion;
		}
		public String getAndroidDownloadUrl() {
			return androidDownloadUrl;
		}
		public void setAndroidDownloadUrl(String androidDownloadUrl) {
			this.androidDownloadUrl = androidDownloadUrl;
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
		public String getBlankNotice() {
			return blankNotice;
		}
		public void setBlankNotice(String blankNotice) {
			this.blankNotice = blankNotice;
		}
	}
}
