package com.xiaodou.userCenter.request;

import com.xiaodou.summer.validator.annotion.LegalValue.LegalValueList;

public class UserBaseInfo extends BaseRequest {

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 头像
	 */
	private String portrait;

	/**
	 * 年龄
	 */
	private Integer age;

	/**
	 * 地址
	 */
	private String address;
	/**
	 * 性别
	 */
	@LegalValueList({ "1", "2" })
	private Integer gender;
	
	private String coin; // 金币
	
	public UserBaseInfo(UserBaseInfo info) {
		if (null != info) {
			nickName = info.getNickName();
			portrait = info.getPortrait();
			age = info.getAge();
			address = info.getAddress();
			gender = info.getGender();
		}
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

  public String getCoin() {
    return coin;
  }

  public void setCoin(String coin) {
    this.coin = coin;
  }

}
