package com.xiaodou.resources.service.quesbk;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.FileUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.resources.dao.quesbk.UserExamTotalMapper;
import com.xiaodou.resources.model.quesbk.UserExamTotal;
import com.xiaodou.resources.request.quesbk.QuesBatchProPojo;
import com.xiaodou.resources.task.quesbk.UserExamRightRank;
import com.xiaodou.resources.task.quesbk.UserExamTotalRank;
import com.xiaodou.summer.sceduling.concurrent.SummerTaskExecutor;
import com.xiaodou.summer.vo.out.ResultInfo;
import com.xiaodou.summer.vo.out.ResultType;

@Service("quesBatchProService")
public class QuesBatchProService {
  @Resource
  SummerTaskExecutor quesbkBatchTaskExecutor;

  @Resource
  UserExamTotalMapper userExamTotalMapper;

  private static final FileUtil _config = FileUtil
      .getInstance("/conf/custom/env/batch_param.properties");

  public ResultInfo batchPro(QuesBatchProPojo pojo) {
    Integer perCount = _config.getPropertiesInt("batch.ques.limit.per");
    if (perCount < 0) perCount = 1000;
    // TODO: 需要根据科目ID查询课程ID,按课程进行处理

    // 查出要进行处理的数据，个数为请求的数量
    Integer userCount = userExamTotalMapper.selectAllUserCountBySubjectId(pojo.getSubject());
    // 求答题量排名 除去user_id = -1 desc(total_ques)
    for (int startPoint = 0; startPoint < userCount; startPoint += perCount - 1) {
      List<UserExamTotal> userByTQList =
          queryNotAndTQAllList(pojo.getSubject(), startPoint, perCount);
      for (int i = 0; i < userByTQList.size(); i++) {
        quesbkBatchTaskExecutor.execute(new UserExamTotalRank(this, userByTQList.get(i), startPoint
            + 1 + i));
      }
    }
    // 求平均正确答题排名 除去user_id = -1 desc(right_ques)
    for (int startPoint = 0; startPoint < userCount; startPoint += perCount - 1) {
      List<UserExamTotal> userByRQList =
          queryNotAllAndRQList(pojo.getSubject(), startPoint, perCount);
      for (int i = 0; i < userByRQList.size(); i++) {
        quesbkBatchTaskExecutor.execute(new UserExamRightRank(this, userByRQList.get(i), startPoint
            + 1 + i));
      }
    }
    return new ResultInfo(ResultType.SUCCESS);
  }

  /**
   * TODO 2、针对单个用户 求答题量排名 除去user_id = -1 desc(total_ques) 求平均正确答题排名 除去user_id = -1 desc(right_ques)
   */
  public void updateNotAllUserTotal(UserExamTotal myExam, Integer myRank) {
    try {
      myExam.setTotalRank(myRank);
      userExamTotalMapper.updateByPrimaryKeySelective(myExam);
    } catch (Exception e) {
      e.printStackTrace();
      LoggerUtil.error("更新失败", e);
    }
  }

  public void updateNotAllUserRight(UserExamTotal myExam, Integer myRank) {
    try {
      myExam.setRightRank(myRank);
      userExamTotalMapper.updateByPrimaryKeySelective(myExam);
    } catch (Exception e) {
      e.printStackTrace();
      LoggerUtil.error("更新失败", e);
    }
  }

  /**
   * 查出除去user_id = -1 的数据根据total_ques排序
   */
  private List<UserExamTotal> queryNotAndTQAllList(String subject, Integer limitStart,
      Integer perCount) {
    Map<String, Object> queryCond = Maps.newHashMap();
    Map<String, Object> input = Maps.newHashMap();
    Map<String, Object> sort = Maps.newHashMap();
    input.put("userId", "-1");
    input.put("courseId", subject);
    sort.put("totalQues", "DESC");
    queryCond.put("input", input);
    queryCond.put("limitStart", limitStart);
    queryCond.put("limitCount", perCount);
    queryCond.put("sort", sort);
    List<UserExamTotal> allList = userExamTotalMapper.selectByUidNotAll(queryCond);
    if (null == allList || allList.size() <= 0) return null;
    return allList;
  }

  /**
   * 查出除去user_id = -1 的数据根据right_ques排序
   */
  private List<UserExamTotal> queryNotAllAndRQList(String subject, Integer limitStart,
      Integer perCount) {
    Map<String, Object> queryCond = Maps.newHashMap();
    Map<String, Object> input = Maps.newHashMap();
    Map<String, Object> sort = Maps.newHashMap();
    input.put("userId", "-1");
    input.put("courseId", subject);
    sort.put("rightQues", "DESC");
    queryCond.put("input", input);
    queryCond.put("limitStart", limitStart);
    queryCond.put("limitCount", perCount);
    queryCond.put("sort", sort);
    List<UserExamTotal> allList = userExamTotalMapper.selectByUidNotAll(queryCond);
    if (null == allList || allList.size() <= 0) return null;
    return allList;
  }

}
