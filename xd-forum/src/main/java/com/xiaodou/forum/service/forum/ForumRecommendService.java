package com.xiaodou.forum.service.forum;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.forum.cache.ForumRecommendCache;
import com.xiaodou.forum.constant.Constant;
import com.xiaodou.forum.enums.ForumEnum;
import com.xiaodou.forum.model.forum.ForumUserModel;
import com.xiaodou.forum.response.forum.ForumRecommendResponse;
import com.xiaodou.forum.service.facade.ForumServiceFacade;
import com.xiaodou.forum.util.ForumUtil;
import com.xiaodou.forum.vo.forum.ForumRecommendVo;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.ucerCenter.agent.util.UserTokenWrapper;

/**
 * 话题推荐service
 * 
 * @author bing.cheng
 * 
 */
@Service("forumRecommendService")
public class ForumRecommendService {

  @Resource
  ForumServiceFacade forumServiceFacade;

  /**
   * 获取推荐列表 TODO 目前简单实现：查询话题表，获取type为推荐的话题，全部返回，总共最多50条
   * 
   * @return
   * @throws ParseException
   */
  public ForumRecommendResponse getRecommendList() throws ParseException {
    ForumRecommendResponse response = new ForumRecommendResponse(ResultType.SUCCESS);
    if (null != UserTokenWrapper.getWrapper().getUserModel())
      response.setUnReadInfoCount(Integer.toString(queryUnreadRelateInfoCount(UserTokenWrapper
          .getWrapper().getUserModel().getId())));
    LinkedList<ForumRecommendVo> allList = null;
    // 1、先去缓存取。
    String result = ForumRecommendCache.getForumRecommendList();
    if (!StringUtils.isBlank(result)) {
      allList =
          (LinkedList<ForumRecommendVo>) JSON.parseObject(result,
              new TypeReference<LinkedList<ForumRecommendVo>>() {});
      response.setList(allList);
      return response;
    }

    // 2、查询数据库 存缓存 返回列表
    // ①、推荐 recommend = 1
    Map<String, Object> cond = new HashMap<String, Object>();
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("userId", UserTokenWrapper.getWrapper().getUserModel().getId());
    input.put("recommend", ForumEnum.IsRecommendForum.getCode());
    cond.put("input", input);
    Map<String, Object> sort = new HashMap<String, Object>();
    sort.put("updateTime", "DESC");
    cond.put("sort", sort);
    // TODO 目前没有分页，全部展示
    cond.put("limitCount", 50);
    cond.put("output", ForumUtil.getForumListOutput());

    List<ForumUserModel> list = forumServiceFacade.queryForumUserListByCondNoPage(cond);
    if (null == list || list.size() <= 0) {
      return response;
    }

    // 构造返回结果
    allList = new LinkedList<ForumRecommendVo>();
    // 普通推荐话题临时存储
    List<ForumUserModel> commonList = new ArrayList<ForumUserModel>();
    for (ForumUserModel model : list) {
      // 筛选出普通话题
      if (ForumEnum.IsTopForum.getCode() != model.getTop()) {
        commonList.add(model);
      } else {
        // 置顶话题存入返回结果列表
        try {
          allList.add(new ForumRecommendVo(model));
        } catch (Exception e) {
          continue;
        }
      }
      Collections.sort(allList, new Comparator<ForumRecommendVo>() {
        @Override
        public int compare(ForumRecommendVo _this, ForumRecommendVo _other) {
          return Integer.parseInt(_other.getPraiseNumber())
              - Integer.parseInt(_this.getPraiseNumber());
        }
      });
    }

    for (ForumUserModel commonModel : commonList) {
      // 置顶话题存入返回结果列表
      try {
        allList.add(new ForumRecommendVo(commonModel));
      } catch (Exception e) {
        continue;
      }
    }
    response.setList(allList);
    return response;
  }

  /**
   * 查询未读消息数量
   * 
   * @param userId update by lidehong --private->public
   * @return
   */
  private Integer queryUnreadRelateInfoCount(String userId) {
    Map<String, Object> cond = new HashMap<String, Object>();
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("targeId", userId);
    input.put("status", Constant.RELATEINFO_UNREAD);
    cond.put("input", input);
    return forumServiceFacade.countRelationInfo(cond);
  }

}
