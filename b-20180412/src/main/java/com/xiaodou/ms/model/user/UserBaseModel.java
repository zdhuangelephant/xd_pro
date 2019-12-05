package com.xiaodou.ms.model.user;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;
import com.xiaodou.ms.model.feedback.UserFeedBack;

import lombok.Data;

@Data
public class UserBaseModel {
    @GeneralField
    @Column( isMajor = true,   persistent = true, autoIncrement = true)
    private Integer id;
    
    @GeneralField
    @Column(    persistent = true )
    private String module;

    @GeneralField
    @Column(    persistent = true  )
    private String xdUniqueId;
    
    @GeneralField
    @Column(    persistent = true   )
    private String salt;    
    
    @GeneralField
    @Column(    persistent = true   )
    private String password;

    @GeneralField
    @Column(    persistent = true   )
    private String telephone;
    
    @GeneralField
    @Column(    persistent = true   )
    private String qq;

    @GeneralField
    @Column(    persistent = true   )
    private String weixin;

    @GeneralField
    @Column(    persistent = true   )
    private String weibo;
    
    @GeneralField
    @Column(    persistent = true   )
    private String tourist;
    
    @GeneralField
    @Column(    persistent = true   )
    private String main_account;

    
    public static void main(String[] args) {
      MybatisXmlTool.getInstance(UserBaseModel.class, "xd_base_user",
          "F:/newspace/xd-ms2b/src/main/resources/conf/mybatis/").buildXml();
      System.out.println("done");
    }

}  

    
