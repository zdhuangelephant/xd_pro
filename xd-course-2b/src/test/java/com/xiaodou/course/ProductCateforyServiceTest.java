package com.xiaodou.course;

import java.text.ParseException;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.course.model.product.ProductCategoryModel;
import com.xiaodou.course.service.product.ProductCategoryService;
import com.xiaodou.course.service.product.ProductExamService;
import com.xiaodou.course.service.product.ProductService;
import com.xiaodou.course.service.product.UserLearnRecordService;
import com.xiaodou.course.web.request.BaseRequest;
import com.xiaodou.course.web.request.product.LearnRecordRequest;
import com.xiaodou.course.web.request.product.ProcuctExamDetailRequest;
import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.course.web.response.product.HomeResponse;
import com.xiaodou.course.web.response.product.ProductExamDetailResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:conf/core/course-center-servlet.xml"})
public class ProductCateforyServiceTest {

    @Resource
    ProductCategoryService productCategoryService;

    @Resource
    ProductService productService;

    @Resource
    UserLearnRecordService userLearnRecordService;


    @Resource
    ProductExamService productExamService;

    // @Test
    public void findCategoryByAppCode() {
        String typeCode = "01A0203 ";
        String module = "3";
        ProductCategoryModel c = productCategoryService.findCategoryByAppCode(typeCode, module);
        System.out.println(FastJsonUtil.toJson(c));
    }

    @Test
    
    public void home() {
        BaseRequest pojo = new BaseRequest();
        pojo.setMajorId("4");
        pojo.setModule("2");
        pojo.setTypeCode("01B0810");
        pojo.setUid("15663479");
        HomeResponse a = productService.home(pojo);
        System.out.println(FastJsonUtil.toJson(a));
    }

    @Test
    @Ignore
    public void a() throws ParseException {
        LearnRecordRequest recordRequest = new LearnRecordRequest();
        recordRequest.setChapterId("13959180");
        recordRequest.setCourseId("13959173");
        recordRequest.setItemId("13959176");
        recordRequest.setLearnTime(111);
        recordRequest.setLearnType("21");
        recordRequest.setMajorId("");
        recordRequest.setModule("2");
        recordRequest.setTypeCode("01B0810");
        recordRequest.setUid("15663408");
        BaseResponse response = userLearnRecordService.addLearnRecord(recordRequest);
        System.out.println(response.toJsonString());
    }

    @Test
    @Ignore
    public void getExamDetail() {
        ProcuctExamDetailRequest pojo = new ProcuctExamDetailRequest();
        pojo.setMajorId("4");
        pojo.setModule("2");
        pojo.setTypeCode("01B0810");
        pojo.setUid("15663479");
        ProductExamDetailResponse response = productExamService.getExamDetail(pojo);
        System.out.println(response.toJsonString());
    }
}
