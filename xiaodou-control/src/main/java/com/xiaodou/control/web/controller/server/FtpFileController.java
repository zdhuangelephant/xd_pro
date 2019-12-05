package com.xiaodou.control.web.controller.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.RandomUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.control.constant.Constant;
import com.xiaodou.control.model.server.FtpFileModel;
import com.xiaodou.control.service.admin.AdminPrivilegeService;
import com.xiaodou.control.service.admin.AdminService;
import com.xiaodou.control.service.facade.MongoDbServiceFacade;
import com.xiaodou.control.service.ftp.FtpService;
import com.xiaodou.control.service.server.BaseNodeService;
import com.xiaodou.control.service.server.NodeService;
import com.xiaodou.control.service.server.ServerGroupService;
import com.xiaodou.control.service.server.ServerService;
import com.xiaodou.control.vo.FtpFileUseVo;
import com.xiaodou.control.web.controller.BaseController;

/**
 * Created by on zhouhuan
 */
@Controller("ftpFileController")
@RequestMapping("/ftpFile")
public class FtpFileController extends BaseController {
	@Resource
	ServerService serverService;
	@Resource
	ServerGroupService serverGroupService;
	@Resource
	BaseNodeService baseNodeService;
	@Resource
	NodeService nodeService;
	@Resource
	FtpService ftpService;
	@Resource
	AdminPrivilegeService adminPrivilegeService;
	@Resource
	AdminService adminService;	
	@Resource
	MongoDbServiceFacade mongoDbServiceFacadeImpl;
	/**
	 * 使用中配置文件列表
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public ModelAndView list(String serverId) {
		ModelAndView modelAndView = new ModelAndView("/server/ftpFileList");
		try {			
			List<FtpFileUseVo> ftpFileUseList=Lists.newArrayList();
			Map<String,Object> cond=Maps.newConcurrentMap();
			cond.put("serverId", serverId);
			List<FtpFileModel> fileList=mongoDbServiceFacadeImpl.getFtpFileListByCond(cond);
			Map<String,String> fileName=Maps.newConcurrentMap();
			for(FtpFileModel f:fileList){
				if(fileName.get(f.getUniqueFileName())!=null){		
				}else{
					fileName.put(f.getUniqueFileName(), f.getUniqueFileName());		
					FtpFileUseVo ftpUse=new FtpFileUseVo();
					ftpUse.setUniqueFileName(f.getUniqueFileName());
					ftpFileUseList.add(ftpUse);
				}
			}
			for(FtpFileUseVo ftpUseVo:ftpFileUseList){
				for(FtpFileModel f:fileList){
					if(ftpUseVo.getUniqueFileName().equals(f.getUniqueFileName())){	
						f.setCreateTime(new Timestamp(Long.valueOf(f.getCreateTime())).toString());
						if(f.getState().equals(Constant.USED)){
							ftpUseVo.setFtpFile(f);
						}	   
						ftpUseVo.getFtpFileList().add(f);	
					}
				}
			}
			modelAndView.addObject("serverId", serverId);
			modelAndView.addObject("ftpFileUseList", ftpFileUseList);
		} catch (Exception e) {		
			LoggerUtil.error("查询列表失败", e);
		}		
		return modelAndView;
	}

	/**
	 * 应用FTP文件
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/useFile")
	@ResponseBody
	public String useFile(String fileId) {
		Map<String, String> map = new HashMap<String, String>();
		if(StringUtils.isNotBlank(fileId)){		
			try {
				
				FtpFileModel file=mongoDbServiceFacadeImpl.getFtpFileById(fileId);
				
				String sourceDir = file.getServerId() + "/allconf";
				String targetDir = file.getServerId() + "/conf";
				ftpService.replaceFile(file.getFileName(), file.getUniqueFileName(), sourceDir, targetDir);
				
				Map<String,Object> cond=Maps.newConcurrentMap();
				cond.put("uniqueFileName", file.getUniqueFileName());
				cond.put("serverId", file.getServerId());
				FtpFileModel fileL=new FtpFileModel();
				fileL.setState(Constant.NO_USED);
				mongoDbServiceFacadeImpl.editFtpFile(cond, fileL);
				
				file.setState(Constant.USED);
				file.setMsg(file.getMsg()+"["+new Date().toString()+":"+this.getUser().getRealName()+"使用了此配置文件]");
				cond.put("fileId", fileId);
				mongoDbServiceFacadeImpl.editFtpFile(cond, file);
				
				map.put("msg", "success");
			} catch (Exception e) {
				map.put("msg", e.toString());
				LoggerUtil.error("使用失败", e);
			}	
		}
		return FastJsonUtil.toJson(map);
	}
	
	/**
	 * 删除服务组类型
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delFile")
	@ResponseBody
	public String delFile(String fileId) {
		Map<String, String> map = new HashMap<String, String>();
		if(StringUtils.isNotBlank(fileId)){		
			try {
				FtpFileModel file=mongoDbServiceFacadeImpl.getFtpFileById(fileId);
			    String dir="/home/vsftpd/" + file.getServerId() + "/";
				if(file.getState().equals(Constant.USED)){
			    	ftpService.deleteFile(dir+"conf/"+file.getUniqueFileName());
			    }
				ftpService.deleteFile(dir+"allconf/"+file.getFileName());
				mongoDbServiceFacadeImpl.delFtpFile(fileId);
				map.put("msg", "success");
			} catch(NullPointerException e){
				mongoDbServiceFacadeImpl.delFtpFile(fileId);
				map.put("msg", "success");
			} catch (Exception e) {
				map.put("msg", e.toString());
				LoggerUtil.error("删除失败", e);
			}	
		}
		return FastJsonUtil.toJson(map);
	}
	
	/**
	 * 下载文件
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/downFile")
	@ResponseBody
	public String downFile(String fileId,
			HttpServletRequest request, HttpServletResponse response){
			try {
				FtpFileModel file=mongoDbServiceFacadeImpl.getFtpFileById(fileId);
				if(file!=null){
					String remotePath = "/"+file.getServerId() + "/allconf/";
					ftpService.downFile(remotePath, file.getFileName(), response,file.getUniqueFileName());
				}
				return null;
			} catch(FileNotFoundException e){
				LoggerUtil.error("文件不存在", e);
				return e.toString();
			}catch (Exception e) {
				LoggerUtil.error("下载文件失败", e);
				return e.toString();
			}
	}
	
	
	/**
	 * 增加配置文件
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addFile")
	@ResponseBody
	public String addFile(String serverId,
			@RequestParam("file") MultipartFile file[]) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String path = serverId + "/allconf/";
			for (int i = 0; i < file.length; i++) {
				Long time=System.currentTimeMillis();
				String uniqueFileName=file[i].getOriginalFilename();
				if(StringUtils.isBlank(uniqueFileName)){
					map.put("msg", "请选择要上传的文件");	
					return  FastJsonUtil.toJson(map);
				}
				String fileName = uniqueFileName+"+"+time.toString();
				ftpService.saveFileFromInputStream(file[i].getInputStream(), path,
						fileName);
				FtpFileModel fileLog=new FtpFileModel();
				fileLog.setFileId(RandomUtil.randomNumber(11).toString());
				fileLog.setUniqueFileName(uniqueFileName);
				fileLog.setFileName(fileName);
				fileLog.setCreateTime(time.toString());
				fileLog.setServerId(serverId);
				fileLog.setUserName(this.getUser().getRealName());
				fileLog.setUserId(this.getUserId().toString());
				fileLog.setState(Constant.NO_USED);
				fileLog.setMsg("["+new Date().toString()+":"+this.getUser().getRealName()+"上传了此配置文件]");
				mongoDbServiceFacadeImpl.addFtpFile(fileLog);
			    this.useFile(fileLog.getFileId());
			}
			map.put("msg", "success");		
		} catch (Exception e) {
			LoggerUtil.error("增加配置文件失败", e);
		}
		return FastJsonUtil.toJson(map);
	}

	
	
	
	
	/**
	 * 下载文件
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/downAllFile")
	@ResponseBody
        public String downAllFile(String serverId,
    			HttpServletRequest request, HttpServletResponse response) throws IOException{
    			try {
					String remotePath = "/"+serverId + "/conf/";
					ftpService.downAllFile(remotePath,request,response);
    				return null;
    			} catch (Exception e) {
    				LoggerUtil.error("下载文件失败", e);
    				return e.toString();
    			}      
    }

}
