package com.xiaodou.ms.service.user;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.xiaodou.ms.constants.XdmsConstant;
import com.xiaodou.ms.dao.knowledge.PushRecordDao;
import com.xiaodou.ms.dao.user.UserNoticeDao;
import com.xiaodou.ms.model.knowledge.PushRecordModel;
import com.xiaodou.ms.model.user.UserNoticeModel;
import com.xiaodou.ms.service.common.PushService;
import com.xiaodou.ms.service.knowledge.PushRecordService;
import com.xiaodou.ms.web.request.user.UserNoticeCreateRequest;
import com.xiaodou.ms.web.request.user.UserNoticeEditRequest;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;
import com.xiaodou.ms.web.response.course.CategoryCreateResponse;
import com.xiaodou.ms.web.response.course.CategoryEditResponse;
import com.xiaodou.push.agent.enums.MessageType;
import com.xiaodou.push.agent.enums.SpreadRange;
import com.xiaodou.push.agent.enums.TargetType;
import com.xiaodou.push.agent.model.Message;
import com.xiaodou.push.agent.util.PushUtil;
import com.xiaodou.summer.dao.pagination.Page;

@Service("userNoticeService")
public class UserNoticeService {
    /**
     * 用户帮助DAO
     */
    @Resource
    UserNoticeDao userNoticeDao;

    @Resource
    PushRecordService pushRecordService;

    @Resource
    PushRecordDao pushRecordDao;


    /**
     * 新增公告
     * 
     * @param entity
     * @return
     */
    public UserNoticeModel addUserNotice(UserNoticeModel entity) {
        return userNoticeDao.addEntity(entity);
    }

    /**
     * 新增公告
     * 
     * @param request
     * @return
     */
    public CategoryCreateResponse addUserNotice(UserNoticeCreateRequest request) {
        UserNoticeModel userNoticeModel = new UserNoticeModel();
        // userNoticeModel.setId(request.getId());
        userNoticeModel.setTitle(request.getTitle());
        userNoticeModel.setContent(request.getContent());
        userNoticeModel.setUpdateUser(request.getUpdateUser());
        userNoticeModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userNoticeModel.setUpdateUser(userDetails.getUsername());
        UserNoticeModel resultModel = this.addUserNotice(userNoticeModel);
        CategoryCreateResponse response = new CategoryCreateResponse(ResultType.SUCCESS);
        response.setCatId(resultModel.getId());
        return response;
    }

    /**
     * 删除公告
     * 
     * @param catId
     * @return
     */
    public Boolean deleteUserNotice(Integer catId) {
        Map<String, Object> cond = new HashMap<>();
        cond.put("id", catId);
        return userNoticeDao.deleteEntity(cond);
    }

    /**
     * 更新公告
     * 
     * @param entity
     * @return
     */
    public Boolean editUserNotice(UserNoticeModel entity) {
        Map<String, Object> cond = new HashMap<>();
        cond.put("id", entity.getId());
        return userNoticeDao.updateEntity(cond, entity);
    }

    /**
     * 用户帮助编辑
     * 
     * @param request
     * @return
     */
    public CategoryEditResponse editUserNotice(UserNoticeEditRequest request) {
        UserNoticeModel userNoticeModel = new UserNoticeModel();
        userNoticeModel.setType(request.getType());
        userNoticeModel.setTitle(request.getTitle());
        userNoticeModel.setContent(request.getContent());
        userNoticeModel.setId(request.getId());
        userNoticeModel.setUpdateUser(request.getUpdateUser());
        userNoticeModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
        Boolean aBoolean = this.editUserNotice(userNoticeModel);
        CategoryEditResponse response = null;
        if (aBoolean) {
            response = new CategoryEditResponse(ResultType.SUCCESS);
        } else {
            response = new CategoryEditResponse(ResultType.SYS_FAIL);
        }
        return response;
    }

    /**
     * 根据主键查询
     * 
     * @param catId
     * @return
     */
    public UserNoticeModel findUserNoticeById(Long id) {
        UserNoticeModel entity = new UserNoticeModel();
        entity.setId(id);
        return userNoticeDao.findEntityById(entity);
    }



