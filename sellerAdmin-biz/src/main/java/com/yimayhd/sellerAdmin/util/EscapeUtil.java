package com.yimayhd.sellerAdmin.util;

import org.apache.commons.lang.StringUtils;

public class EscapeUtil {
	public static final String baseEscapeHtml(String text) {
		if (StringUtils.isBlank(text)) {
			return text;
		} else {
			return text.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		}
	}

	public static void main(String[] args) {
		System.out.println(EscapeUtil.baseEscapeHtml("<script>"));
	}
}
