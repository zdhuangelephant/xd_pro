package com.xiaodou.mysqladmin.system.utils;

import java.util.HashSet;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;

@WebListener
public class SessionListener implements HttpSessionListener {
  private Logger log = LoggerFactory.getLogger(SessionListener.class);

  public void sessionCreated(HttpSessionEvent event) {
    HttpSession session = event.getSession();
    ServletContext application = session.getServletContext();

    @SuppressWarnings("unchecked")
    HashSet<HttpSession> sessions = (HashSet<HttpSession>) application.getAttribute("sessions");
    if (sessions == null) {
      sessions = Sets.newHashSet();
      application.setAttribute("sessions", sessions);
    }
    sessions.add(session);
    this.log.info("session：" + session.getId() + " add");
  }

  public void sessionDestroyed(HttpSessionEvent event) {
    HttpSession session = event.getSession();
    ServletContext application = session.getServletContext();
    @SuppressWarnings("unchecked")
    HashSet<HttpSession> sessions = (HashSet<HttpSession>) application.getAttribute("sessions");

    sessions.remove(session);
    this.log.info("session：" + session.getId() + " remove");
  }
}
