package com.yimayhd.sellerAdmin.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
===========EMS============={"gyCode":"EMS","id":30,"name":"EMS","tcCode":"ems"}=========================
===========百世汇通============={"gyCode":"HTKY","id":118,"name":"百世汇通","tcCode":"huitongkuaidi"}=========================
===========申通快递============={"gyCode":"STO","id":313,"name":"申通快递","tcCode":"shentong"}=========================
===========顺丰速运============={"gyCode":"SF","id":321,"name":"顺丰速运","tcCode":"shunfeng"}=========================
===========天天快递============={"gyCode":"TTKD","id":334,"name":"天天快递","tcCode":"tiantian"}=========================
===========圆通============={"gyCode":"YTO","id":397,"name":"圆通速递","tcCode":"yuantong"}=========================
===========韵达快递============={"gyCode":"YUNDA","id":410,"name":"韵达快递","tcCode":"yunda"}=========================
===========中通快递============={"gyCode":"ZTO","id":426,"name":"中通快递","tcCode":"zhongtong"}=========================
 * @author Administrator
 *
 */
public class ExpressUtils {

	static Map<String,String> map = new HashMap<String,String>();
	static{
		map.put("ems", "EMS");
		map.put("huitongkuaidi", "顺丰速运");
		map.put("shunfeng", "百世汇通");
		map.put("shentong", "申通快递");
		map.put("shunfeng", "顺丰速运");
		map.put("tiantian", "天天快递");
		map.put("yuantong", "圆通速递");
		map.put("yunda", "韵达快递");
		map.put("zhongtong", "中通快递");
	}
	
	public static String getExpressNameByCode(String code){
		if(StringUtils.isBlank(code)){
			return "";
		}
		return map.get(code);
	}
	
	public static void main(String args[]){
		ExpressUtils expressUtils = new ExpressUtils();
		System.out.print(expressUtils.getExpressNameByCode("shunfeng"));
	}
}
