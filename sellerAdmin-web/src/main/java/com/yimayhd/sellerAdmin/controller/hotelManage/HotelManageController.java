package com.yimayhd.sellerAdmin.controller.hotelManage;

import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.model.HotelManage.HotelMessageVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.HtmlUtils;

/**
 * 酒店管理
 * 
 * @author wangdi
 *
 */
//@Controller
@RequestMapping("/hotel")
public class HotelManageController extends BaseController {

	/**
	 * 商品资源信息列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHotelManageList")
	public String queryHotelManageList(Model model,HotelMessageVO hotelMessageVO) throws Exception {
		System.out.println("ddddd");
		return "/system/comm/hotelManage/choicehotel";
	}

	/**
	 * 添加酒店商品
	 * @param model
	 * @return
	 * @throws Exception
     */
	@RequestMapping(value = "/addHotelMessageVOByData")
	public String addHotelMessageVOByData(Model model,HotelMessageVO hotelMessageVO) throws Exception{
		if(hotelMessageVO==null||hotelMessageVO.getHotelId()==null){
			throw new BaseException("酒店资源信息错误,无法添加商品");
		}
		String checkMsg = checkAddHotelMessageVOParam(hotelMessageVO);
		if(StringUtils.isNotBlank(checkMsg)){
			throw new BaseException(checkMsg);
		}

		return "";

	}

	/**
	 * 编辑酒店资源
	 * @param model
	 * @param hotelMessageVO
	 * @return
	 * @throws Exception
     */
	public String editHotelMessageVOByData(Model model,HotelMessageVO hotelMessageVO) throws Exception{
		if(hotelMessageVO==null||hotelMessageVO.getHotelId()==null){
			throw new BaseException("酒店资源信息错误,无法编辑商品");
		}
		return "";
	}

	/**
	 * 查询酒店商品信息详情
	 * @param model
	 * @param hotelMessageVO
     * @return
     */
	public String queryHotelMessageVOyData(Model model,HotelMessageVO hotelMessageVO){
		if(hotelMessageVO==null||hotelMessageVO.getHotelId()==null){
			throw new BaseException("酒店资源信息错误,无法编辑商品");
		}
		return "";

	}

	/**
	 * 添加酒店商品信息验证
	 * @param hotelMessageVO
	 * @return
     */
	public String checkAddHotelMessageVOParam(HotelMessageVO hotelMessageVO){
		if(StringUtils.isBlank(hotelMessageVO.getTitle())){
			return "商品标题为空";
		}
		//退订限制
		//退订规则
		//最晚到点时间
		//提前预定天数
		return null;

	}

	public static void main(String[] args) {

		String str = "hello world <br>";//数据库
		System.out.println("error:"+str);
		String s = HtmlUtils.htmlEscape("hello world <br>");
		System.out.println("处理后:"+s);
		String s2 = HtmlUtils.htmlUnescape(s);
		System.out.println("不转义:"+s2);
	}


}