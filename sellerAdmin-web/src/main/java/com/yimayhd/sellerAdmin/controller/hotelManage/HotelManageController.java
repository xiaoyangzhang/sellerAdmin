package com.yimayhd.sellerAdmin.controller.hotelManage;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.RoomDO;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.Paginator;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.model.HotelManage.HotelMessageVO;
import com.yimayhd.sellerAdmin.model.HotelManage.MultiChoice;
import com.yimayhd.sellerAdmin.model.HotelManage.RoomMessageVO;
import com.yimayhd.sellerAdmin.model.HotelManage.SupplierCalendarTemplate;
import com.yimayhd.sellerAdmin.service.hotelManage.HotelManageService;
import com.yimayhd.sellerAdmin.util.CommonJsonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.HtmlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 酒店管理
 * 
 * @author wangdi
 *
 */
@Controller
@RequestMapping("/hotel")
public class HotelManageController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(HotelManageController.class);
	private final Integer pageNo=8;

	@Autowired
	private HotelManageService hotelManageService;

	/**
	 * 选择列表
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/initHotelManage")
	public String queryHotelManageList(Model model) throws Exception {
		HotelMessageVO hotelMessageVO = new  HotelMessageVO();
		List<MultiChoice> multiChoiceList = initMultiChoiceList(null);
		model.addAttribute("hotelMessageVO", hotelMessageVO);
		model.addAttribute("multiChoiceList",multiChoiceList);// 最晚到店时间列表
		return "/system/comm/hotelManage/addhotel";
	}

	/**
	 * 商品资源信息列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHotelManageList", method = RequestMethod.GET)
	public String queryHotelManageList(Model model,HotelMessageVO hotelMessageVO) throws Exception {
		long userId = sessionManager.getUserId() ;
		hotelMessageVO.setSellerId(userId);
		hotelMessageVO.setPageSize(8);
		//hotelMessageVO.setPageNo(pageNo);//
		WebResult<PageVO<HotelMessageVO>> result= hotelManageService.queryHotelMessageVOListByData(hotelMessageVO);
		if(!result.isSuccess()){
			logger.error("查询列表失败");
			return "/error";
		}
		PageVO<HotelMessageVO> pageResult = result.getValue();
		List<HotelMessageVO> hotelMessageVOList = pageResult.getResultList();
		int totalPage = 0;
		if (pageResult.getTotalCount()%pageResult.getPageSize() > 0) {
			totalPage += pageResult.getTotalCount()/pageResult.getPageSize()+1;
		}else {
			totalPage += pageResult.getTotalCount()/pageResult.getPageSize();
		}

		model.addAttribute("pageVo", pageResult);
		model.addAttribute("hotelMessageVOList", hotelMessageVOList);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("pageNo", pageResult.getPaginator().getPage());
		model.addAttribute("pageSize",8);
		model.addAttribute("totalCount", pageResult.getPaginator().getTotalItems());
		return "/system/comm/hotelManage/searchhotel";
	}

	/**
	 * 酒店房型列表
	 * @param model
	 * @param hotelId
     * @return
     */
