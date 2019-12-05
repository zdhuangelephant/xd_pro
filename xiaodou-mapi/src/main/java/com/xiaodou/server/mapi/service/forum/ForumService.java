package com.xiaodou.server.mapi.service.forum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.xiaodou.common.http.HttpResultStatus;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.EmojiUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.server.mapi.request.forum.ShareRequest;
import com.xiaodou.server.mapi.response.forum.ForumListResponse;
import com.xiaodou.server.mapi.response.forum.ForumResponse;
import com.xiaodou.server.mapi.response.forum.ForumShareResponse;
import com.xiaodou.server.mapi.response.forum.IndexResponse;
import com.xiaodou.server.mapi.response.forum.ShareAllResponse;
import com.xiaodou.server.mapi.response.forum.ShareResponses;
import com.xiaodou.server.mapi.response.forum.UserForumListResponse;
import com.xiaodou.server.mapi.response.product.ProductCategoryListResponse;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.server.mapi.service.BaseMapiService;
import com.xiaodou.server.mapi.vo.forum.Forum;
import com.xiaodou.server.mapi.vo.request.ForumIndexRequest;
import com.xiaodou.server.mapi.vo.request.ForumListRequest;
import com.xiaodou.server.mapi.vo.request.ForumPraiseRequest;
import com.xiaodou.server.mapi.vo.request.ForumRequest;
import com.xiaodou.server.mapi.vo.request.ForumUserRequest;
import com.xiaodou.summer.vo.out.ResultType;

@Service("forumService")
public class ForumService extends BaseMapiService {

