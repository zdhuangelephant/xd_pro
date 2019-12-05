package com.xiaodou.control.service.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.xiaodou.control.enums.GroupServiceTypeEnum;
import com.xiaodou.control.prop.FtpProp;
import com.xiaodou.control.request.server.ServerRequest;
import com.xiaodou.control.service.facade.MongoDbServiceFacade;
import com.xiaodou.control.util.FtpUtil;
import com.xiaodou.control.util.compartor.FileCompartor;
import com.xiaodou.control.vo.FtpFileVo;

import edu.emory.mathcs.backport.java.util.Collections;

@Service("ftpService")
public class FtpService {
	@Resource
	MongoDbServiceFacade mongoDbServiceFacadeImpl;
	private FTPClient ftpClient;
	public FtpService() {
	}

	/**
	 * 获取文件列表根据type和地址
	 * @param warAdress war包的文件列表地址
	 * @param type  1本地环境 2线上环境
	 * @author zhouhuan
	 */

	public List<FtpFileVo> getFileList(String warAdress, String type)throws Exception  {
		// TODO Auto-generated method stub
		List<FtpFileVo> fileList = this.getFileNameList(warAdress);
		List<FtpFileVo> fileListNoRelease = Lists.newArrayList();
		if (type.equals(GroupServiceTypeEnum.NativeGroup.getCode())) {
			for (FtpFileVo f : fileList) {
				if (f.getName().indexOf("release") == -1) {
					fileListNoRelease.add(f);
				}
			}
		} else {
			for (int i = fileList.size() - 1; i >= 0; i--) {
				if (fileList.get(i).getName().indexOf("release") == -1) {
					fileList.remove(i);
				}
			}
		}
		return fileListNoRelease;
	}
	
	/**
	 * 下载文件
	 * @author zhouhuan
	 */
	public void downFile(String remotePath,String fileName,HttpServletResponse response,String uniFileName) throws Exception{
		String path = this.downFileLocal(remotePath, fileName);
		if (path != null) {
			InputStream inStream = new FileInputStream(path);
			// 设置输出的格式
			response.reset();
			response.setCharacterEncoding("utf-8");
			response.setContentType("multipart/form-data");
			response.addHeader("Content-Disposition",
					"attachment; filename=\"" + uniFileName + "\"");
			// 循环取出流中的数据
			byte[] b = new byte[100];
			int len;
			if (inStream != null) {
					while ((len = inStream.read(b)) > 0)
						response.getOutputStream().write(b, 0, len);
					inStream.close();
			}
		}
	}
	/**
	 * 获取未生成发行版的文件和生成了发行版的文件
	 * @author zhouhuan
	 */
	public void packageRelease(ServerRequest request,List<FtpFileVo> fileList,List<FtpFileVo> fileListNoRelease )throws Exception{
		if (request.getGroupServiceType().equals(
				GroupServiceTypeEnum.NativeGroup.getCode())) {
			for (FtpFileVo f : fileList) {
				if (f.getName().indexOf("release") == -1) {
					fileListNoRelease.add(f);
				}
			}
		} else {
			for (int i = fileList.size() - 1; i >= 0; i--) {
				if (fileList.get(i).getName().indexOf("release") == -1) {
					fileList.remove(i);
				}
			}
		}
	}
	

	/**
	 * 生成发行版本
	 */
	public boolean createReleaseVersion  (String path, String version)throws Exception {
		boolean result = false;
			/*FTPClientPool pool = (FTPClientPool) SpringContextUtil
					.getBean("ftpClientPool");
			ftpClient = pool.borrowObject();*/
			ftpClient =FtpUtil.getFtpClient();
			String sourceDir = "/home/vsftpd/" + path + "/" + version + "/";
			String targetDir = "/home/vsftpd/" + path + "/" + version
					+ "_release/";
			result = ftpClient.rename(sourceDir, targetDir);
			/* this.copyDirectiory(sourceDir, targetDir, ftpClient); */
			//pool.returnObject(ftpClient);
			FtpUtil.disconnect();
		return result;
	}

	/**
	 * 复制文件夹.
	 * 
	 * @param sourceDir
	 * @param targetDir
	 * @throws IOException
	 */
	public void copyDirectiory(String sourceDir, String targetDir, FTPClient ftp)
			throws Exception {
		// 新建目标目录
		ftp.makeDirectory(targetDir);
		// 获取源文件夹当前下的文件或目录
		// File[] file = (new File(sourceDir)).listFiles();
		FTPFile[] ftpFiles = ftpClient.listFiles(sourceDir);
		for (int i = 0; i < ftpFiles.length; i++) {
			if (ftpFiles[i].isFile()) {
				copyFile(ftpFiles[i].getName(), sourceDir, targetDir, ftp);
			} else if (ftpFiles[i].isDirectory()) {
				copyDirectiory(sourceDir + "/" + ftpFiles[i].getName(),
						targetDir + "/" + ftpFiles[i].getName(), ftp);
			}
		}
	}

	
	public void replaceFile(String sourceFileName,String uniqueFileName,String sourceDir,
			String targetDir)throws Exception {
			/*FTPClientPool pool = (FTPClientPool) SpringContextUtil.getBean("ftpClientPool");
			ftpClient = pool.borrowObject();*/
			ftpClient =FtpUtil.getFtpClient();	
			sourceDir = "/home/vsftpd/" + sourceDir + "/";
			targetDir = "/home/vsftpd/" + targetDir + "/";
			ftpClient.makeDirectory(targetDir);
			ftpClient.deleteFile(targetDir+uniqueFileName);  
			this.copyFile(sourceFileName, sourceDir, targetDir, ftpClient);
			ftpClient.rename(targetDir+sourceFileName,targetDir+uniqueFileName);
			FtpUtil.disconnect();
	}
	
