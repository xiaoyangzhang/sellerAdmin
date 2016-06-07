package com.baidu.ueditor.upload;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;
import com.taobao.common.tfs.TfsManager;

public class BinaryUploader {

	public static final State save(byte[] data, Map<String, Object> conf, TfsManager tfs, String filename,
			String suffix) {
		try {

			if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
				return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
			}
			return StorageManager.saveBinaryFile(data, tfs, filename, suffix);
		} catch (Exception e) {
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	private static boolean validType(String type, String[] allowTypes) {
		List<String> list = Arrays.asList(allowTypes);

		return list.contains(type);
	}
}
