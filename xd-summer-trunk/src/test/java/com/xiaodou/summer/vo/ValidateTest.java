package com.xiaodou.summer.vo;

import java.util.List;

import org.springframework.validation.Errors;

import com.google.common.collect.Lists;
import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.validator.annotion.NotEmpty.AllNotEmptyList;
import com.xiaodou.summer.validator.annotion.NotEmpty.OrNotEmptyList;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;
import com.xiaodou.summer.web.BaseController;

public class ValidateTest extends BaseController {

  public static class Test extends BaseValidatorPojo {
    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getAge() {
      return age;
    }

    public void setAge(String age) {
      this.age = age;
    }

    public String getDegree() {
      return degree;
    }

    public void setDegree(String degree) {
      this.degree = degree;
    }

    public String getGender() {
      return gender;
    }

    public void setGender(String gender) {
      this.gender = gender;
    }

    public String getAddress() {
      return address;
    }

    public void setAddress(String address) {
      this.address = address;
    }

    public String getNosign() {
      return nosign;
    }

    public void setNosign(String nosign) {
      this.nosign = nosign;
    }

    public List<String> getTest() {
      return test;
    }

    public void setTest(List<String> test) {
      this.test = test;
    }

    @NotEmpty
    private String id;
    @NotEmpty("id")
    private String name;
    @OrNotEmptyList(value = {@NotEmpty("id"), @NotEmpty("name")})
    private String age;
    @AllNotEmptyList(value = {@NotEmpty(field = "id", value = "13"), @NotEmpty("name")})
    private String degree;
    @NotEmpty(field = "name", value = "null")
    private String gender;
    @OrNotEmptyList(value = {@NotEmpty(field = "id", value = "124"),
        @NotEmpty(field = "name", value = "zhaodan")})
    private String address;
    @NotEmpty
    private List<String> test = Lists.newArrayList();
    @NotEmpty(field = "test", value = "null")
    private String nosign;
  }

  @org.junit.Test
  public void testValidate() {
    Test test = new Test();
    test.setId("12");
    test.setName("222");
    Errors errors = test.validate();
    if (errors.hasErrors()) {
      System.out.println(getErrMsg(errors));
    }
  }

}
