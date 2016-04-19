package com.yimayhd.sellerAdmin.url;

import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.enums.UserOptions;

public class AuthorityHelper {
	/**
	 * 如果是达人、或者商户，直接进入信息编辑页面
	 * @param userDO
	 * @return
	 */
	public static String getRedirectUrl(UserDO userDO) {
		if (userDO == null) {
			return null;
		}
		long option = userDO.getOptions();
		boolean isTalentA = UserOptions.CERTIFICATED.has(option);
		boolean isTalentB = UserOptions.USER_TALENT.has(option);

		boolean isMerchant = UserOptions.COMMERCIAL_TENANT.has(option);
		if (isTalentA || isTalentB) {
			return "/basicInfo/talent/toAddTalentInfo";
		} else if (isMerchant) {
			return "/basicInfo/merchant/toAddBasicPage";
		}
		return null;
	}
}
