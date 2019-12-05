package com.xiaodou.jmsg.dao;

import org.springframework.stereotype.Repository;

import com.xiaodou.jmsg.entity.sqlite.FailSendMessage;

@Repository("failSendMessageDao")
public class FailSendMessageDao extends SqliteBaseDao<FailSendMessage> {}