    /**
     * 根据id查询
     * 
     * @param id
     * @return
     */
    public Page<UserNoticeModel> queryUserNoticeByCatId(Integer id) {
        Map<String, Object> cond = new HashMap<>();
        cond.put("id", id);
        Map<String, Object> output = new HashMap<>();
        output.put("id", "");
        output.put("title", "");
        output.put("content", "");
        output.put("createTime", "");
        output.put("updateUser", "");
        return userNoticeDao.queryListByCond0(cond, output, null);
    }


    /**
     * 获取所有栏目
     * 
     * @return
     */
    public List<UserNoticeModel> queryAllUserNotice() {
        Map<String, Object> cond = new HashMap<String, Object>();
        Map<String, Object> output = new HashMap<>();
        output.put("id", "");
        output.put("type", "");
        output.put("title", "");
        output.put("content", "");
        output.put("createTime", "");
        output.put("updateUser", "");
        Page<UserNoticeModel> userNoticeList = userNoticeDao.queryListByCond0(cond, output, null);
        return userNoticeList.getResult();
    }

    /**
     * 
     * 根据id获取
     * 
     * @param id
     * @return
     */
    public List<UserNoticeModel> queryAllUserNoticeById(Long id) {
        List<UserNoticeModel> result = new ArrayList<UserNoticeModel>();
        List<UserNoticeModel> list = this.queryAllUserNotice();
        result.addAll(list);
        for (UserNoticeModel model : list) {
            result.addAll(this.queryAllUserNoticeById(model.getId()));
        }
        return result;
    }

    /**
     * 下拉框目录列表
     * 
     * @return
     */
    public List<UserNoticeModel> userNoticeSelect(String selectedId) {
        List<UserNoticeModel> userNoticeList = this.queryAllUserNotice();
        return userNoticeList;
    }

    public BaseResponse pushForum(Long id, String typeId, Integer counts, String tag) {
        BaseResponse response = new BaseResponse(ResultType.SUCCESS);

        UserNoticeModel userNotice = new UserNoticeModel();
        userNotice.setId(id);
        userNotice = userNoticeDao.findEntityById(userNotice);

        Message pushMessage = new Message();

        pushMessage.setMessageContent("小编推荐：" + userNotice.getTitle());
        pushMessage.setNoticeContent("小编推荐：" + userNotice.getTitle());
        pushMessage.setMessageType(MessageType.ALL);

        pushMessage.setTarget(TargetType.ALL);
        Map<String, String> messageextras = new HashMap<String, String>();
        Map<String, String> noticeextras = new HashMap<String, String>();
        if (typeId.equals("4")) {
            noticeextras.put("notieceId", userNotice.getId() + "");
            messageextras.put("notieceId", userNotice.getId() + "");
            PushUtil.setRetCode(noticeextras, "10509");
            PushUtil.setRetDesc(noticeextras, "已发布的全网公告推送");
            PushUtil.setRetCode(messageextras, "10509");
            PushUtil.setRetDesc(messageextras, "已发布的全网公告推送");
        }

        pushMessage.setMessageextras(messageextras); // 消息参数
        pushMessage.setNoticeextras(noticeextras); // 通知参数

        pushMessage.setScope(SpreadRange.TAG);

        {
            PushRecordModel pushRecordModel = new PushRecordModel();
            pushRecordModel.setTypeId(Integer.parseInt(typeId));
            pushRecordModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
            pushRecordModel.setIsPush(1 + counts);
            HashMap<String, Object> inputs = Maps.newHashMap();
            inputs.put("typeId", typeId);
            inputs.put("currentTime", XdmsConstant.sdf.format(new Date()));
            List<PushRecordModel> lists = pushRecordService.findListByCond(inputs);
            if (lists != null && lists.size() > 0) {
                PushRecordModel prm = lists.get(0);
                prm.setIsPush(prm.getIsPush() + 1);
                pushRecordDao.updateEntityById(prm);
            } else {
                pushRecordDao.addEntity(pushRecordModel);
            }
        }

        PushService.toTagPush(response, pushMessage, tag);
        
        return response;
    }

}