	public void deleteFile(String dir) throws  Exception{
			/*FTPClientPool pool = (FTPClientPool) SpringContextUtil.getBean("ftpClientPool");
			ftpClient = pool.borrowObject();*/
			ftpClient =FtpUtil.getFtpClient();	
			ftpClient.deleteFile(dir);
			FtpUtil.disconnect();
	}
	/**
	 * 复制文件.
	 * 
	 * @param sourceFileName
	 * @param targetFile
	 * @throws IOException
	 */
	public void copyFile(String sourceFileName, String sourceDir,
			String targetDir, FTPClient ftp) throws Exception {
		InputStream is = null;
		try {
			ftp.makeDirectory(targetDir);
			// 变更工作路径
			ftpClient.changeWorkingDirectory(sourceDir);
			// 设置以二进制流的方式传输
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			is = ftpClient.retrieveFileStream(new String(sourceFileName
					.getBytes("GBK"), "iso-8859-1"));
			// 主动调用一次getReply()把接下来的226消费掉. 这样做是可以解决这个返回null问题
			ftpClient.getReply();

			if (is != null) {
				ftpClient.changeWorkingDirectory(targetDir);
				ftpClient.storeFile(new String(sourceFileName.getBytes("GBK"),
						"iso-8859-1"), is);
			}

		} finally {
			// 关闭流
			if (is != null) {
				is.close();
			}
		}
	}

