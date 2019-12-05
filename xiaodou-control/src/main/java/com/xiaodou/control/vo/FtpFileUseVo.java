package com.xiaodou.control.vo;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.control.model.server.FtpFileModel;

public class FtpFileUseVo {
	private String uniqueFileName;
	private FtpFileModel ftpFile;
	private List<FtpFileModel> ftpFileList=Lists.newArrayList();
	public String getUniqueFileName() {
		return uniqueFileName;
	}
	public void setUniqueFileName(String uniqueFileName) {
		this.uniqueFileName = uniqueFileName;
	}
	public FtpFileModel getFtpFile() {
		return ftpFile;
	}
	public void setFtpFile(FtpFileModel ftpFile) {
		this.ftpFile = ftpFile;
	}
	public List<FtpFileModel> getFtpFileList() {
		return ftpFileList;
	}
	public void setFtpFileList(List<FtpFileModel> ftpFileList) {
		this.ftpFileList = ftpFileList;
	}
}