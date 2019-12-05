package com.xiaodou.server.mapi.response.ucenter;

import com.xiaodou.server.mapi.response.selftaught.BaseResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class BlankNoticeResponse extends BaseResponse {
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
	public Short getType() {
		return type;
	}
	public void setType(Short type) {
		this.type = type;
	}
	public String getJumpUrl() {
		return jumpUrl;
	}
	public void setJumpUrl(String jumpUrl) {
		this.jumpUrl = jumpUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Short getIsVisible() {
		return isVisible;
	}
	public void setIsVisible(Short isVisible) {
		this.isVisible = isVisible;
	}
	
}
