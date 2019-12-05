package com.xiaodou.st.dashboard.web.controller.grade;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xiaodou.st.dashboard.constants.Constants;
import com.xiaodou.st.dashboard.domain.apply.ApplyDO;
import com.xiaodou.st.dashboard.domain.grade.ClassDO;
import com.xiaodou.st.dashboard.service.admin.AdminService;
import com.xiaodou.st.dashboard.service.admin.AdminUser;
import com.xiaodou.st.dashboard.service.apply.ApplyService;
import com.xiaodou.st.dashboard.service.grade.ClassService;
import com.xiaodou.st.dashboard.service.student.StudentService;
import com.xiaodou.st.dashboard.web.controller.BaseController;

@Controller("classController")
@RequestMapping("/class")
public class ClassController extends BaseController {

  @Resource
  ClassService classService;
  @Resource
  StudentService studentService;
  @Resource
  AdminService adminService;
  @Resource
  ApplyService applyService;

  /**
   * 
   * @description 查询登录用户，第三级单位的班级
   * @author 李德洪
   * @Date 2017年3月30日
   * @return
   */
  @RequestMapping("/class_list")
  public ModelAndView classList() {
    ModelAndView mv = new ModelAndView("/class/classList");
    AdminUser adminUser = super.getAdminUser();
    mv.addObject("adminUser", adminUser);
    List<ClassDO> classList = classService.listClass();
    for (ClassDO classDO : classList) {
      if (classDO.getStudentCount() > 0) {
        ApplyDO applyDO = new ApplyDO();
        applyDO.setClassId(classDO.getId());
        List<ApplyDO> applyList = applyService.listApply(applyDO);
        if (null == applyList || applyList.isEmpty()) {
          classDO.setBatchDel(Constants.CAN_BATCH_DEL);
        }
      }
    }
    mv.addObject("listClass", classList);
    // 班级管理员列表
    mv.addObject("listClassAdmin", adminService.listClassAdmin(adminUser.getUnitId()));
    return mv;
  }

  // @RequestMapping("/to_save_class")
  // public ModelAndView toSaveClass() {
  // ModelAndView mv = new ModelAndView("/class/classDetail");
  // return mv;
  // }

  @RequestMapping("/save_class")
  @ResponseBody()
  public String saveClass(ClassDO classDO) {
    return classService.saveClass(classDO);
  }

  // @RequestMapping("/to_update_class")
  // public ModelAndView toUpdateClass(Long classId) {
  // ModelAndView mv = new ModelAndView("/class/classDetail");
  // mv.addObject("class", classService.getClass(classId));
  // return mv;
  // }

  @RequestMapping("/update_class")
  @ResponseBody()
  public String updateClass(ClassDO classDO) {
    return classService.updateClass(classDO);
  }

  @RequestMapping("/remove_class")
  @ResponseBody()
  public String removeClass(Long classId) {
    return classService.removeClass(classId);
  }
}
