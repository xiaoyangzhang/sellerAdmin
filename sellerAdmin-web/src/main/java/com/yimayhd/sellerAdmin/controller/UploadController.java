package com.yimayhd.sellerAdmin.controller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.velocity.tools.generic.ClassTool.Sub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.taobao.common.tfs.TfsManager;
import com.taobao.common.tfs.impl.TfsFile;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.ResponseVo;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.util.WebResourceConfigUtil;
import com.yimayhd.sellerAdmin.util.excel.JxlFor2003;
import com.yimayhd.sellerAdmin.util.excel.TestPerson;

/**
 * Created by Administrator on 2015/10/23.
 */
@Controller
@RequestMapping("/upload")
public class UploadController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger("UploadController");

	@Autowired
	private TfsManager tfsManager;

	/**
	 * 上传页面
	 * 
	 * @return 上传页面
	 * @throws Exception
	 */
	@RequestMapping(value = "/toUpload", method = RequestMethod.GET)
	public String toUpload(HttpServletRequest request) throws Exception {
		request.setAttribute("hello", new Date());
		return "/demo/upload";
	}

	/**
	 * 上传页面
	 * 
	 * @return 上传页面
	 * @throws Exception
	 */
	@RequestMapping(value = "/toUpload2", method = RequestMethod.GET)
	public String toUpload2(HttpServletRequest request) throws Exception {
		request.setAttribute("hello", new Date());
		return "/demo/upload2";
	}

	/**
	 * 上传单个文件
	 * 
	 * @return 文件名称
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/file", method = RequestMethod.POST)
	public WebResult<String> uploadFile(HttpServletRequest request) throws Exception {
		WebResult<String> result = upload(request);
		return result ;
//		try {
//			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//			Iterator<String> iterator = multipartRequest.getFileNames();
//			MultipartFile multipartFile = multipartRequest.getFile(iterator.next());
//			if( multipartFile.getSize() <=0 ){
//				return null;
//			}
//			
//			String fileName = multipartFile.getOriginalFilename();
//			String suffix = "";
//			if (fileName.lastIndexOf(".") != -1) {
//				suffix = fileName.substring(fileName.lastIndexOf("."));
//			}
//			String tfsName = tfsManager.saveFile(multipartFile.getBytes(), null, suffix);
//			if (StringUtils.isNotBlank(tfsName) && !"null".equals(tfsName)) {
//				tfsName += suffix;
//			} else {
//				return WebResult.failure(WebReturnCode.SYSTEM_ERROR, "上传失败");
//			}
//			return WebResult.success(tfsName);
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//			return WebResult.failure(WebReturnCode.SYSTEM_ERROR, "上传失败");
//		}
	}
	
    
	

    @RequestMapping(value = "/file_string", method = RequestMethod.POST)
    public void uploadFile4String(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	response.setHeader("Content-Type", "text/html; charset=UTF-8");  
    	response.setHeader("Accept-Charset:", "UTF-8");  
    	WebResult<String> result = upload(request);
    	OutputStream op = response.getOutputStream();  
    	op.write(JSON.toJSONString(result).getBytes());
    	op.close();  
    	response.flushBuffer(); 
    }
	
	
	/**
	 * 上传多个文件
	 * 
	 * @param request
	 * @return 文件名称数组
	 * @throws Exception
	 */
	@RequestMapping("/files")
	@ResponseBody
	public WebResult<Map<String, String>> uploadFiles(HttpServletRequest request) throws Exception {
		try {
			Map<String, String> map = new HashMap<String, String>();
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Iterator<String> iterator = multipartRequest.getFileNames();
			while (iterator.hasNext()) {
				MultipartFile multipartFile = multipartRequest.getFile(iterator.next());
				String fileName = multipartFile.getOriginalFilename();
				String suffix = "";
				if (fileName.lastIndexOf(".") != -1) {
					suffix = fileName.substring(fileName.lastIndexOf("."));
				}
				suffix = suffix.toLowerCase();
				String tfsName = tfsManager.saveFile(multipartFile.getBytes(), null, suffix) + suffix;
				if (StringUtils.isNotBlank(tfsName) && !"null".equals(tfsName)) {
					// 除去后缀截取十五个字符
					if (fileName.split("\\.")[0].length() > 15) {
						fileName = fileName.split("\\.")[0].substring(0, 15)
								+ (fileName.split("\\.").length > 1 ? "." + fileName.split("\\.")[1] : "");
					}
				}
				map.put(fileName, tfsName);
			}
			return WebResult.success(map);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return WebResult.failure(WebReturnCode.SYSTEM_ERROR, "上传失败");
		}
	}

	/**
	 * 富文本编辑上传单个文件
	 * 
	 * @param request
	 * @return 文件地址
	 * @throws Exception
	 */
	@RequestMapping("/ckeditorFile")
	@ResponseBody
	public Object ckeditorFile(HttpServletRequest request) throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Iterator<String> iterator = multipartRequest.getFileNames();
		MultipartFile multipartFile = multipartRequest.getFile(iterator.next());
		String tfsName = WebResourceConfigUtil.getTfsRootPath()
				+ tfsManager.saveFile(multipartFile.getBytes(), null, null);
		return tfsName;

	}

	/**
	 * 上传文件（富文本编辑）
	 * 
	 * @return 上传文件
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/ckeditor", method = RequestMethod.POST)
	public ResponseVo uploadCKEditor(@RequestParam(value = "activityDetailWeb", required = false) String detail,
			@RequestParam(value = "activityDetailApp", required = false) String detaill) throws Exception {
		// 保存文件到tfs
		String encodeHtml = "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />";
		String detailTfs = tfsManager.saveFile((encodeHtml + detail).getBytes("utf-8"), null, "html");
		// 返回文件名
		return new ResponseVo(detailTfs);
	}

	// TODO test
	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<TestPerson> testPersonList = new ArrayList<TestPerson>();
		for (int i = 0; i < 5; i++) {
			TestPerson testPersonData = new TestPerson();
			testPersonData.setId(i);
			testPersonData.setName("张三" + i);
			testPersonData.setAge(i * 2);
			testPersonList.add(testPersonData);
		}
		List<BasicNameValuePair> headList = new ArrayList<BasicNameValuePair>();
		headList.add(new BasicNameValuePair("id", "编号"));
		headList.add(new BasicNameValuePair("name", "名称"));
		JxlFor2003.exportExcel(response, "测试.xls", testPersonList, headList);

	}
	

    private static final String DOT = ".";
    private static final int MAX_LENGTH = 4 * 1024 * 1024 ; 
    
	
	private  WebResult<String> upload(HttpServletRequest request) {
		WebResult<String> result = new WebResult<String>();
		try{
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			
			
			Iterator<String> iterator = multipartRequest.getFileNames();
			MultipartFile multipartFile = multipartRequest.getFile(iterator.next());
			String fileName = multipartFile.getOriginalFilename();
			long size = multipartFile.getSize();
			if (size > MAX_LENGTH) {
				result.setWebReturnCode(WebReturnCode.FILE_TO_BIG);
				return result;
			}
			
			String tfsName = upload(fileName, multipartFile.getBytes(), size);
			if (tfsName == null) {// 上传失败
				result.setWebReturnCode(WebReturnCode.UPLOAD_FILE_FAILED);
				return result;
			}
			result.setValue(tfsName);
			
		}catch(Exception e){
			logger.error("upload failed! ", e);
			result.setWebReturnCode(WebReturnCode.UPLOAD_FILE_FAILED);
		}
		return result;
	}
	
    private String upload(String fileName, byte[] bytes, long size){
    	String log = "UUID="+UUID.randomUUID()+"  filename="+fileName+"  size="+size ;
    	logger.info(log);
		int index = fileName.lastIndexOf(DOT);
		String suffix = index != -1 ? fileName.substring(index, fileName.length()) : null;
		if (StringUtils.isBlank(suffix)) {
			suffix = ".jpg";
		}
		suffix = suffix.toLowerCase();
		String tfsName = null;
		try {
			if (size > MAX_LENGTH) {
				return null;
			} else if (size > TfsFile.MAX_SMALL_FILE_LENGTH && size < MAX_LENGTH) {
				String key = String.valueOf(System.currentTimeMillis());
				tfsName = tfsManager.saveLargeFile(bytes, null, suffix, key);
			} else {
				tfsName = tfsManager.saveFile(bytes, null, suffix);
			}

		} catch (Exception e) {
			logger.error("upload  filename={}  size=", fileName, size, e);
		}
		if (tfsName == null) {// 上传失败
			return null;
		}
		String targetFileName = tfsName + suffix;
		
		logger.info(log+"  result="+targetFileName);
		return targetFileName;
    }
	
}
