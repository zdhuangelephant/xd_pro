package com.xiaodou.ms.service.user;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.common.info.constant.Constant;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.ms.dao.user.BlankNoticeDao;
import com.xiaodou.ms.dao.user.CommonInfoDao;
import com.xiaodou.ms.model.user.BlankNoticeModel;
import com.xiaodou.ms.web.request.user.BlankNoticeRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.ms.web.response.course.CategoryEditResponse;
import com.xiaodou.summer.dao.pagination.Page;

@Service("blankNoticeService")
public class BlankNoticeService {
  private String blankNotice_code = "009";
  @Resource
  BlankNoticeDao blankNoticeDao;
  @Resource
  CommonInfoDao commoninfoDao;

  /**
   * 获取所有栏目
   * 
   * @return
   */
  public Page<BlankNoticeModel> queryBlankNotice(Short isVisible, Integer pageNo) {
    Map<String, Object> cond = new HashMap<String, Object>();
    if(null != isVisible) {
      cond.put("isVisible", isVisible);
    }
    Map<String, Object> allField = CommUtil.getAllField(BlankNoticeModel.class);
    allField.remove("moduleName");
    allField.remove("module");
    Page<BlankNoticeModel> page = null;
    if(null != pageNo) {
      page = new Page<BlankNoticeModel>();
      page.setPageNo(pageNo);
      page.setPageSize(20);
    }
    Page<BlankNoticeModel> blankNoticeList = blankNoticeDao.queryListByCond(cond, allField, page);
    return blankNoticeList;
  }



  /**
   * 根据主键查询
   * 
   * @param catId
   * @return
   */
  public BlankNoticeModel findBlankNoticeById(Long id) {
    BlankNoticeModel entity = new BlankNoticeModel();
    entity.setId(id);
    return blankNoticeDao.findEntityById(entity);
  }

  /**
   * 更新首页要传递的数据
   * 
   * @param entity
   * @return
   */
  public Boolean editBlankNotice(BlankNoticeModel entity) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", entity.getId());
    return blankNoticeDao.updateEntityById(entity);
  }

  /**
   * 用户帮助编辑
   * 
   * @param request
   * @return
   */
  public BaseResponse editBlankNotice(BlankNoticeRequest request) {
    BlankNoticeModel blankNoticeModel = new BlankNoticeModel();
    blankNoticeModel.setId(request.getId());
    blankNoticeModel.setType(request.getType());
    blankNoticeModel.setJumpUrl(request.getJumpUrl());
    Boolean flag1 = this.editBlankNotice(blankNoticeModel);
    Boolean flag2 = true;
    if (flag1) {
      // 为xd_common_info中的info_version加1
      flag2 = commoninfoDao.updateInfoVersion();
      // 清理config/version接口中的缓存
      String commonInfo =
          JedisUtil.getStringFromJedis(Constant.CACHE_INFO + Constant.SEPRATOR + blankNotice_code);
      if (StringUtils.isNotBlank(commonInfo))
        JedisUtil.delKeyFromJedis(Constant.CACHE_INFO + Constant.SEPRATOR + blankNotice_code);
    }
    BaseResponse response = null;
    if (flag1 && flag2) {
      response = new CategoryEditResponse(ResultType.SUCCESS);
    } else {
      response = new CategoryEditResponse(ResultType.SYS_FAIL);
    }
    return response;
  }



  public Boolean delete(Long id) {
    BlankNoticeModel bModel = new BlankNoticeModel();
    bModel.setId(id);
    return blankNoticeDao.deleteEntityById(bModel);
  }



  public BaseResponse doOpenScreenNoticeAdd(BlankNoticeRequest request) {
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    BlankNoticeModel initModel = request.initModel(request);
    if (null == initModel.getIsInnerLink()) {
      initModel.setIsInnerLink((short) 1);
    }
    BlankNoticeModel addEntity = blankNoticeDao.addEntity(initModel);
    if(null != addEntity) {
      return response;
    }
    else {
      return new BaseResponse(ResultType.SYS_FAIL);
    }
    
  }



}
