package com.yimayhd.sellerAdmin.controller.about;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.biz.UserBiz;
import com.yimayhd.sellerAdmin.checker.UserChecker;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.converter.UserConverter;
import com.yimayhd.sellerAdmin.model.User;
import com.yimayhd.sellerAdmin.model.vo.user.LoginVo;
import com.yimayhd.sellerAdmin.model.vo.user.ModifyPasswordVo;
import com.yimayhd.sellerAdmin.model.vo.user.RegisterVo;
import com.yimayhd.sellerAdmin.model.vo.user.RetrievePasswordVo;
import com.yimayhd.sellerAdmin.url.UrlHelper;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.dto.ChangePasswordDTO;
import com.yimayhd.user.client.dto.LoginDTO;
import com.yimayhd.user.client.dto.RegisterDTO;
import com.yimayhd.user.client.dto.RevivePasswordDTO;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.result.login.LoginResult;
import com.yimayhd.user.client.service.UserService;
import com.yimayhd.user.session.manager.SessionHelper;
import com.yimayhd.user.session.manager.SessionManager;
import com.yimayhd.user.session.manager.VerifyCodeManager;
import com.yimayhd.user.session.manager.annot.SessionChecker;

/**
 * 
 * @author wzf
 *
 */
@RestController
@RequestMapping("/about")
public class AboutController extends BaseController {

	
	@RequestMapping(value = "/service", method = RequestMethod.GET) 
	public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/system/user/service");
	}
	
}
