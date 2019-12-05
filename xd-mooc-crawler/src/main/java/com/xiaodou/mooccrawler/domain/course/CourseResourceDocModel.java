package com.xiaodou.mooccrawler.domain.course;

/**
 * 
 * @ClassName: DocModel
 * @Description: 资源类型之一:文档Model
 * @author zhaoxu.yang
 * @date 2015年4月12日 上午10:57:55
 */
public class CourseResourceDocModel {

	// 文档ID
	private Integer id;

  // 课程Id
  private Integer courseId;

	// 文档所属的大纲章节ID
	private Integer chapterId;

	// 文档名称
	private String name;

	// 文档所在的URL地址
	private String url;

  // 文件下载地址
  private String fileUrl;

	// 文档详细描述
	private String detail;

	// 关键词
	private String keyPoint;

  // 章节名称
  private String chapterName;

  // 状态
  private Integer status;

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Integer getCourseId() {
    return courseId;
  }

  public void setCourseId(Integer courseId) {
    this.courseId = courseId;
  }

  public String getFileUrl() {
    return fileUrl;
  }

  public void setFileUrl(String fileUrl) {
    this.fileUrl = fileUrl;
  }

  public String getChapterName() {
    return chapterName;
  }

  public void setChapterName(String chapterName) {
    this.chapterName = chapterName;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getChapterId() {
    return chapterId;
  }

  public void setChapterId(Integer chapterId) {
    this.chapterId = chapterId;
  }

  public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

  public String getKeyPoint() {
    return keyPoint;
  }

  public void setKeyPoint(String keyPoint) {
    this.keyPoint = keyPoint;
  }
}