/*	@RequestMapping(value = "/queryRoomTypeListByData")
	public String queryRoomTypeListByData(Model model,Long hotelId ){
		if(hotelId==null){
			logger.error("酒店pid不能为空");
			return "/error";
		}
		HotelMessageVO hotelMessageVO = new HotelMessageVO();
		hotelMessageVO.setHotelId(hotelId);
		WebResult<List<RoomMessageVO>> result = hotelManageService.queryRoomTypeListByData(hotelMessageVO);
		if(!result.isSuccess()){
			logger.error("查询房型信息失败");
			return "/error";
		}
		model.addAttribute("roomList", result.getValue());
		return "/system/comm/hotelManage/addhotel";
	}*/


	/**
	 * 添加酒店商品
	 * @param model
	 * @return
	 * @throws Exception
     */
	@RequestMapping(value = "/addHotelMessageVOByData")
	public WebResult<String> addHotelMessageVOByData(Model model,HotelMessageVO hotelMessageVO) throws Exception{
		WebResult<String> message = new WebResult<String>();
		if(hotelMessageVO==null||hotelMessageVO.getHotelId()==null){
			message.initFailure(WebReturnCode.PARAM_ERROR,"酒店资源信息错误,无法添加商品");
			return message;
		}
		/**必要参数验证**/
		String checkMsg = checkAddHotelMessageVOParam(hotelMessageVO);
		if(StringUtils.isNotBlank(checkMsg)){
			message.initFailure(WebReturnCode.PARAM_ERROR,checkMsg);
			return message;
		}

		WebResult<HotelMessageVO> result = hotelManageService.addHotelMessageVOByData(hotelMessageVO);
		if(!result.isSuccess()){
			message.initFailure(WebReturnCode.PARAM_ERROR,"添加商品错误");
			return message;
		}
		/**最晚到店时间**/
		List<MultiChoice> multiChoiceList = initMultiChoiceList(hotelMessageVO);

		model.addAttribute("multiChoiceList",multiChoiceList);// 最晚到店时间列表
		model.addAttribute("hotelMessageVO", result.getValue());
		return message;

	}



	/**
	 * 编辑酒店资源
	 * @param model
	 * @param hotelMessageVO
	 * @return
	 * @throws Exception
     */
	/*public String editHotelMessageVOByData(Model model,HotelMessageVO hotelMessageVO) throws Exception{
		if(hotelMessageVO==null||hotelMessageVO.getHotelId()==null){
			//throw new BaseException("酒店资源信息错误,无法编辑商品");
		}
		WebResult<Long> result = hotelManageService.editHotelMessageVOByData(hotelMessageVO);
		if(!result.isSuccess()){
			//
		}
		model.addAttribute("itemId", result.getValue());
		return "";
	}*/

	/**
	 * 查询酒店商品信息详情
	 * @param model
	 * @param hotelMessageVO
     * @return
     */
	/*public String queryHotelMessageVOyData(Model model,HotelMessageVO hotelMessageVO){
		if(hotelMessageVO==null||hotelMessageVO.getHotelId()==null){
			//throw new BaseException("酒店资源信息错误,无法编辑商品");
		}
		WebResult<HotelMessageVO> result =hotelManageService.queryHotelMessageVOyData(hotelMessageVO);
		if(!result.isSuccess()){

		}
		model.addAttribute("hotelMessageVO", result.getValue());
		return "";

	}*/
	/**
	 * 初始化最晚到店时间
	 * @return
	 */
	public List<MultiChoice> initMultiChoiceList(HotelMessageVO hotelMessageVO){
		List<Integer> choiseTime = new ArrayList<Integer>();
		if(hotelMessageVO!=null&&hotelMessageVO.getLatestCheckin().size()>0){
			choiseTime = hotelMessageVO.getLatestCheckin();
		}
		List<MultiChoice> multiChoiceList = new ArrayList<MultiChoice>();
		for(int i=0;i<24;i++){
			MultiChoice multiChoice = new MultiChoice();
			multiChoice.setId(i);//id
			multiChoice.setTitle("时间");
			multiChoice.settValue(i);
			multiChoice.setValue(i+":00");
			multiChoice.setChoice(false);
			if(!CollectionUtils.isEmpty(choiseTime)){
				for (int time :choiseTime){
					if(time == i){
						/**设置选中标识**/
						multiChoice.setChoice(true);
					}
				}
			}
			multiChoiceList.add(multiChoice);

		}
		return multiChoiceList;
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
		if(hotelMessageVO.getCode()==null){
			return "商品代码不能为空";
		}
		if(hotelMessageVO.getPayType()==null){
			return "付款方式不能为空";
		}
		if(hotelMessageVO.getCancelLimit()==null){
			return "退订限制为空";
		}
		if(StringUtils.isBlank(hotelMessageVO.getDescription())){
			return "退订规则不能为空";
		}
		if(StringUtils.isBlank(hotelMessageVO.getStoreLastTime())){
			return "最晚到店时间不能为空";
		}
		if(hotelMessageVO.getStartBookTimeLimit()==null){
			return "提前预定天数不能为空";
		}

		return null;

	}


	public static void main(String[] args) {

		String str = "hello world <br>";//数据库
		System.out.println("error:"+str);
		String s = HtmlUtils.htmlEscape("hello world <br>");
		System.out.println("处理后:"+s);
		String s2 = HtmlUtils.htmlUnescape(s);
		System.out.println("不转义:"+s2);
		String json = "{\"supplier_calendar\":{\"seller_id\":\"2088102122524333\",\"hotel_id\":\"123\",\"biz_list\":[{\"sku_id\":10012,\"state\":\"update\",\"stock_num\":10,\"price\":\"8.8\",\"vTxt\":\"2088101117955611\"},{\"sku_id\":10012,\"state\":\"update\",\"stock_num\":10,\"price\":\"8.8\",\"vTxt\":\"2088101117955611\"}]}}";
		String json2="{\"seller_id\":\"2088102122524333\",\"hotel_id\":\"123\",\"biz_list\":[{\"sku_id\":10012,\"state\":\"update\",\"stock_num\":10,\"price\":\"8.8\",\"vTxt\":\"2088101117955611\"},{\"sku_id\":10012,\"state\":\"update\",\"stock_num\":10,\"price\":\"8.8\",\"vTxt\":\"2088101117955611\"}]}";
		JSONObject jsonObject = JSONObject.parseObject(json) ;
		JSONArray jsonArray = jsonObject.getJSONArray("biz_list");
		String supplier_calendar = jsonObject.get("supplier_calendar").toString();
		System.out.println(supplier_calendar);

		SupplierCalendarTemplate template = (SupplierCalendarTemplate)CommonJsonUtil.jsonToObject(json2, SupplierCalendarTemplate.class);
		System.out.println(template.getHotel_id());

	}

	public HotelManageService getHotelManageService() {
		return hotelManageService;
	}

	public void setHotelManageService(HotelManageService hotelManageService) {
		this.hotelManageService = hotelManageService;
	}
}