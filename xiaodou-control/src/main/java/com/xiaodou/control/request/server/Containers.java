package com.xiaodou.control.request.server;

import com.alibaba.fastjson.annotation.JSONField;

public class Containers {
	public Containers() {
	}
	@JSONField(name = "Id")
	private String id;
	@JSONField(name = "Created")
	private Long created;
	@JSONField(name = "Labels")
	private Labels labels;
	@JSONField(name = "Status")
	private String status;
	@JSONField(name = "Image")
	private String image;
	@JSONField(name = "Command")
	private String command;
	@JSONField(name = "Names")
	private String names;
	@JSONField(name = "Ports")
	private Ports ports;
	public String getId() {
		return id;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getNames() {
		return names;
	}
	public void setNames(String names) {
		this.names = names;
	}
	public Ports getPorts() {
		return ports;
	}
	public void setPorts(Ports ports) {
		this.ports = ports;
	}
	
}
