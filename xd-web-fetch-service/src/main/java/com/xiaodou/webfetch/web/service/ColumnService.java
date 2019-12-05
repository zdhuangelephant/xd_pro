package com.xiaodou.webfetch.web.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.webfetch.web.constants.Consts;
import com.xiaodou.webfetch.web.constants.ForumClassify;
import com.xiaodou.webfetch.web.constants.ForumType;
import com.xiaodou.webfetch.web.constants.IDGenerator;
import com.xiaodou.webfetch.web.constants.Platform;
import com.xiaodou.webfetch.web.dao.AuthorModelDao;
import com.xiaodou.webfetch.web.dao.ColumnDao;
import com.xiaodou.webfetch.web.dao.ForumDao;
import com.xiaodou.webfetch.web.domain.AuthorModel;
import com.xiaodou.webfetch.web.domain.Column;
import com.xiaodou.webfetch.web.domain.Column.Author;
import com.xiaodou.webfetch.web.domain.ForumModel;

/**
 * 知乎专栏Service层
 * 
 * @name ColumnService CopyRright (c) 2017 by
 *       <a href="mailto:huangzedong@corp.51xiaodou.com">zdhuang</a>
 *
 * @author <a href="mailto:huangzedong@corp.51xiaodou.com">zdhuang</a>
 * @date 2017年12月28日
 * @description TODO
 * @version 1.0
 */
@Service("columnService")
public class ColumnService {
  @Resource
  ColumnDao columnDao;
  @Resource
  AuthorModelDao authorModelDao;
  @Resource
  ForumDao forumDao;

  public void save(String signature, Integer offset, Integer size) {
    Map<String,String> uidSelfMaps = Maps.newHashMap();
    
    ArrayList<Column> results = Lists.newArrayList();
    List<Column> columns = null;
    do {
      columns = getDocument(signature, offset, size, uidSelfMaps);
      if (null == columns || columns.isEmpty()) {
        break;
      }
      offset += size;
      results.addAll(columns);
    } while (!CollectionUtils.isEmpty(columns));

    // 存入数据库
    if (!CollectionUtils.isEmpty(results)) {
      for (Column column : results) {
        columnDao.addEntity(column);
      }
    }
  }


  public static List<Column> getDocument(String signature, Integer offset, Integer size, Map<String,String> uidSelfMaps) {
    
    try {
      if (null == offset) offset = 0;
      if (null == size) size = 20;
      Connection connect =
          Jsoup.connect(String.format(Consts.ZH_ZHUANLAN, signature, size, offset));
      connect.maxBodySize(Consts.BODYSIZE);
      byte[] body = connect.ignoreContentType(true).execute().bodyAsBytes();
      if (null != body && body.length > 0) {
        String sbody = new String(body, Consts.CHARSET);
        List<Column> resultOfJson =
            FastJsonUtil.fromJsons(sbody, new TypeReference<List<Column>>() {});
        if (!CollectionUtils.isEmpty(resultOfJson)) {
          for (Column column : resultOfJson) {
            if(!uidSelfMaps.containsKey(column.getAuthor().getUid()+"")) {
              uidSelfMaps.put(column.getAuthor().getUid()+"", IDGenerator.getSelfId());
            }
            column.getAuthor().setSelfId(uidSelfMaps.get(column.getAuthor().getUid()+""));
            column.setSeflId(IDGenerator.getSelfId());
            column.setSignature(signature);
          }
        }
        return resultOfJson;
      }
    } catch (IOException e) {
      LoggerUtil.error("Jsoup解析知乎专栏出错", e);
    }
    return null;
  }


