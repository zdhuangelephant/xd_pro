package com.xiaodou.st.dashboard.service.grade;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.st.dashboard.constants.Constants;
import com.xiaodou.st.dashboard.domain.grade.ClassDO;
import com.xiaodou.st.dashboard.service.BaseDashboardService;
import com.xiaodou.st.dashboard.service.admin.AdminService;
import com.xiaodou.st.dashboard.service.admin.AdminUser;
import com.xiaodou.st.dashboard.service.facade.IStServiceFacade;
import com.xiaodou.st.dashboard.service.message.QueueService;
import com.xiaodou.summer.dao.pagination.Page;

@Service
public class ClassService extends BaseDashboardService {
  @Resource
  IStServiceFacade stServiceFacade;
  @Resource
  QueueService queueService;
  @Resource
  AdminService adminService;

  /**
   * 查询班级列表
   * 
   * @return
   */
  public List<ClassDO> listClass() {
    Map<String, Object> inputs = Maps.newHashMap();
    AdminUser adminUser = super.getAdminUser();
    if (null == adminUser.getUnitId()) return null;
    inputs.put("pilotUnitId", adminUser.getUnitId());
    if (null != adminUser.getChildRole()
        && Constants.POILT_UNIT_CHILD_ROLE.equals(adminUser.getChildRole())) {
      inputs.put("adminId", adminUser.getId());
    }
    Page<ClassDO> page = stServiceFacade.listClass(inputs, CommUtil.getAllField(ClassDO.class));
    if (null == page) return null;
    return page.getResult();
  }

  public List<String> listClassName() {
    return Lists.transform(this.listClass(), new Function<ClassDO, String>() {
      @Override
      public String apply(ClassDO input) {
        if (null == input) return null;
        return input.getClassName();
      }
    });
  }

  /**
   * 增加班級
   */
  public String saveClass(ClassDO classDO) {
    AdminUser adminUser = super.getAdminUser();
    classDO.setPilotUnitId(adminUser.getUnitId());
    classDO.setPilotUnitName(adminUser.getUnitName());
    // classDO.setAdminId(adminUser.getUserId());
    // classDO.setAdminName(adminUser.getRealName());
    classDO.setCreateTime(new Timestamp(System.currentTimeMillis()));
    List<String> classNameList = this.listClassName();
    if (StringUtils.isNotBlank(classDO.getClassName())) {
      boolean flagV = classNameList.contains(classDO.getClassName());
      if (flagV) return "该单位下，班级名称已经被使用！";
    }
    boolean flag = stServiceFacade.saveClass(classDO);
    return String.valueOf(flag);
  }

  /**
   * 修改班級信息
   * 
   * @description TODO
   * @author 李德洪
   * @Date 2017年4月1日
   * @param classId
   * @return
   */
  public String updateClass(ClassDO classDO) {
    boolean flag = false;
    if (null != classDO.getId() && StringUtils.isNotBlank(classDO.getClassName())) {
      ClassDO cd = this.getClass(classDO.getId());
      if (!cd.getClassName().equals(classDO.getClassName())) {
        List<String> classNameList = this.listClassName();
        boolean flagV = classNameList.contains(classDO.getClassName());
        if (flagV) return "该单位下，班级名称已经被使用！";
      }
      flag = stServiceFacade.updateClass(classDO);
      // 发送消息进行学生信息表的数据同步更新
      queueService.pushUpdateClassName(classDO);
    }
    if(null != classDO.getId() && StringUtils.isBlank(classDO.getClassName())){
      flag = stServiceFacade.updateClass(classDO);
    }
    return String.valueOf(flag);
  }

  /**
   * 查詢班級
   * 
   * @description TODO
   * @author 李德洪
   * @Date 2017年4月1日
   * @param classId
   * @return
   */
  public ClassDO getClass(Long classId) {
    return stServiceFacade.getClass(classId);
  }

  /**
   * 刪除班級
   * 
   * @description TODO
   * @author 李德洪
   * @Date 2017年4月1日
   * @param classId
   * @return
   */
  public String removeClass(Long classId) {
    boolean flag = stServiceFacade.removeClass(classId);
    return String.valueOf(flag);
  }
}
