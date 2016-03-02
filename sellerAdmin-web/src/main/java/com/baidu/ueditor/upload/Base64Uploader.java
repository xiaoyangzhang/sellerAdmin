package com.baidu.ueditor.upload;

import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;
import com.taobao.common.tfs.TfsManager;

public final class Base64Uploader {

	public static State save(String content, Map<String, Object> conf, TfsManager tfs, String filename, String suffix) {
		byte[] data = decode(content);
		long maxSize = ((Long) conf.get("maxSize")).longValue();
		if (!validSize(data, maxSize)) {
			return new BaseState(false, AppInfo.MAX_SIZE);
		}
		return StorageManager.saveBinaryFile(data, tfs, filename, suffix);
	}

	private static byte[] decode(String content) {
		return Base64.decodeBase64(content);
	}

	private static boolean validSize(byte[] data, long length) {
		return data.length <= length;
	}

}