package com.xiaodou.course.model.notes;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.tool.MybatisXmlTool;
import com.xiaodou.common.annotation.GeneralField;

import lombok.Data;

@Data
public class NotesModel {
	/** id 主键 */
	@Column(isMajor = true)
	private String id;
	private String noteId;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String courseId;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String chapterId;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String itemId;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String content;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String source;// 来源章节名称
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String resourcesName;// 资源名称
	@GeneralField
	@Column(canUpdate = false, sortBy = true)
	private Timestamp createTime;
	@GeneralField
	@Column(canUpdate = true, sortBy = false)
	private String userId;
	@Column(canUpdate = false, sortBy = false)
	private String typeCode;
	private String productCategoryName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNoteId() {
		return noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getChapterId() {
		return chapterId;
	}

	public void setChapterId(String chapterId) {
		this.chapterId = chapterId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getResourcesName() {
		return resourcesName;
	}

	public void setResourcesName(String resourcesName) {
		this.resourcesName = resourcesName;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getProductCategoryName() {
		return productCategoryName;
	}

	public void setProductCategoryName(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}

	public static void main(String[] args) {
		MybatisXmlTool.getInstance(NotesModel.class, "xd_user_notes",
				"E:/work3/xd-course/src/main/resources/conf/mybatis/notes/")
				.buildXml();
	}

}
