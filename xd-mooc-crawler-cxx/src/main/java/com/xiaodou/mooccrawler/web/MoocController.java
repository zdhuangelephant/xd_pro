package com.xiaodou.mooccrawler.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.mooccrawler.domain.MoocItem;
import com.xiaodou.mooccrawler.holder.MoocCourseHolder;
import com.xiaodou.mooccrawler.holder.MoocCourseHolder.MoocCourseStatistic;
import com.xiaodou.mooccrawler.request.CourseInfoPojo;
import com.xiaodou.mooccrawler.request.ItemInfoPojo;
import com.xiaodou.mooccrawler.request.ResourceInfoPojo;
import com.xiaodou.mooccrawler.service.MoocService;

@Controller("moocController")
public class MoocController {

  @Resource
  MoocService moocService;

  @RequestMapping(value = "/create_course_info", method = RequestMethod.POST)
  @ResponseBody
  public String createCourseInfo(String courseInfo) {
    if (StringUtils.isJsonNotBlank(courseInfo)) {
      CourseInfoPojo pojo = FastJsonUtil.fromJson(courseInfo, CourseInfoPojo.class);
      moocService.createCourseInfo(pojo);
    }
    return "success";
  }

  @RequestMapping(value = "/finish_item_info", method = RequestMethod.POST)
  @ResponseBody
  public String finishItemInfo(String itemInfo) {
    if (StringUtils.isJsonNotBlank(itemInfo)) {
      ItemInfoPojo pojo = FastJsonUtil.fromJson(itemInfo, ItemInfoPojo.class);
      return moocService.finishItemInfo(pojo);
    } else {
      MoocItem item = MoocCourseHolder.renew();
      if (null == item) {
        return "Finish!";
      } else {
        return item.getHref();
      }
    }
  }

  @RequestMapping(value = "/upload_resource_info", method = RequestMethod.POST)
  @ResponseBody
  public String uploadResourceInfo(String resourceInfo) {
    if (StringUtils.isJsonNotBlank(resourceInfo)) {
      ResourceInfoPojo pojo = FastJsonUtil.fromJson(resourceInfo, ResourceInfoPojo.class);
      return moocService.uploadResourceInfo(pojo);
    } else {
      MoocItem item = MoocCourseHolder.renew();
      if (null == item) {
        return "Finish!";
      } else {
        return item.getHref();
      }
    }
  }

  @RequestMapping(value = "/statistic", method = RequestMethod.GET)
  public ModelAndView statistic() {
    return new ModelAndView("statistic");
  }

  @RequestMapping(value = "/statistic_detail", method = RequestMethod.GET)
  @ResponseBody
  public String statisticDetail() {
    StringBuilder sb = new StringBuilder(2000);
    sb.append("<table>").append(System.getProperty("line.separator"));
    sb.append(
        "<tr><td>CourseId</td><td>TotalCount</td><td>WaitCount</td><td>ProcessingCount</td><td>FinishCount</td></tr>")
        .append(System.getProperty("line.separator"));
    List<MoocCourseStatistic> statisticList = MoocCourseHolder.getStatistic();
    if (null != statisticList && !statisticList.isEmpty()) {
      for (MoocCourseStatistic statistic : statisticList) {
        sb.append("<tr><td>").append(statistic.getCourseId()).append("</td><td>")
            .append(statistic.getTotalCount()).append("</td><td>").append(statistic.getWaitCount())
            .append("</td><td>").append(statistic.getProcessingCount()).append("</td><td>")
            .append(statistic.getFinishCount()).append("</td></tr>")
            .append(System.getProperty("line.separator"));
      }
    }
    sb.append("</table>").append(System.getProperty("line.separator"));
    return sb.toString();
  }

  @RequestMapping(value = "/printSsh/{courseId}")
  public ResponseEntity<byte[]> printSsh(HttpServletRequest request,
      @PathVariable("courseId") String courseId, Model model) throws Exception {
    String script = moocService.getScript(courseId);
    // 下载文件路径
    HttpHeaders headers = new HttpHeaders();
    // 下载显示的文件名，解决中文名称乱码问题
    String downloadFielName = String.format("download_%s.sh", courseId);
    // 通知浏览器以attachment（下载方式）打开图片
    headers.setContentDispositionFormData("attachment", downloadFielName);
    // application/octet-stream ： 二进制流数据（最常见的文件下载）。
    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    return new ResponseEntity<byte[]>(script.getBytes(), headers, HttpStatus.CREATED);
  }
}
