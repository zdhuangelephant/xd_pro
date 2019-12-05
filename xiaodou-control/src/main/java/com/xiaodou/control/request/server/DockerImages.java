package com.xiaodou.control.request.server;

import com.alibaba.fastjson.annotation.JSONField;

public class DockerImages {
	public DockerImages() {
	}
	@JSONField(name = "Id")
	private String id;
	@JSONField(name = "Created")
	private Long created;
	@JSONField(name = "Labels")
	private Labels labels;
	@JSONField(name = "VirtualSize")
	private Long virtualSize;
	@JSONField(name = "ParentId")
	private String parentId;
	@JSONField(name = "Size")
	private Long size;
	@JSONField(name = "RepoTags")
	private String repoTags;
	private String startCommand;
	public String getId() {
		return id;
	}
	public String getRepoTags() {
		return repoTags;
	}
	public void setRepoTags(String repoTags) {
		this.repoTags = repoTags;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getCreated() {
		return created;
	}
	public void setCreated(Long created) {
		this.created = created;
	}
	public Labels getLabels() {
		return labels;
	}
	public void setLabels(Labels labels) {
		this.labels = labels;
	}
	public Long getVirtualSize() {
		return virtualSize;
	}
	public void setVirtualSize(Long virtualSize) {
		this.virtualSize = virtualSize;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public String getStartCommand() {
		return startCommand;
	}
	public void setStartCommand(String startCommand) {
		this.startCommand = startCommand;
	}

	
}
