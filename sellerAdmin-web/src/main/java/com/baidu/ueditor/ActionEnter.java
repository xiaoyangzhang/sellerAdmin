package com.baidu.ueditor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.baidu.ueditor.define.ActionMap;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;
import com.baidu.ueditor.hunter.FileManager;
import com.baidu.ueditor.hunter.ImageHunter;
import com.baidu.ueditor.upload.Uploader;
import com.taobao.common.tfs.TfsManager;

public class ActionEnter {

	private HttpServletRequest request = null;

	private String configPath = null;

	private String actionType = null;

	private ConfigManager configManager = null;

	private TfsManager tfsManager = null;

	public ActionEnter(HttpServletRequest request, String configPath, TfsManager tfsManager) {
		this.request = request;
		this.configPath = configPath;
		this.actionType = request.getParameter("action");
		this.configManager = ConfigManager.getInstance(this.configPath);
		this.tfsManager = tfsManager;
	}

	public String exec() {

		String callbackName = this.request.getParameter("callback");

		if (callbackName != null) {

			if (!validCallbackName(callbackName)) {
				return new BaseState(false, AppInfo.ILLEGAL).toJSONString();
			}

			return callbackName + "(" + this.invoke() + ");";

		} else {
			return this.invoke();
		}

	}

	public String invoke() {

		if (actionType == null || !ActionMap.mapping.containsKey(actionType)) {
			return new BaseState(false, AppInfo.INVALID_ACTION).toJSONString();
		}

		if (this.configManager == null || !this.configManager.valid()) {
			return new BaseState(false, AppInfo.CONFIG_ERROR).toJSONString();
		}

		State state = null;

		int actionCode = ActionMap.getType(this.actionType);

		Map<String, Object> conf = null;

		switch (actionCode) {

		case ActionMap.CONFIG:
			return this.configManager.getAllConfig().toString();

		case ActionMap.UPLOAD_IMAGE:
		case ActionMap.UPLOAD_SCRAWL:
		case ActionMap.UPLOAD_VIDEO:
		case ActionMap.UPLOAD_FILE:
			if (!ServletFileUpload.isMultipartContent(request)) {
				state = new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
			} else {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				conf = this.configManager.getConfig(actionCode);
				MultipartFile multipartFile = multipartRequest.getFile(conf.get("fieldName").toString());
				if (multipartFile == null) {
					state = new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
				} else {
					state = new Uploader(multipartFile, conf, tfsManager).doExec();
				}
			}
			break;

		case ActionMap.CATCH_IMAGE:
			conf = configManager.getConfig(actionCode);
			String[] list = this.request.getParameterValues((String) conf.get("fieldName"));
			state = new ImageHunter(conf, tfsManager).capture(list);
			break;

		case ActionMap.LIST_IMAGE:
		case ActionMap.LIST_FILE:
			conf = configManager.getConfig(actionCode);
			int start = this.getStartIndex();
			state = new FileManager(conf).listFile(start);
			break;
		}
		return state.toJSONString();
	}

	public int getStartIndex() {

		String start = this.request.getParameter("start");

		try {
			return Integer.parseInt(start);
		} catch (Exception e) {
			return 0;
		}

	}

	/**
	 * callback参数验证
	 */
	public boolean validCallbackName(String name) {

		if (name.matches("^[a-zA-Z_]+[\\w0-9_]*$")) {
			return true;
		}

		return false;

	}

}