package com.xiaodou.userCenter.response;

import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.response.resultype.UcenterResType;

/**
 * 
 * 注册response
 *
 * @author		weirong.li 
 * @version		1.0  
 * @since		JDK1.7
 */
public class RegistAccountResponse extends BaseResultInfo{
	public RegistAccountResponse(UcenterResType type) {
		super(type);
	}
  /**
   * token值
   */
  private String token;
  
  public RegistAccountResponse(ResultType type) {
    super(type);
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
  
}
