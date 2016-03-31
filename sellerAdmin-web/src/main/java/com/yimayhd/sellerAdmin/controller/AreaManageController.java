package com.yimayhd.sellerAdmin.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.sellerAdmin.base.AreaService;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.ResponseVo;
import com.yimayhd.sellerAdmin.model.AreaVO;

@Controller
@RequestMapping("/AREA")
public class AreaManageController extends BaseController {

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public ResponseVo getArea(
			@RequestParam(value = "areaType", required = true) String areaType,
			@RequestParam(value = "areaParentCode", required = false) String areaParentCode) {

		List<AreaVO> list = AreaService.getInstance().getAreaByIDAndType(
				areaType, areaParentCode);
		return new ResponseVo(list);
	}

}
