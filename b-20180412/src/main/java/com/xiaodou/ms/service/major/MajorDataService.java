package com.xiaodou.ms.service.major;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.ms.dao.major.MajorDataDao;
import com.xiaodou.ms.model.major.MajorDataModel;
import com.xiaodou.ms.model.major.MajorInfo;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

/**
 * Created by zhouhuan on 16/7/1.
 */
@Service("majorDataService")
public class MajorDataService {

  @Resource
  MajorDataDao majorDataDao;


  /**
   * 查询
   * 
   * @param id
   * @author zhouhaun
   * @return
   * 现在id和region变成联合主键，此方法过期
   */
  @Deprecated
  public MajorDataModel findMajorById(String id) {
    MajorDataModel cond = new MajorDataModel();
    cond.setId(id);
    return majorDataDao.findEntityById(cond);
  }

  public MajorDataModel findMajorByIdAndRegion(String id, String regionCode) {
    IQueryParam param = new QueryParam();
    param.addInput("id", id);
    param.addInput("region", regionCode);
    param.addOutput("name", "");
    param.addOutput("id", "");
    param.addOutput("region", "");
    param.addOutput("majorInfo", "");
    Page<MajorDataModel> page = majorDataDao.findEntityListByCond(param, null);
    return (page == null || page.getResult().size() == 0) ? null : page.getResult().get(0);
  }

  /**
   * 新增专业
   * 
   * @param entity
   * @author zhouhaun
   * @return
   */
  public MajorDataModel addMajor(MajorDataModel entity) {
    return majorDataDao.addEntity(entity);
  }


  /**
   * 根据JSON返回实体类
   * 
   * @param majorInfo
   * @author zhouhaun
   * @return
   */
  public MajorInfo getMajorInfo(String majorInfo) {
    if (StringUtils.isJsonNotBlank(majorInfo)) {
      MajorInfo mInfo = FastJsonUtil.fromJson(majorInfo, MajorInfo.class);
      if (null != mInfo) {
        return mInfo;
      }
    }
    return null;
  }

  /**
   * 分页查找专业列表
   * 
   * @param entity
   * @author zwj
   * @return
   */
  public Page<MajorDataModel> searchCourse(Integer pageNo, String courseName,String region) {
    IQueryParam param = new QueryParam();
    Page<MajorDataModel> page = new Page<>();
    if (pageNo == null) {
      page = null;
    } else {
      page.setPageNo(pageNo);
      page.setPageSize(20);
    }
    param.addInput("nameLike", courseName);
    param.addInput("region", region);
    param.addOutputs(CommUtil.getAllField(MajorDataModel.class));
    Page<MajorDataModel> coursePage = majorDataDao.findEntityListByCond(param, page);
    return coursePage;
  }



  /*public String readWord(String path) {
    String buffer = "";
    try {
      if (path.endsWith(".doc")) {
        InputStream is = new FileInputStream(new File(path));
        WordExtractor ex = new WordExtractor(is);
        buffer = ex.getText();
        ex.close();
      } else if (path.endsWith(".docx")) {
        OPCPackage opcPackage = POIXMLDocument.openPackage(path);
        POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
        buffer = extractor.getText();
        extractor.close();
      } else {
        System.out.println("此文件不是word文件！");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return buffer;
  }
*/
}
