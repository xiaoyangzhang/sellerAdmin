package com.yimayhd.sellerAdmin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yimayhd.sellerAdmin.constant.ResponseStatus;
import org.apache.commons.lang.StringUtils;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.taobao.common.tfs.TfsManager;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.ResponseVo;
import com.yimayhd.sellerAdmin.util.WebResourceConfigUtil;
import com.yimayhd.sellerAdmin.util.excel.JxlFor2003;
import com.yimayhd.sellerAdmin.util.excel.TestPerson;

/**
 * Created by Administrator on 2015/10/23.
 */
@Controller
@RequestMapping("/upload")
public class UploadController extends BaseController {

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
	public ResponseVo uploadFile(HttpServletRequest request) throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Iterator<String> iterator = multipartRequest.getFileNames();
		MultipartFile multipartFile = multipartRequest.getFile(iterator.next());
		String fileName = multipartFile.getOriginalFilename();
		String suffix = "";
		if (fileName.lastIndexOf(".") != -1) {
			suffix = fileName.substring(fileName.lastIndexOf("."));
		}
		String tfsName = tfsManager.saveFile(multipartFile.getBytes(), null, suffix);
		if(StringUtils.isNotBlank(tfsName) && !"null".equals(tfsName)){
			tfsName += suffix;
		}else{
			return new ResponseVo(ResponseStatus.ERROR.VALUE,"图片上传失败");
		}

		return new ResponseVo(tfsName);
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
	public ResponseVo uploadFiles(HttpServletRequest request) throws Exception {
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
			String tfsName = tfsManager.saveFile(multipartFile.getBytes(), null, suffix) + suffix;
			if(StringUtils.isNotBlank(tfsName) && !"null".equals(tfsName)) {
				// 除去后缀截取十五个字符
				if (fileName.split("\\.")[0].length() > 15) {
					fileName = fileName.split("\\.")[0].substring(0, 15)
							+ (fileName.split("\\.").length > 1 ? "." + fileName.split("\\.")[1] : "");
				}
			}
			map.put(fileName, tfsName);
		}
		return new ResponseVo(map);

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
}
