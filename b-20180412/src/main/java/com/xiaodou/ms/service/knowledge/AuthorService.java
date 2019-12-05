package com.xiaodou.ms.service.knowledge;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.RandomUtil;
import com.xiaodou.ms.dao.knowledge.AuthorDao;
import com.xiaodou.ms.model.knowledge.AuthorModel;
import com.xiaodou.ms.web.request.knowledge.AuthorAddRequest;
import com.xiaodou.ms.web.request.knowledge.AuthorEditRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.ms.web.response.selftaught.ShowResponse;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

/**
 * @name AuthorService CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年2月6日
 * @description 作者Service
 * @version 1.0
 */
@Service("authorService")
public class AuthorService {

  @Resource
  AuthorDao authorDao;

  public List<AuthorModel> search(String authorName) {
    IQueryParam param = new QueryParam();
    param.addInput("nameLike", authorName);
    param.addOutputs(CommUtil.getAllField(AuthorModel.class));
    Page<AuthorModel> authorPage = authorDao.findEntityListByCond(param, null);
    if (null == authorPage) return null;
    return authorPage.getResult();
  }

  public ShowResponse show(Long authorId) {
    ShowResponse response = new ShowResponse(ResultType.SUCCESS);
    AuthorModel author = new AuthorModel();
    author.setId(authorId);
    response.setAuthor(authorDao.findEntityById(author));
    return response;
  }

  public BaseResponse doAdd(AuthorAddRequest request) {
    BaseResponse response = new ShowResponse(ResultType.SUCCESS);
    AuthorModel model = request.initModel();
    model.setId(Long.parseLong(RandomUtil.randomNumber(9)));
    model.setGender((short) 1);
    model.setCreateTime(new Timestamp(System.currentTimeMillis()));
    authorDao.addEntity(model);
    return response;
  }

  public BaseResponse doEdit(AuthorEditRequest request) {
    BaseResponse response = new ShowResponse(ResultType.SUCCESS);
    AuthorModel model = request.initModel();
    authorDao.updateEntityById(model);
    return response;
  }

  public BaseResponse delete(Long id) {
    BaseResponse response = new BaseResponse(ResultType.SUCCESS);
    AuthorModel model = new AuthorModel();
    model.setId(id);
    authorDao.deleteEntityById(model);
    return response;
  }

}
