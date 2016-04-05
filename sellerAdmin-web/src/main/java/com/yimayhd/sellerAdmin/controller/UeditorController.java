package com.yimayhd.sellerAdmin.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.ueditor.ActionEnter;
import com.taobao.common.tfs.TfsManager;
import com.yimayhd.sellerAdmin.base.BaseLineController;

/**
 * 百度编辑器-配置
 * 
 * @author yebin 2015年11月23日
 *
 */
@Controller
@RequestMapping("/ueditor")
public class UeditorController extends BaseLineController {
	@Autowired
	private TfsManager tfsManager;

	@RequestMapping(value = "/controller")
	public @ResponseBody String imageUp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setHeader("Content-Type", "text/html");
		ServletContext application = request.getSession().getServletContext();
		String rootPath = application.getRealPath("/WEB-INF/resources/ueditor/jsp/");
		return new ActionEnter(request, rootPath, tfsManager).exec();
	}
}
