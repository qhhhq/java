package net.qhhhq.service.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import net.qhhhq.global.service.Service;
import net.qhhhq.service.common.AppHead;
import net.qhhhq.service.common.HandlerChain;
import net.qhhhq.service.common.SysHead;

public class FileServiceBean implements Service {

	private static Logger log = Logger.getLogger(FileServiceBean.class);

	public void execute(Map<String, Object> paramMap, HandlerChain paramHandlerChain, SysHead sysHead, AppHead appHead,
			JSONObject data) {
		log.info(paramMap);
		if (sysHead.getFileType() == null || sysHead.getFileType().trim().equals("")) {
			sysHead.setFail("000011", "sysHead.FILE_TYPE 文件所属类别不能为空");
		} else {
			String file = (String) paramMap.get("file");
			String filePath = (String) paramMap.get("filePath");
			if (filePath == null) {
				sysHead.setFail("999999", "系统错误，上传失败");
				log.error("文件上传路径为空，系统错误");
				return;
			}
			if (file != null) {
				String suffix = file.substring(file.lastIndexOf("."));
				StringBuffer fileName = new StringBuffer("/");
				fileName.append(sysHead.getFileType()).append("/");
				fileName.append(sysHead.getTranDate().replaceAll("-", "")).append("/");
				File dirFile = new File(filePath + fileName);
				if (!dirFile.exists()) {
					dirFile.mkdirs();
				}
				fileName.append(sysHead.getSeqNo());
				fileName.append(suffix);
				copyFile(file, filePath + fileName);
				delFile(file);
				String deleteFile = (String) paramMap.get("deleteFile");
				if(deleteFile != null && !deleteFile.trim().equals("")) {
					delFile(filePath + deleteFile);
				}
				data.put("fileName", fileName);
			} else {
				sysHead.setFail("000012", "上传文件为空，上传失败");
			}
		}
	}

	/**
     *  删除文件
     *  @param  filePathAndName  String  文件路径及名称  如c:/fqf.txt
     *  @param  fileContent  String
     *  @return  boolean
     */
   private void delFile(String  filePathAndName)  {
       try  {
           String  filePath  =  filePathAndName;
           filePath  =  filePath.toString();
           java.io.File  myDelFile  =  new  java.io.File(filePath);
           myDelFile.delete();
       }
       catch  (Exception  e)  {
           System.out.println("删除文件操作出错");
           e.printStackTrace();

       }
   }

	/**
	 * 复制单个文件
	 *
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	private void copyFile(String oldPath, String newPath) {
		try {
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();

		}

	}
}
