package com.xiaodou.im.agent.huanxin.result;

import java.util.List;

public class UserResult extends BaseResult {

  private String action;
  private String application;
  private String path;
  private String uri;
  private String organization;
  private String applicationName;
  private List<User> entities;

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getApplication() {
    return application;
  }

  public void setApplication(String application) {
    this.application = application;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public String getOrganization() {
    return organization;
  }

  public void setOrganization(String organization) {
    this.organization = organization;
  }

  public String getApplicationName() {
    return applicationName;
  }

  public void setApplicationName(String applicationName) {
    this.applicationName = applicationName;
  }

  public List<User> getEntities() {
    return entities;
  }

  public void setEntities(List<User> entities) {
    this.entities = entities;
  }

  public static class User {
    private String uuid;
    private String type;
    private long created;
    private long modified;
    private String username;
    private boolean activated;

    public String getUuid() {
      return uuid;
    }

    public void setUuid(String uuid) {
      this.uuid = uuid;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public long getCreated() {
      return created;
    }

    public void setCreated(long created) {
      this.created = created;
    }

    public long getModified() {
      return modified;
    }

    public void setModified(long modified) {
      this.modified = modified;
    }

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public boolean isActivated() {
      return activated;
    }

    public void setActivated(boolean activated) {
      this.activated = activated;
    }
  }
}