  /**
   * 初始化作者 知乎专栏
   */
  public void initAuthor() {
    
    // step 1 查询已经存在的数据库中作者的信息
    HashMap<String, AuthorModel> existsAuthors = Maps.newHashMap();
    IQueryParam cond = new QueryParam();
    cond.addOutputs(CommUtil.getAllField(AuthorModel.class));
    Page<AuthorModel> pageBean = authorModelDao.findEntityListByCond(cond, null);
    if (null != pageBean && pageBean.getResult().size() != 0) {
      for (AuthorModel author : pageBean.getResult()) {
        if (!existsAuthors.containsKey(author.getId()+"")) {
          existsAuthors.put(author.getId()+"", author);
        }
      }
    }

    // step 2 从mongo内读出所有已经存在的信息(待优化,已处理和待处理应分别标记)
    LinkedHashMap<String, Author> authorInfos = Maps.newLinkedHashMap();
    IQueryParam param = new QueryParam();
    param.addOutputs(CommUtil.getAllField(Column.class));
    Page<Column> page = columnDao.findEntityListByCond(param, null);
    if (null == page || page.getResult().size() == 0) return;
    for (Column column : page.getResult()) {
      if (column == null) continue;
      Author author = column.getAuthor();
      if (null == author) continue;
      if (!authorInfos.containsKey(author.getUid() + "")) {
        if (!CollectionUtils.isEmpty(existsAuthors)
            && existsAuthors.containsKey(author.getUid()+"")) {
          continue;
        } else {
          authorInfos.put(author.getUid() + "", author);
        }
      }
    }

    // step 3 将作者信息存入到xd_resource_author中(待优化.应根据时间戳及时更新作者个人信息)
    if (!CollectionUtils.isEmpty(authorInfos)) {
      for (Map.Entry<String, Column.Author> author : authorInfos.entrySet()) {
        AuthorModel authorModel = new AuthorModel();
        authorModel.setId(Long.parseLong(author.getKey()));
        authorModel.setGender((short) 1);
        authorModel.setWorksNum(page.getResult().size() == 0 ? 0 : page.getResult().size());
        authorModel.setName(author.getValue().getName());
        authorModel.setPortrait(Consts.PORTRAIT_ZHIHU + author.getValue().getAvatar().getId()
            + Consts.PORTRAIT_UNDERLINE_ZHIHU + Consts.PORTRAIT_SIZE_ZHIHU);
        authorModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
        authorModel.setInfo(author.getValue().getDescription());
        authorModel.setSelfId(author.getValue().getSelfId());
        authorModelDao.addEntity(authorModel);
      }
    }
  }


  public void clean() {
    // step1 获取所有的组着信息
    HashMap<String, String> authorInfos = Maps.newHashMap();
    IQueryParam cond = new QueryParam();
    cond.addOutputs(CommUtil.getAllField(AuthorModel.class));
    Page<AuthorModel> pageBean = authorModelDao.findEntityListByCond(cond, null);
    if (null != pageBean && pageBean.getResult().size() != 0) {
      for (AuthorModel author : pageBean.getResult()) {
        if (!authorInfos.containsKey(author.getSelfId()) && org.apache.commons.lang.StringUtils.isNotBlank(author.getSelfId())) {
          authorInfos.put(author.getId()+"", author.getSelfId());
        }
      }
    }


    // step2 获取所有已经存在的信息
    ArrayList<String> existsForums = Lists.newArrayList();
    IQueryParam input = new QueryParam();
    input.addOutput("selfId", "");
    Page<ForumModel> page = forumDao.findEntityListByCond(input, null);
    if (null != page && page.getResult().size() != 0) {
      for (ForumModel forum : page.getResult()) {
        if(forum == null) continue;
        if (org.apache.commons.lang.StringUtils.isNotBlank(forum.getSelfId()) && !existsForums.contains(forum.getSelfId())) {
          existsForums.add(forum.getSelfId());
        }
      }
    }


    IQueryParam param = new QueryParam();
    param.addOutputs(CommUtil.getAllField(Column.class));
    Page<Column> pageColumn = columnDao.findEntityListByCond(param, null);
    if (null == pageColumn || pageColumn.getResult().size() == 0) return;
    for (Column column : pageColumn.getResult()) {
      if (!CollectionUtils.isEmpty(existsForums) && existsForums.contains(column.getSeflId())) {
        continue;
      }
      ForumModel forum = new ForumModel();
      forum.setForumId(IDGenerator.getSeqID());
      forum.setForumTag("00383"); // major_data内的专业代码
      forum.setForumType(ForumType.OPENCOURSE.code); // 1公开课; 2知识分享; 4校园之声
      forum.setForumClassify(ForumClassify.TEXT.getCode()); // 1文本; 2视频; 3音频
      forum.setForumCover(column.getTitleImage());
      forum.setForumTitle(column.getTitle());
      forum.setForumContent(column.getContent());
//      forum.setAuthorId(column.getAuthor().getUid()+"");
      forum.setAuthorId("802423018"); // 慕享公开课
      forum.setForumMedia("0");
      forum.setForumPraiserNum(column.getLikesCount().intValue());
      forum.setForumReaderNum(column.getLikesCount().intValue()
          + column.getCommentsCount().  intValue() + IDGenerator.getRandoms(1000, 0));
      forum.setPlatform(Platform.COLUMN_ZHIHU.getCode().toString());
      forum.setSelfId(column.getSeflId());
      forum.setStatus((short) 0);
      forum.setCreateTime(new Timestamp(System.currentTimeMillis()));
      
      forum.setUpdateTime(forum.getCreateTime());
      try {
        forumDao.addEntity(forum);
        Column c = new Column();
        c.setSeflId(column.getSeflId());
        c.setIsSub("1");
        columnDao.updateEntityById(c);
      } catch (Exception e) {
        LoggerUtil.error("入库xd_resource_list中出错" + column.getSeflId(), e);
      }
    }
  }

}
