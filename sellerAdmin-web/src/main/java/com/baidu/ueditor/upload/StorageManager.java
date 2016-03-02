package com.baidu.ueditor.upload;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;
import com.taobao.common.tfs.TfsManager;
import com.yimayhd.sellerAdmin.util.WebResourceConfigUtil;

public class StorageManager {
	public static final int BUFFER_SIZE = 8192;

	public StorageManager() {
	}

	public static State saveBinaryFile(byte[] data, TfsManager tfs, String filename, String suffix) {
		String tfsName = tfs.saveFile(data, null, suffix);
		State state = new BaseState(true, tfsName);
		state.putInfo("size", data.length);
		state.putInfo("title", filename);
		if (state.isSuccess()) {
			state.putInfo("url", WebResourceConfigUtil.getTfsRootPath() + tfsName);
			state.putInfo("type", suffix);
			state.putInfo("original", filename);
		}
		return state;
	}

	public static State saveFileByInputStream(InputStream is, TfsManager tfs, String filename, String suffix) {
		try {
			return saveBinaryFile(IOUtils.toByteArray(is), tfs, filename, suffix);
		} catch (IOException e) {
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}
}
