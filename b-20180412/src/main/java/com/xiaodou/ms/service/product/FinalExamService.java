package com.xiaodou.ms.service.product;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.RandomUtil;
import com.xiaodou.ms.dao.mission.MissionDao;
import com.xiaodou.ms.dao.product.FinalExamDao;
import com.xiaodou.ms.dao.product.UserFinalExamRelationDao;
import com.xiaodou.ms.model.mission.MissionModel;
import com.xiaodou.ms.model.product.FinalExamModel;
import com.xiaodou.ms.model.product.ProductModel;
import com.xiaodou.ms.model.product.UserFinalExamRelationModel;
import com.xiaodou.ms.service.mission.MissionService;
import com.xiaodou.ms.vo.MissionVo;
import com.xiaodou.ms.web.request.product.FinalExamRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.ms.web.response.product.FinalExamResponse;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IJoinQueryParam;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.JoinQueryParam;
import com.xiaodou.summer.dao.param.QueryEnums.Sort;
import com.xiaodou.summer.dao.param.QueryParam;

/**
 * Created by zyp on 15/8/18.
 */
@Service("finalExamService")
public class FinalExamService {

  @Resource
  FinalExamDao finalExamDao;

  @Resource
  MissionService missionService;

  @Resource
  MissionDao missionDao;

  @Resource
  UserFinalExamRelationDao userFinalExamRelationDao;

  @Resource
  ProductService productService;

  @Resource
  ProductItemService productItemService;

  public Page<FinalExamModel> queryAllRecord(Long productId) {
    IQueryParam param = new QueryParam();
    param.addInput("courseId", productId);
    param.addOutput("id", "");
    param.addOutput("examName", "");
    param.addOutput("questionNums", "");
    param.addOutput("sort", "");
    param.addOutput("courseId", "");
    param.addOutput("createTime", "");
    param.addSort("sort", Sort.ASC);
    return finalExamDao.findEntityListByCond(param, null);
  }

  public List<MissionVo> queryAllRecordMission(Long productId) {
    IJoinQueryParam param = new JoinQueryParam();
    param.addInput("courseId", productId);
    param.addOutput("id", "");
    param.addOutput("examName", "");
    param.addOutput("questionNums", "");
    param.addOutput("sort", "");
    param.addOutput("courseId", "");
    param.addOutput("createTime", "");


    param.addSort("sort", Sort.ASC);
    Page<FinalExamModel> finalExamPage = finalExamDao.cascadeQueryFinalExamMission(param, null);

    if (finalExamPage != null && finalExamPage.getResult().size() != 0) {
      for (FinalExamModel finalExam : finalExamPage.getResult()) {
        if (finalExam == null) continue;
        IQueryParam _param = new QueryParam();
        _param.addInput("threshold", finalExam.getId());
        _param.addInput("courseId", finalExam.getCourseId());
        _param.addInput("chapterId", "-1");
        _param.addOutputs(CommUtil.getAllField(MissionModel.class));
        Page<MissionModel> page_mission = missionDao.findEntityListByCond(_param, null);
        if (page_mission != null && page_mission.getResult().size() != 0) {
          MissionModel missionModel = page_mission.getResult().get(0);
          finalExam.setMissionId(missionModel.getId());
          finalExam.setMissionOrder(missionModel.getMissionOrder());
          finalExam.setMissionState("" + missionModel.getMissionState());
        }
      }
    }



    if (null != finalExamPage && null != finalExamPage.getResult()
        && finalExamPage.getResult().size() > 0) {
      List<MissionVo> missionList = Lists.newArrayList();
      for (FinalExamModel exam : finalExamPage.getResult()) {
        missionList.add(packageFinalExam2Mission(exam));
      }
      return missionList;
    }
    return null;
  }

  private MissionVo packageFinalExam2Mission(FinalExamModel finalExam) {
    MissionVo mission = new MissionVo();
    mission.setDesc("完成" + finalExam.getExamName() + "并闯关成功");
    mission.setCourseId(finalExam.getCourseId().toString());
    mission.setChapterId("-1");
    mission.setChapterIndex("期末测验");
    mission.setChapterName(finalExam.getExamName());
    mission.setThreshold(finalExam.getId().toString());
    mission.setListOrder(finalExam.getSort());
    mission.setMissionId(finalExam.getMissionId());
    mission.setMissionOrder(finalExam.getMissionOrder());
    mission.setMissionState(finalExam.getMissionState());
    mission.setModule(finalExam.getModule());
    return mission;
  }

  public Boolean deleteFinalExam(Long id) {
    FinalExamModel model = new FinalExamModel();
    model.setId(id);
    IQueryParam param = new QueryParam();
    param.addInput("finalExamId", id);
    param.addOutput("id", "");
    param.addOutput("userId", "");
    param.addOutput("paperNo", "");
    Page<UserFinalExamRelationModel> results =
        userFinalExamRelationDao.findEntityListByCond(param, null);
    if (results == null || results.getResult().size() <= 0) {
      return finalExamDao.deleteEntityById(model);
    } else {
      return Boolean.FALSE;
    }

  }

  /**
   * 编辑幻灯片
   * 
   * @param slide
   * @return
   */
  public Boolean editFinalExam(FinalExamModel finalExam) {
    Map<String, Object> cond = new HashMap<>();
    cond.put("id", finalExam.getId());
    Boolean res = finalExamDao.updateEntity(cond, finalExam);
    return res;
  }

  public BaseResponse doAdd(FinalExamRequest request) {
    BaseResponse response = new FinalExamResponse(ResultType.SUCCESS);
    FinalExamModel model = request.initModel();
    model.setId(Long.parseLong(RandomUtil.randomNumber(8)));
    model.setCreateTime(new Timestamp(System.currentTimeMillis()));
    model.setSort(model.getSort() == null ? 0 : model.getSort());
    model = finalExamDao.addEntity(model);
    // 设置地域为课程所属地域
    ProductModel findForModule = productService.findSubjectById(model.getCourseId());
    model.setModule(findForModule.getModule());
    missionService.addStandardMission(packageFinalExam2Mission(model));
    productService.updateSubjectMisc(model.getCourseId(),
        productItemService.buildMisc(model.getCourseId()));
    return response;
  }

  public Boolean delete(Long id) {
    FinalExamModel model = new FinalExamModel();
    model.setId(id);
    IQueryParam param = new QueryParam();
    param.addInput("finalExamId", id);
    param.addOutput("id", "");
    param.addOutput("userId", "");
    param.addOutput("paperNo", "");
    Page<UserFinalExamRelationModel> results =
        userFinalExamRelationDao.findEntityListByCond(param, null);
    if (results == null || results.getResult() == null || results.getResult().size() <= 0) {
      if (finalExamDao.deleteEntityById(model)) {
        if (null != id) missionService.deleteStandardMission(id.toString());
        return Boolean.TRUE;
      }
    }
    return Boolean.FALSE;

  }

  public FinalExamModel findFinalExamById(Long id) {
    FinalExamModel entity = new FinalExamModel();
    entity.setId(id);
    return finalExamDao.findEntityById(entity);
  }

  public BaseResponse doEdit(FinalExamRequest request) {
    BaseResponse response = new FinalExamResponse(ResultType.SUCCESS);
    FinalExamModel model = request.initModel();
    if (finalExamDao.updateEntityById(model)) {
      MissionVo vo = packageFinalExam2Mission(model);
      MissionModel mission = new MissionModel();
      mission.setThreshold(vo.getThreshold());
      mission.setMissionDesc(vo.getDesc());
      missionService.editMissionState(mission);
    }
    return response;
  }

}