    public IndexResponse index(ForumIndexRequest request) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("forumId", request.getForumId());
        HttpResult httpResult = router("forum", "index", param);
        if (null != httpResult && httpResult.getStatusCode() == 200)
            return FastJsonUtil.fromJson(httpResult.getContent(), IndexResponse.class);
        return new IndexResponse();
    }

    public ForumListResponse openCourse(ForumListRequest request) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("uid", request.getUid());
        param.put("module", request.getModule());
        param.put("forumId", request.getForumId());
        param.put("forumTag", request.getForumTag());
        param.put("forumType", request.getForumType());
        HttpResult httpResult = router("forum", "openCoureseList", param);
        if (null != httpResult && httpResult.getStatusCode() == 200)
            return FastJsonUtil.fromJson(httpResult.getContent(), ForumListResponse.class);
        return new ForumListResponse(ResultType.SYSFAIL);
    }

    public ForumShareResponse shareList(ForumListRequest request) {
        ForumShareResponse response = new ForumShareResponse(ResultType.SUCCESS);
        ShareAllResponse res = new ShareAllResponse(ResultType.SUCCESS);
        Map<String, Object> param = Maps.newHashMap();
        param.put("uid", request.getUid());
        param.put("module", request.getModule());
        param.put("forumType", request.getForumType());
        param.put("forumTag", request.getForumTag());
        param.put("forumId", request.getForumId());
        param.put("forumTime", request.getForumTime());
        HttpResult httpResult = router("forum", "share", param);
        if (null != httpResult && httpResult.getStatusCode() == 200) {
            res = FastJsonUtil.fromJson(httpResult.getContent(), ShareAllResponse.class);
            TreeMap<String, List<Forum>> map = new TreeMap<String, List<Forum>>();
            for (Forum forum : res.getForumList()) {
                String tsStr = "";
                if (forum.getCreateTime() != null) {
                    try {
                        tsStr = forum.getCreateTime().substring(0, 7);
                        if (map.get(tsStr) == null) {
                            List<Forum> alist = new ArrayList<Forum>();
                            alist.add(forum);
                            map.put(tsStr, alist);
                        } else {
                            map.get(tsStr).add(forum);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            for (String key : map.keySet()) {
                ForumShareResponse.ForumShare f = response.new ForumShare();
                f.setTimeTag(key);
                f.setForumList(map.get(key));
                response.getList().add(0, f);
            }
            response.setProductTagList(res.getList());
            return response;
        }
        return new ForumShareResponse(ResultType.SYSFAIL);
    }

    public ForumResponse forumDetail(ForumRequest request) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("uid", request.getUid());
        param.put("module", request.getModule());
        param.put("forumId", request.getForumId());
        HttpResult httpResult = router("forum", "forumDetail", "1.1.0", param);
        if (null != httpResult && httpResult.getStatusCode() == 200)
            return FastJsonUtil.fromJson(httpResult.getContent(), ForumResponse.class);
        return new ForumResponse(ResultType.SYSFAIL);
    }

    public UserForumListResponse authorDetail(ForumUserRequest request) {
        // TODO Auto-generated method stub
        UserForumListResponse response = new UserForumListResponse(ResultType.SUCCESS);
        Map<String, Object> param = Maps.newHashMap();
        param.put("uid", request.getAuthorId());
        param.put("module", request.getModule());
        param.put("forumId", request.getForumId());
        HttpResult httpResult = router("forum", "authorDetail", param);
        if (null != httpResult && httpResult.getStatusCode() == 200) {
            response = FastJsonUtil.fromJson(httpResult.getContent(), UserForumListResponse.class);
            return response;
        }
        return new UserForumListResponse(ResultType.SYSFAIL);
    }

    public ProductCategoryListResponse major(ForumListRequest request) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("module", request.getModule());
        param.put("forumType", request.getForumType());
        param.put("forumId", request.getForumId());
        HttpResult httpResult = router("forum", "major", param);
        if (null != httpResult && httpResult.getStatusCode() == 200) return FastJsonUtil
                .fromJson(httpResult.getContent(), ProductCategoryListResponse.class);
        return new ProductCategoryListResponse(ResultType.SYSFAIL);
    }

    /**
     * 点赞
     * 
     * @author zhouhuan
     */
    public BaseResponse praise(ForumPraiseRequest pojo) {
        // TODO Auto-generated method stub
        Map<String, Object> params = Maps.newHashMap();
        params.put("uid", pojo.getUid());
        params.put("module", pojo.getModule());
        params.put("deviceId", pojo.getDeviceId());
        params.put("clientIp", pojo.getClientIp());
        params.put("resourcesId", pojo.getForumId());
        HttpResult userList = router("forum", "praise", "1.1.0", params);
        if (null != userList && userList.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(userList.getContent())) {
            return FastJsonUtil.fromJson(userList.getContent(), BaseResponse.class);
        }
        return new BaseResponse(ResultType.SYSFAIL);
    }

    /**
     * 取消点赞
     * 
     * @author zhouhuan
     */
    public BaseResponse cancel(ForumPraiseRequest pojo) {
        // TODO Auto-generated method stub
        Map<String, Object> params = Maps.newHashMap();
        params.put("uid", pojo.getUid());
        params.put("module", pojo.getModule());
        params.put("deviceId", pojo.getDeviceId());
        params.put("clientIp", pojo.getClientIp());
        params.put("resourcesId", pojo.getForumId());
        HttpResult userList = router("forum", "cancel", "1.1.0", params);
        if (null != userList && userList.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(userList.getContent())) {
            return FastJsonUtil.fromJson(userList.getContent(), BaseResponse.class);
        }
        return new BaseResponse(ResultType.SYSFAIL);
    }

    public ShareResponses share(ShareRequest pojo) {
        ShareResponses response = new ShareResponses(ResultType.SUCCESS);
        Map<String, Object> params = Maps.newHashMap();
        params.put("uid", "-1");
        params.put("module", "3");
        params.put("shareType", pojo.getShareType());
        params.put("resourceId", pojo.getResourceId());
        HttpResult result = router("resources", "share", params);
        if (null != result && result.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(result.getContent())) {
            String content = EmojiUtil.resolveToEmojiFromByte(result.getContent());
            content = content.replaceAll("\\s*", "");
            content = content.replaceAll("&nbsp;", "");
            response = FastJsonUtil.fromJson(content, ShareResponses.class);
        } else {
            response = new ShareResponses(ResultType.SYSFAIL);
        }
        return response;
    }

    public String sharePage(String resourceId, String _timeStamp) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("resourceId", resourceId);
        params.put("_timeStamp", _timeStamp);
        HttpResult result = router("resources", "sharePage", "1.1.0", params);
        if (null != result && result.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(result.getContent())) {
            return result.getContent();
        } else {
            return new BaseResponse(ResultType.SYSFAIL).toString();
        }
    }

    public String getJsApiMap(String url) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("url", url);
        HttpResult result = router("resources", "getJsApiMap", "1.1.0", params);
        if (null != result && result.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(result.getContent())) {
            return result.getContent();
        } else {
            return new BaseResponse(ResultType.SYSFAIL).toString();
        }
    }

    public String notWeiXin() {
        Map<String, Object> params = Maps.newHashMap();
        HttpResult result = router("resources", "notWeiXin", "1.1.0", params);
        if (null != result && result.getStatusCode() == HttpResultStatus.OK.getCode()
                && StringUtils.isJsonNotBlank(result.getContent())) {
            return result.getContent();
        } else {
            return new BaseResponse(ResultType.SYSFAIL).toString();
        }
    }

}