	/**
	 * 保存文件
	 * 
	 * @param stream
	 * @param path
	 * @param filename
	 * @throws IOException
	 */
	public void saveFileFromInputStream(InputStream stream, String path,
			String filename) throws Exception {
			/*FTPClientPool pool = (FTPClientPool) SpringContextUtil.getBean("ftpClientPool");
			ftpClient = pool.borrowObject();*/
			ftpClient =FtpUtil.getFtpClient();	
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE); ///传输文件为流的形式
			ftpClient.changeWorkingDirectory("/home/vsftpd/");
			String[] ftppaths = path.split("/");  
			for(int i=0; i<ftppaths.length; i++) {  
				ftpClient.makeDirectory(ftppaths[i]);  
				ftpClient.changeWorkingDirectory(ftppaths[i]);  
			}  
			ftpClient.enterLocalPassiveMode();
			ftpClient.storeFile(filename, stream);
			stream.close();
			FtpUtil.disconnect();
			//pool.returnObject(ftpClient);
	}

	/**
	 * 获取目录下的文件
	 * 
	 * @param ftpDirectory
	 * @return
	 */
	public List<FtpFileVo> getFileNameList(String path)throws Exception  {
		List<FtpFileVo> list = new ArrayList<FtpFileVo>();
/*			FTPClientPool pool = (FTPClientPool) SpringContextUtil
					.getBean("ftpClientPool");
			ftpClient = pool.borrowObject();*/
			ftpClient =FtpUtil.getFtpClient();
			ftpClient.changeWorkingDirectory("/home/vsftpd/");
			if(ftpClient.changeWorkingDirectory(path)){
				ftpClient.enterLocalPassiveMode();
				FTPFile[] file = ftpClient.listFiles();
				for (FTPFile f : file) {
					FtpFileVo ftpFile = new FtpFileVo();
					ftpFile.setName(f.getName());
					ftpFile.setTime(f.getTimestamp().getTime().toString());
					list.add(ftpFile);
				}
				FileCompartor mc = new FileCompartor();
				Collections.sort(list, mc);
				FtpUtil.disconnect();
			}
		return list;
	}

	/**
	 * 生成发行版本
	 */
	public boolean create(String path, String dir)throws Exception  {
		boolean result = false;
/*			FTPClientPool pool = (FTPClientPool) SpringContextUtil
					.getBean("ftpClientPool");
			ftpClient = pool.borrowObject();*/
			ftpClient =FtpUtil.getFtpClient();
			ftpClient.changeWorkingDirectory("/home/vsftpd/");
			ftpClient.makeDirectory(path + "/");
			ftpClient.changeWorkingDirectory(path + "/");
			if (!dir.equals("")) {
				ftpClient.makeDirectory(dir);
				ftpClient.changeWorkingDirectory(dir);
			}
			FtpUtil.disconnect();
			//pool.returnObject(ftpClient);
		return result;
	}

	/**
	 * Description: 从FTP服务器下载文件
	 * 
	 * @Version1.0 zhouhuan 2016/11/08
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param remotePath
	 *            FTP服务器上的相对路径
	 * @param fileName
	 *            要下载的文件名
	 * @param localPath
	 *            下载后保存到本地的路径
	 * @return
	 */
	public String downFileLocal(String remotePath, String fileName) throws Exception {
			String localPath = FtpProp.getParams("downPath");
			ftpClient =FtpUtil.getFtpClient();
			String filePath = localPath + "/" + fileName;
			String path="/home/vsftpd/"+remotePath;
			ftpClient.changeWorkingDirectory(path);
			ftpClient.enterLocalPassiveMode();
			FTPFile[] file = ftpClient.listFiles();
			for (FTPFile ff : file) {
				if (ff.getName().equals(fileName)) {
					File localFile = new File(filePath);
					if(!localFile.exists()){
						localFile.createNewFile();
					}
					OutputStream is = new FileOutputStream(localFile);
					ftpClient.retrieveFile(ff.getName(), is);
					is.close();
				}
			}
			FtpUtil.disconnect();
			return filePath;
	}

	
	/**
	 * Description: 从FTP服务器下载文件
	 * 
	 * @Version1.0 zhouhuan 2016/11/08
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param remotePath
	 *            FTP服务器上的相对路径
	 * @param fileName
	 *            要下载的文件名
	 * @param localPath
	 *            下载后保存到本地的路径
	 * @return
	 */
	public List<String> downAllFile(String remotePath) throws Exception {
			String localPath = FtpProp.getParams("downPath");
			List<String> pathList=Lists.newArrayList();
			ftpClient =FtpUtil.getFtpClient();
			
			String path="/home/vsftpd/"+remotePath;
			ftpClient.changeWorkingDirectory(path);
			ftpClient.enterLocalPassiveMode();
			FTPFile[] file = ftpClient.listFiles();
			for (FTPFile ff : file) {
				    String filePath = localPath + "/"+ff.getName();
					File localFile = new File(filePath);
					if(!localFile.exists()){
						localFile.createNewFile();
					}
					OutputStream is = new FileOutputStream(localFile);
					ftpClient.retrieveFile(ff.getName(), is);
					pathList.add(filePath);
					is.flush();
					is.close();
			}
			FtpUtil.disconnect();
			return pathList;
	}
	
	@SuppressWarnings("resource")
	public void downAllFile(String remotePath,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		// TODO Auto-generated method stub
		//压缩文件初始设置
		String path = FtpProp.getParams("downPath");
        String baseName = "file";
        String fileZip = baseName + ".zip"; // 拼接zip文件
        String zipPath = path+"/" + fileZip;//之后用来生成zip文件
        List<String> pathLists=this.downAllFile(remotePath);

        response.setContentType("APPLICATION/OCTET-STREAM");  
        response.setHeader("Content-Disposition","attachment; filename="+fileZip);
        ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
        try {
        	for(String s:pathLists){
        		this.doCompress(s, out);
        		response.flushBuffer();
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            out.close();
        }
	}
	
	 public  void doCompress(String srcFile, String zipFile) throws IOException {
	        this.doCompress(new File(srcFile), new File(zipFile));
	    }
	    
	    /**
	     * 文件压缩
	     * @param srcFile 目录或者单个文件
	     * @param zipFile 压缩后的ZIP文件
	     */
	    public  void doCompress(File srcFile, File zipFile) throws IOException {
	        ZipOutputStream out = null;
	        try {
	            out = new ZipOutputStream(new FileOutputStream(zipFile));
	            this.doCompress(srcFile, out);
	        } catch (Exception e) {
	            throw e;
	        } finally {
	            out.close();//记得关闭资源
	        }
	    }
	    
	    public void doCompress(String filelName, ZipOutputStream out) throws IOException{
	        doCompress(new File(filelName), out);
	    }
	    
	    public  void doCompress(File file, ZipOutputStream out) throws IOException{
	        doCompress(file, out, "");
	    }
	    
	    public  void doCompress(File inFile, ZipOutputStream out, String dir) throws IOException {
	        if ( inFile.isDirectory() ) {
	            File[] files = inFile.listFiles();
	            if (files!=null && files.length>0) {
	                for (File file : files) {
	                    String name = inFile.getName();
	                    if (!"".equals(dir)) {
	                        name = dir + "/" + name;
	                    }
	                    this.doCompress(file, out, name);
	                }
	            }
	        } else {
	             this.doZip(inFile, out, dir);
	        }
	    }
	    
	    public  void doZip(File inFile, ZipOutputStream out, String dir) throws IOException {
	        String entryName = null;
	        if (!"".equals(dir)) {
	            entryName = dir + "/" + inFile.getName();
	        } else {
	            entryName = inFile.getName();
	        }
	        ZipEntry entry = new ZipEntry(entryName);
	        out.putNextEntry(entry);
	        
	        int len = 0 ;
	        byte[] buffer = new byte[1024];
	        FileInputStream fis = new FileInputStream(inFile);
	        while ((len = fis.read(buffer)) > 0) {
	            out.write(buffer, 0, len);
	            out.flush();
	        }
	        out.closeEntry();
	        fis.close();
	    }
	
}

