package com.yimayhd.sellerAdmin.converter;

import com.alibaba.fastjson.JSONObject;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.model.CityActivityItemVO;
import com.yimayhd.sellerAdmin.model.draft.DraftDetailVO;
import com.yimayhd.sellerAdmin.model.line.LineVO;

/**
 * 草稿箱转换器
 * 
 * @author xiemingna
 *
 */
public class DraftConverter {
	public static LineVO toLineVOWithDraftDetailVO(WebResult<DraftDetailVO> draftDetailVOResult) {
		DraftDetailVO detailVO = draftDetailVOResult.getValue();
		String jsonStr = detailVO.getJSONStr();
		jsonStr = jsonStr.replaceAll("\\\\\"", "\"");
		LineVO gt = (LineVO) JSONObject.parseObject(jsonStr, LineVO.class);
		return gt;
	}

	public static CityActivityItemVO toCityactivityWithDraftDetailVO(WebResult<DraftDetailVO> draftDetailVOResult) {
		DraftDetailVO detailVO = draftDetailVOResult.getValue();
		String jsonStr = detailVO.getJSONStr();
		jsonStr = jsonStr.replaceAll("\\\\\"", "\"");
		CityActivityItemVO cityActivityItemVO = (CityActivityItemVO) JSONObject.parseObject(jsonStr, CityActivityItemVO.class);
		return cityActivityItemVO;
	}

}
