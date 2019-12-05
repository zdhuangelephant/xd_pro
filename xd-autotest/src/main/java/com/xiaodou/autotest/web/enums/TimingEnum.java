package com.xiaodou.autotest.web.enums;

/**
 * @name @see com.xiaodou.server.mapi.engine.enums.ActionEnum.java
 * @CopyRright (c) 2017 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2017年8月21日
 * @description 定时任务枚举
 * @version 1.0
 */
public enum TimingEnum {
	/** NOTASK  */
	NOTASK("0","NOTASK","无任务"),
	/** EVERYHOUR  */
	EVERYHOUR("1","EVERYHOUR","每小时"),
	/** EVERYDAY  */
	EVERYDAY("2","EVERYDAY","每天"),
	/** EVERYWEEK  */
	EVERYWEEK("3","EVERYWEEK","每周");
	TimingEnum(String code, String name,String desc) {
		this.code = code;
		this.name = name;
		this.desc = desc;
	}

	private String code;
	private String name;
	private String desc;
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    public static TimingEnum getInstance(String code){
    	 for (TimingEnum tim : TimingEnum.values()) {  
             if (tim.getCode().equals(code)) {  
                 return tim;  
             }  
         }  
        return null;  
    }
}
