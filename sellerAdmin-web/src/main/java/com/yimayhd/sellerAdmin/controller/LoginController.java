package com.yimayhd.sellerAdmin.controller;


import com.alibaba.fastjson.JSON;
import com.yimayhd.membercenter.client.domain.HaMenuDO;
import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.client.query.InfoQueryDTO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.enums.MerchantType;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.ResponseVo;
import com.yimayhd.sellerAdmin.biz.MerchantBiz;
import com.yimayhd.sellerAdmin.cache.CacheManager;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.constant.ResponseStatus;
import com.yimayhd.sellerAdmin.controller.loginout.vo.LoginoutVO;
import com.yimayhd.sellerAdmin.helper.UrlHelper;
import com.yimayhd.sellerAdmin.util.CalendarField;
import com.yimayhd.sellerAdmin.util.DateUtil;
import com.yimayhd.sellerAdmin.util.WebResourceConfigUtil;
import com.yimayhd.user.client.domain.MerchantDO;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.dto.LoginDTO;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.result.login.LoginResult;
import com.yimayhd.user.client.service.MerchantService;
import com.yimayhd.user.client.service.UserService;
import com.yimayhd.user.session.manager.JsonResult;
import com.yimayhd.user.session.manager.SessionManager;
import com.yimayhd.user.session.manager.VerifyCodeManager;
import net.pocrd.entity.AbstractReturnCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/10/23.
 */
@Controller
@RequestMapping
public class LoginController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private UserService userServiceRef;

    @Resource
    private VerifyCodeManager verifyCodeManager;

    @Autowired
    private com.yimayhd.sellerAdmin.service.UserService userService;
    @Autowired
    private SessionManager sessionManager;


    @Autowired
    private CacheManager cacheManager;

    @Resource
    private MerchantService merchantService;

    @Autowired
    private MerchantBiz merchantBiz;

    @Value("{sellerAdmin.bufferDay}")
    private String bufferDays;

    @Value("sellerAdmin.default_contract_date")
    private String DEFAULT_CONTRACT_DATE;

    private long DAY = 86400;

    /**
     * 登录页面
     *
     * @return 登录页面
     * @throws Exception
     */
    @RequestMapping(value = "/toLogin", method = RequestMethod.GET)
    public String toLogin() throws Exception {
        return "/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo login(LoginoutVO loginoutVO, HttpServletRequest request, HttpServletResponse response) {
        int errorCode = 0;
        ResponseVo responseVo = new ResponseVo();
        if (StringUtils.isEmpty(loginoutVO.getUsername()) || StringUtils.isEmpty(loginoutVO.getPassword())) {
            responseVo.setStatus(ResponseStatus.INVALID_DATA.VALUE);
            responseVo.setMessage(ResponseStatus.INVALID_DATA.MESSAGE);
            return responseVo;
        }
        try {
            LOGGER.info("login loginoutVO= {}", JSON.toJSONString(loginoutVO));
            sessionManager.removeToken(request);
            LoginDTO loginDTO = new LoginDTO();
            loginDTO.setMobile(loginoutVO.getUsername().trim());
            loginDTO.setPassword(loginoutVO.getPassword());
            LoginResult result = userServiceRef.loginV3(loginDTO);
            errorCode = result.getErrorCode();
            if (Integer.valueOf(AbstractReturnCode._C_SUCCESS).equals(Integer.valueOf(errorCode))) {
                LOGGER.info("loginoutVO= {} login success and userId = {}", loginoutVO, result.getValue());
                String token = result.getToken();
                Cookie cookie = new Cookie("token", token);
                cookie.setHttpOnly(true);
                response.addCookie(cookie);
                return new ResponseVo(result.getResultMsg());
            }
            LOGGER.info("loginoutVO= {} login fail and msg = {}", loginoutVO, result.getResultMsg());
            responseVo.setStatus(ResponseStatus.ERROR.VALUE);
            responseVo.setMessage(result.getResultMsg());
            return responseVo;
        } catch (Throwable e) {
            LOGGER.error("loginoutVO= {} login fail and msg = {}", JSON.toJSONString(e));
            responseVo.setStatus(ResponseStatus.ERROR.VALUE);
            responseVo.setMessage(ResponseStatus.ERROR.MESSAGE);
            return responseVo;
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVo logout(HttpServletRequest request, Model model) {
        sessionManager.removeToken(request);
        return new ResponseVo();
    }


    @RequestMapping("/validateCode")
    @ResponseBody
    public JsonResult validateCode(LoginoutVO loginoutVO) {
        LOGGER.info("validateCode loginoutVO= {}", loginoutVO);

        String verifyCode = loginoutVO.getVerifyCode();
        boolean checkResult = verifyCodeManager.checkVerifyCode(verifyCode);
        if (!checkResult) {
            LOGGER.warn("loginoutVO.getVerifyCode() = {} is not correct", loginoutVO.getVerifyCode());
            return JsonResult.buildFailResult(1, "验证码错误!", null);
        }

        LOGGER.info("validateCode success loginoutVO= {}", loginoutVO);
        return JsonResult.buildSuccessResult("", null);
    }

    /**
     * 登录后的成功页
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String toMain(Model model) throws Exception {
        UserDO user = sessionManager.getUser();
        List<HaMenuDO> haMenuDOList = userService.getMenuListByUserId(user.getId());
        InfoQueryDTO info = new InfoQueryDTO();
        info.setDomainId(Constant.DOMAIN_JIUXIU);
        info.setSellerId(user.getId());
        MemResult<ExamineInfoDTO> merchantInfoResult = merchantBiz.queryMerchantExamineInfoBySellerId(info);
        if (merchantInfoResult == null || !merchantInfoResult.isSuccess() || merchantInfoResult.getValue().getType() != MerchantType.MERCHANT.getType()) {
            String url = UrlHelper.getUrl(WebResourceConfigUtil.getRootPath(), "/error/lackPermission");
        }

        BaseResult<MerchantDO> meResult = merchantService.getMerchantBySellerId(user.getId(), Constant.DOMAIN_JIUXIU);
        if(meResult==null||!meResult.isSuccess()) {
            String url = UrlHelper.getUrl(WebResourceConfigUtil.getRootPath(), "/error/lackPermission");
        }
//        if (checkContractDate(meResult.getValue().getContractEndTime(), user.getId())) {
//            String renew = "1";
//            model.addAttribute("renewContract", renew);
//        }
        model.addAttribute("menuList", haMenuDOList);
        model.addAttribute("userNickName", user.getNickname());
        return "/layout/layout";
    }

    /**
     * 登录后的欢迎页
     *
     * @throws Exception
     */
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcome() throws Exception {
        return "/system/welcome";
    }

    /**
     * 没有权限页面
     *
     * @return 错误页面
     * @throws Exception
     */
    @RequestMapping(value = "/user/noPower", method = RequestMethod.GET)
    public String toNoPower(Model model, String urlName) throws Exception {
        model.addAttribute("message", "没有" + urlName + "权限，请联系管理员");
        return "/error";
    }

}
