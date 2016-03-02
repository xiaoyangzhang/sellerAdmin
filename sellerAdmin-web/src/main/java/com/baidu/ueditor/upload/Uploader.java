package com.baidu.ueditor.upload;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;
import com.taobao.common.tfs.TfsManager;

public class Uploader {
	private MultipartFile file = null;
	private Map<String, Object> conf = null;
	private TfsManager tfsManager = null;

	public Uploader(MultipartFile file, Map<String, Object> conf, TfsManager tfsManager) {
		this.file = file;
		this.conf = conf;
		this.tfsManager = tfsManager;
	}

	public final State doExec() {
		State state = null;
		if (this.file == null) {
			return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
		}
		try {
			if ("true".equals(this.conf.get("isBase64"))) {
				String filename = (String) conf.get("filename");
				String suffix = FileType.getSuffix("JPG");
				state = Base64Uploader.save(new String(file.getBytes()), this.conf, tfsManager, filename, suffix);
			} else {
				String filename = file.getOriginalFilename();
				String suffix = FileType.getSuffixByFilename(filename);
				filename = filename.substring(0, filename.length() - suffix.length());
				state = BinaryUploader.save(file.getBytes(), this.conf, tfsManager, filename, suffix);
			}
		} catch (IOException e) {
			state = new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
		}
		return state;
	}
}
