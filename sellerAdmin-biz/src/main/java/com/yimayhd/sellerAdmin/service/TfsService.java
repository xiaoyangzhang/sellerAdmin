package com.yimayhd.sellerAdmin.service;

import com.yimayhd.sellerAdmin.base.BaseException;

public interface TfsService {
	/**
	 * 将富文本格式化为Html5页发布到tfs
	 * 
	 * @param body
	 *            富文本
	 * @return tfsCode
	 * @throws BaseException 
	 */
	String publishHtml5(String body) throws BaseException;

	/**
	 * 读取Html5
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	String readHtml5(String code) throws Exception;

	String uploadToTFS(String fileName) throws Exception;
}
