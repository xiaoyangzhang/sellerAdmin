package com.yimayhd.sellerAdmin.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yimayhd.membercenter.client.domain.talent.TalentInfoDO;
import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.client.dto.TalentInfoDTO;
import com.yimayhd.membercenter.client.query.InfoQueryDTO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.client.service.examine.ExamineDealService;
import com.yimayhd.membercenter.enums.ExamineStatus;
import com.yimayhd.membercenter.enums.MerchantType;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.biz.TalentBiz;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.helper.UrlHelper;
import com.yimayhd.user.client.domain.MerchantDO;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.service.MerchantService;
import com.yimayhd.user.session.manager.SessionManager;

public class BasicInfoAuthorityInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger("BasicInfoAuthorityInterceptor");
	@Autowired
	private SessionManager sessionManager;
	@Value("${sellerAdmin.rootPath}")
	private String rootPath;
	@Autowired
	private TalentBiz talentBiz;
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private ExamineDealService examineDealService;
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		UserDO userDO = sessionManager.getUser(request);
		InfoQueryDTO info = new InfoQueryDTO();
		info.setDomainId(Constant.DOMAIN_JIUXIU);
		info.setSellerId(userDO.getId());
		MemResult<ExamineInfoDTO> result = examineDealService.queryMerchantExamineInfoBySellerId(info);
		if (result != null && result.getValue() !=null ) {
			ExamineInfoDTO examineInfoDTO = result.getValue();
			if (examineInfoDTO.getExaminStatus() != ExamineStatus.EXAMIN_OK.getStatus()) {
				response.sendRedirect(UrlHelper.getUrl(rootPath, "/apply/toChoosePage"));
				return false;
			}
			if (examineInfoDTO.getType() == MerchantType.TALENT.getType()) {
				/*if (examineInfoDTO.getExaminStatus() != ExamineStatus.EXAMIN_OK.getStatus()) {
					response.sendRedirect(UrlHelper.getUrl(rootPath, "/apply/toChoosePage"));
					return false;
				}*/
				WebResult<TalentInfoDTO> dtoResult = talentBiz.queryTalentInfoByUserId();
				TalentInfoDTO talentInfoDTO = dtoResult.getValue();
				TalentInfoDO talentInfoDO = talentInfoDTO.getTalentInfoDO();

				if (StringUtils.isBlank(talentInfoDO.getAvatar()) || StringUtils.isBlank(talentInfoDO.getReallyName()) || StringUtils.isBlank(talentInfoDO.getServeDesc()) ||
						talentInfoDO.getCityCode() <= 0 || talentInfoDO.getProvinceCode() <= 0 || talentInfoDO.getBirthday() == null ||
						talentInfoDO.getGender() <= 0 || CollectionUtils.isEmpty(talentInfoDO.getPictures()) ||
						CollectionUtils.isEmpty(talentInfoDO.getServiceTypes()) || talentInfoDTO.getPictureTextDTO() == null ) {
					response.sendRedirect(UrlHelper.getUrl(rootPath, "/basicInfo/talent/toAddTalentInfo"));
					return false;
				}
			}
			if (examineInfoDTO.getType() == MerchantType.MERCHANT.getType()) {
				
				BaseResult<MerchantDO> meResult = merchantService.getMerchantBySellerId(userDO.getId(), Constant.DOMAIN_JIUXIU);
				if (meResult != null && meResult.getValue() != null) {
					/*if (examineInfoDTO.getExaminStatus() != ExamineStatus.EXAMIN_OK.getStatus()) {
						response.sendRedirect(UrlHelper.getUrl(rootPath, "/apply/toChoosePage"));
						return false;
					}*/
					if (StringUtils.isBlank(meResult.getValue().getBackgroudImage()) || StringUtils.isBlank(meResult.getValue().getServiceTel()) || StringUtils.isBlank(meResult.getValue().getLogo()) ) {
						response.sendRedirect(UrlHelper.getUrl(rootPath, "/basicInfo/merchant/toAddBasicPage"));
						return false;
					}
				}
			}
		}
//		String url = UrlHelper.getUrl( rootPath, "/error/lackPermission") ;
//		response.sendRedirect(url);
		return true;
	}
}
