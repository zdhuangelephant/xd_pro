package com.xiaodou.st.dataclean.dao.raw;

import org.springframework.stereotype.Repository;

import com.xiaodou.st.dataclean.model.domain.raw.RawDataFaceRecognitionModel;
import com.xiaodou.summer.dao.jdbc.BaseDao;

/**
 * @name @see com.xiaodou.st.dataclean.dao.raw.RawDataFaceRecognitionDao.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月31日
 * @description 原始数据:人脸识别认证数据
 * @version 1.0
 */
@Repository("rawDataFaceRecognitionDao")
public class RawDataFaceRecognitionDao extends BaseDao<RawDataFaceRecognitionModel> {}
