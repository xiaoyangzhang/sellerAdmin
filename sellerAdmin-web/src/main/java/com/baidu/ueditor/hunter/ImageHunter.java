package com.baidu.ueditor.hunter;

import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.MIMEType;
import com.baidu.ueditor.define.MultiState;
import com.baidu.ueditor.define.State;
import com.baidu.ueditor.upload.StorageManager;
import com.taobao.common.tfs.TfsManager;

/**
 * 图片抓取器
 * 
 * @author hancong03@baidu.com
 *
 */
public class ImageHunter {

	private String filename = null;
	private List<String> allowTypes = null;
	private long maxSize = -1;

	private List<String> filters = null;
	private TfsManager tfs = null;

	public ImageHunter(Map<String, Object> conf, TfsManager tfs) {

		this.filename = (String) conf.get("filename");
		this.maxSize = (Long) conf.get("maxSize");
		this.allowTypes = Arrays.asList((String[]) conf.get("allowFiles"));
		this.filters = Arrays.asList((String[]) conf.get("filter"));
		this.tfs = tfs;
	}

	public State capture(String[] list) {

		MultiState state = new MultiState(true);

		for (String source : list) {
			state.addState(captureRemoteData(source));
		}

		return state;

	}

	public State captureRemoteData(String urlStr) {

		HttpURLConnection connection = null;
		URL url = null;
		String suffix = null;
		try {
			url = new URL(urlStr);

			if (!validHost(url.getHost())) {
				return new BaseState(false, AppInfo.PREVENT_HOST);
			}

			connection = (HttpURLConnection) url.openConnection();

			connection.setInstanceFollowRedirects(true);
			connection.setUseCaches(true);

			if (!validContentState(connection.getResponseCode())) {
				return new BaseState(false, AppInfo.CONNECTION_ERROR);
			}

			suffix = MIMEType.getSuffix(connection.getContentType());

			if (!validFileType(suffix)) {
				return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
			}

			if (!validFileSize(connection.getContentLength())) {
				return new BaseState(false, AppInfo.MAX_SIZE);
			}
			return StorageManager.saveFileByInputStream(connection.getInputStream(), tfs, this.filename, suffix);

		} catch (Exception e) {
			return new BaseState(false, AppInfo.REMOTE_FAIL);
		} finally {
			IOUtils.close(connection);
		}
	}

	private boolean validHost(String hostname) {
		try {
			InetAddress ip = InetAddress.getByName(hostname);

			if (ip.isSiteLocalAddress()) {
				return false;
			}
		} catch (UnknownHostException e) {
			return false;
		}

		return !filters.contains(hostname);

	}

	private boolean validContentState(int code) {

		return HttpURLConnection.HTTP_OK == code;

	}

	private boolean validFileType(String type) {

		return this.allowTypes.contains(type);

	}

	private boolean validFileSize(int size) {
		return size < this.maxSize;
	}

}
