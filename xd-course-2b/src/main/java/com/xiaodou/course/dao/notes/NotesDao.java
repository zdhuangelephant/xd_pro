package com.xiaodou.course.dao.notes;

import org.springframework.stereotype.Repository;

import com.xiaodou.course.dao.BaseProcessDao;
import com.xiaodou.course.model.notes.NotesModel;

/**
 * @name @see com.xiaodou.course.dao.order.ProductOrderDao.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年1月5日
 * @description 笔记Dao
 * @version 1.0
 */
@Repository("notesDao")
public class NotesDao extends BaseProcessDao<NotesModel> {}
