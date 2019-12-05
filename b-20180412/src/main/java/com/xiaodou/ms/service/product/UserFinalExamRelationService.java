package com.xiaodou.ms.service.product;

import org.springframework.stereotype.Service;

/**
 * Created by zyp on 15/8/18.
 */
@Service("userFinalExamRelationService")
public class UserFinalExamRelationService {

  /*@Resource
  FinalExamDao finalExamDao;

public Page<FinalExamModel> queryAllRecord(Integer productId) {
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

*//**
   * 编辑幻灯片
   * @param slide
   * @return
   *//*
  public Boolean editFinalExam(FinalExamModel finalExam){
    Map<String,Object> cond = new HashMap<>();
    cond.put("id",finalExam.getId());
     Boolean res = finalExamDao.updateEntity(cond,finalExam);
     return res;
  }

public BaseResponse doAdd(FinalExamRequest request) {
	BaseResponse response = new FinalExamResponse(ResultType.SUCCESS);
	FinalExamModel model = request.initModel();
	model.setId(Integer.parseInt(RandomUtil.randomNumber(8)));
	model.setCreateTime(new Timestamp(System.currentTimeMillis()));
	model.setSort(model.getSort() == null ? 0: model.getSort());
    finalExamDao.addEntity(model);
	return response;
}

public Boolean delete(Integer id) {
	FinalExamModel model = new FinalExamModel();
	model.setId(id);
	return finalExamDao.deleteEntityById(model);
}

public FinalExamModel findFinalExamById(Integer id) {
	FinalExamModel entity = new FinalExamModel();
	entity.setId(id);
	return finalExamDao.findEntityById(entity);
}

public BaseResponse doEdit(FinalExamRequest request) {
	BaseResponse response = new FinalExamResponse(ResultType.SUCCESS);
	FinalExamModel model = request.initModel();
	finalExamDao.updateEntityById(model);
	return response;
}
*/
  /**
   * 根据Id查找
   * @param id
   * @return
   *//*
  public ModuleSlideModel findById(Integer id){
    ModuleSlideModel slideModel = new ModuleSlideModel();
    slideModel.setId(id);
    return moduleSlideDao.findEntityById(slideModel);
  }

  *//**
   * 列表
   * @param moduleId
   * @return
   *//*
  public List<ModuleSlideModel> slideList(Integer moduleId){
    Map<String,Object> cond = new HashMap<>();
    cond.put("moduleId",moduleId);
    Map<String,Object> output = new HashMap<>();
    output.put("id","");
    output.put("description","");
    output.put("imageUrl","");
    output.put("linkUrl","");
    output.put("listOrder","");
    output.put("moduleId","");
    Page<ModuleSlideModel> moduleSlideModelPage =
      moduleSlideDao.queryListByCondWithOutPage(cond, output);
    return moduleSlideModelPage.getResult();
  }

  *//**
   * 添加幻灯片
   * @param slide
   * @return
   *//*
  public ModuleSlideModel addSlide(ModuleSlideModel slide){
    return moduleSlideDao.addEntity(slide);
  }

  *//**
   * 添加幻灯片
   * @param request
   * @return
   *//*
  public ModuleSlideModel addSlide(SlideAddRequest request){
    ModuleSlideModel slide = new ModuleSlideModel();
    slide.setImageUrl(request.getImageUrl());
    slide.setCreateTime(new Timestamp(System.currentTimeMillis()));
    slide.setDescription(request.getDescription());
    slide.setLinkUrl(request.getLinkUrl());
    slide.setModuleId(request.getModuleId());
    return this.addSlide(slide);
  }

  *//**
   * 编辑幻灯片
   * @param slide
   * @return
   *//*
  public Boolean editSlide(ModuleSlideModel slide){
    Map<String,Object> cond = new HashMap<>();
    cond.put("id",slide.getId());
    return moduleSlideDao.updateEntity(cond,slide);
  }

  *//**
   * 编辑幻灯片
   * @param request
   * @return
   *//*
  public Boolean editSlide(SlideEditRequest request){
    ModuleSlideModel slide = new ModuleSlideModel();
    slide.setModuleId(request.getModuleId());
    slide.setId(request.getId());
    slide.setListOrder(request.getListOrder());
    slide.setLinkUrl(request.getLinkUrl());
    slide.setDescription(request.getDescription());
    slide.setImageUrl(request.getImageUrl());
    return this.editSlide(slide);
  }

  *//**
   * 删除幻灯片
   * @param id
   * @return
   *//*
  public Boolean deleteSlide(Integer id){
    Map<String,Object> cond = new HashMap<>();
    cond.put("id",id);
    return moduleSlideDao.deleteEntity(cond);
  }*/

}
