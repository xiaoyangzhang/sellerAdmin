package com.yimayhd.sellerAdmin.controller.hotelManage;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.RoomDO;
import com.yimayhd.ic.client.model.domain.item.ItemSkuDO;
import com.yimayhd.membercenter.client.result.MemResultSupport;
import com.yimayhd.membercenter.client.service.MerchantItemCategoryService;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.Paginator;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebResultSupport;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.cache.CacheManager;
import com.yimayhd.sellerAdmin.constant.Constant;
import com.yimayhd.sellerAdmin.enums.ItemCodeEnum;
import com.yimayhd.sellerAdmin.helper.UrlHelper;
import com.yimayhd.sellerAdmin.model.HotelManage.*;
import com.yimayhd.sellerAdmin.service.hotelManage.HotelManageService;
import com.yimayhd.sellerAdmin.util.CommonJsonUtil;
import com.yimayhd.sellerAdmin.util.DateCommon;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * 酒店管理
 * 
 * @author wangdi
 *
 */
@Controller
@RequestMapping("/hotel")
public class HotelManageController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger("hotelManage-business.log");
	private static final String UPDATE="update";
	//private static final long categoryId=231;
	private static final int[] lastTimeArr = {12,13,14,15,16,17,18,19,20,21,22,23,24,1,2,3,4,5,6};
	private static final String DATE_END=":00";

	@Autowired
	private HotelManageService hotelManageService;
	@Autowired
	private CacheManager cacheManager ;
	@Autowired
	private MerchantItemCategoryService merchantItemCategoryService;

	@Value("${sellerAdmin.rootPath}")
	private String rootPath;

	/**
	 * 选择列表
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addHotelMessageVOByDataView")
	public String addHotelMessageVOByDataView(Model model,@RequestParam(required = true) long categoryId) throws Exception {
		HotelMessageVO hotelMessageVO = new  HotelMessageVO();
		if(categoryId==0){
			return "/system/error/404";
		}
		long userId = sessionManager.getUserId() ;
		MemResultSupport memResultSupport =merchantItemCategoryService.checkCategoryPrivilege(Constant.DOMAIN_JIUXIU, categoryId, userId);
		if(!memResultSupport.isSuccess()){
			return "/system/error/lackPermission";
		}
		hotelMessageVO.setSellerId(userId);
		hotelMessageVO.setCategoryId(categoryId);
		List<MultiChoice> multiChoiceList = initMultiChoiceList(null);
		model.addAttribute("hotelMessageVO", hotelMessageVO);
		model.addAttribute("multiChoiceList",multiChoiceList);// 最晚到店时间列表
		model.addAttribute("categoryId",categoryId);//
		model.addAttribute("itemId", 0);
		model.addAttribute("UUID", UUID.randomUUID().toString());
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
		System.out.println("userID:"+userId);
		//hotelMessageVO.setPageSize(PAGESIZE);
		//hotelMessageVO.setPageNo(pageNo);//
		WebResult<PageVO<HotelMessageVO>> result= hotelManageService.queryHotelMessageVOListByData(hotelMessageVO);
		if(!result.isSuccess()){
			logger.error("查询列表失败");
			model.addAttribute("errorInfo","查询列表失败");
			return "/system/comm/hotelManage/searchhotel";
		}
		PageVO<HotelMessageVO> pageResult = result.getValue();
		List<HotelMessageVO> hotelMessageVOList = pageResult.getResultList();
		int totalPage = 0;
		if (pageResult.getTotalCount()%pageResult.getPageSize() > 0) {
			totalPage += pageResult.getTotalCount()/pageResult.getPageSize()+1;
		}else {
			totalPage += pageResult.getTotalCount()/pageResult.getPageSize();
		}
		model.addAttribute("errorInfo","");
		model.addAttribute("pageVo", pageResult);
		model.addAttribute("hotelMessageVOList", hotelMessageVOList);
		model.addAttribute("totalPage", totalPage);
		//model.addAttribute("page", pageResult.getPaginator().getPage());
		//model.addAttribute("pageSize",PAGESIZE);
		model.addAttribute("totalCount", pageResult.getPaginator().getTotalItems());
		return "/system/comm/hotelManage/searchhotel";
	}

	/**
	 * 酒店房型列表
	 * @param model
	 * @param hotelId
     * @return
     */
	@RequestMapping(value = "/queryRoomTypeListByData")
	public String queryRoomTypeListByData(Model model,Long hotelId ){
		String returnUrl = "/system/comm/hotelManage/hotelRoomList";
		if(hotelId==null){
			logger.error("酒店pid不能为空");
			model.addAttribute("errorInfo", "酒店pid不能为空");
			return returnUrl;
		}
		HotelMessageVO hotelMessageVO = new HotelMessageVO();
		hotelMessageVO.setHotelId(hotelId);
		WebResult<List<RoomMessageVO>> result = hotelManageService.queryRoomTypeListByData(hotelMessageVO);
		if(!result.isSuccess()){
			logger.error("查询房型信息失败");
			model.addAttribute("errorInfo", "查询房型信息失败");
			return returnUrl;
		}
		model.addAttribute("roomList", result.getValue());

		return returnUrl;
	}


	/**
	 * 添加酒店商品
	 * @param model
	 * @return
	 * @throws Exception
     */
	@RequestMapping(value = "/addHotelMessageVOByData",method = RequestMethod.POST)
	@ResponseBody
	public WebResult<String> addHotelMessageVOByData(Model model, HotelMessageVO hotelMessageVO) throws Exception{
		WebResult<String> message = new WebResult<String>();
		if(hotelMessageVO==null||hotelMessageVO.getHotelId()==0){
			message.initFailure(WebReturnCode.PARAM_ERROR,"酒店资源信息错误,无法添加商品");
			return message;
		}
		/**必要参数验证**/
		String checkMsg = checkAddHotelMessageVOParam(hotelMessageVO);
		if(StringUtils.isNotBlank(checkMsg)){
			message.initFailure(WebReturnCode.PARAM_ERROR,checkMsg);
			return message;
		}

		//hotelMessageVO.setBreakfast(1);
		//hotelMessageVO.setCategoryId(categoryId);// 测试放开
		boolean rs = cacheManager.addToTair(Constant.UUIDKEY+hotelMessageVO.getUUID(), true , 2, 10*60*60);
		if(!rs){
			message.initFailure(WebReturnCode.SYSTEM_ERROR,"请不要重复提交");
			return message;
		}

		WebResult<HotelMessageVO> result = hotelManageService.addHotelMessageVOByData(hotelMessageVO);
		if(!result.isSuccess()){
			message.initFailure(WebReturnCode.PARAM_ERROR,"添加商品错误");
			return message;
		}
		/**最晚到店时间**/
		List<MultiChoice> multiChoiceList = initMultiChoiceList(hotelMessageVO);
		model.addAttribute("hotelMessageVO",hotelMessageVO);
		model.addAttribute("multiChoiceList",multiChoiceList);// 最晚到店时间列表
		model.addAttribute("hotelMessageVO", result.getValue());
		String url = UrlHelper.getUrl(rootPath, "/item/list") ;
		message.setValue(url);
		return message;

	}


	/**
	 * 编辑初始页面
	 * @param model
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editHotelMessageView" )
	public String editHotelMessageView(Model model,@RequestParam(required = true) long categoryId,
									               @RequestParam(required = true) long itemId,
									               @RequestParam(required = true) String operationFlag) throws Exception {
		HotelMessageVO hotelMessageVO = new HotelMessageVO();
		String systemLog = ItemCodeEnum.SYS_START_LOG.getDesc();
		long currentUserId = sessionManager.getUserId() ;
		hotelMessageVO.setSellerId(currentUserId);
		hotelMessageVO.setCategoryId(categoryId);
		hotelMessageVO.setItemId(itemId);
		if(hotelMessageVO==null){
			// "编辑商品信息错误";
			log.warn("编辑商品信息错误");
			throw new BaseException("编辑商品信息错误");
		}
		if(hotelMessageVO.getItemId()==0){
			// "编辑商品ID错误";
			log.warn("编辑商品ID错误");
			throw new BaseException("编辑商品ID错误");
		}
		if(hotelMessageVO.getCategoryId()==0){
			// "商品类目ID错误";
			log.warn("商品类目ID错误");
			throw new BaseException("商品类目ID错误");
		}
		WebResult<HotelMessageVO> webResult = hotelManageService.queryHotelMessageVOyData(hotelMessageVO);
		if(!webResult.isSuccess()){
			// "商品类目ID错误";
			systemLog=webResult.getResultMsg();
		}
		hotelMessageVO = webResult.getValue();
		if(currentUserId>0&&currentUserId!=hotelMessageVO.getSellerId()){
			return "/system/error/lackPermission";
		}
		List<MultiChoice> multiChoiceList = initMultiChoiceList(webResult.getValue());
		model.addAttribute("hotelMessageVO", hotelMessageVO);
		model.addAttribute("multiChoiceList",multiChoiceList);// 最晚到店时间列表
		model.addAttribute("systemLog",systemLog);//
		model.addAttribute("categoryId",categoryId);//
		model.addAttribute("itemId", itemId);
		model.addAttribute("roomMessageVO", hotelMessageVO.getRoomMessageVO());
		model.addAttribute("roomList", hotelMessageVO.getListRoomMessageVO());
		model.addAttribute("UUID",UUID.randomUUID().toString());
		if(operationFlag.equals(UPDATE)){
			model.addAttribute("operationFlag",operationFlag);//操作标示
			return "/system/comm/hotelManage/addhotel";
		}else{
			return "/system/comm/hotelManage/hoteldetails";
		}



	}
	/**
	 * 编辑酒店资源
	 * @param model
	 * @param hotelMessageVO
	 * @return
	 * @throws Exception
     */
	@RequestMapping(value = "/editHotelMessageVOByData",method = RequestMethod.POST)
	@ResponseBody
	public WebResult<String> editHotelMessageVOByData(Model model,HotelMessageVO hotelMessageVO) throws Exception{
		String systemLog = ItemCodeEnum.SYS_START_LOG.getDesc();
		hotelMessageVO.setCurrentState(UPDATE);

		WebResult<String> message = new WebResult<String>();
		if(hotelMessageVO==null||hotelMessageVO.getHotelId()==0){
			message.initFailure(WebReturnCode.PARAM_ERROR,"酒店资源信息错误,无法编辑商品");
			log.error("editHotelMessageVOByData.酒店资源信息错误,无法编辑商品");
			return message;
		}
		if(hotelMessageVO.getItemId()==0){
			message.initFailure(WebReturnCode.PARAM_ERROR,"无效商品ID,无法编辑商品");
			log.error("editHotelMessageVOByData.无效商品ID,无法编辑商品");
			return message;
		}
		/**必要参数验证**/
		String checkMsg = checkAddHotelMessageVOParam(hotelMessageVO);
		if(StringUtils.isNotBlank(checkMsg)){
			message.initFailure(WebReturnCode.PARAM_ERROR,checkMsg);
			log.error("editHotelMessageVOByData."+checkMsg);
			return message;
		}
		boolean rs = cacheManager.addToTair(Constant.UUIDKEY+hotelMessageVO.getUUID(), true , 2, 10*60*60);
		if(!rs){
			message.initFailure(WebReturnCode.SYSTEM_ERROR,"请不要重复提交");
			return message;
		}
		logger.info("editHotelMessageVOByData:start 入参: itemId={},hotelMessageVO={}",hotelMessageVO.getItemId(),CommonJsonUtil.objectToJson(hotelMessageVO,HotelMessageVO.class));
		WebResult<Long> result = hotelManageService.editHotelMessageVOByData(hotelMessageVO);
		if(!result.isSuccess()){
			message.initFailure(WebReturnCode.PARAM_ERROR,"编辑商品错误");
			log.error("editHotelMessageVOByData.添加商品错误");
			return message;
		}
		logger.info("editHotelMessageVOByData: end 回参:webResult="+result.isSuccess());
		/**最晚到店时间**/
		List<MultiChoice> multiChoiceList = initMultiChoiceList(hotelMessageVO);
		model.addAttribute("itemId", result.getValue());
		model.addAttribute("multiChoiceList",multiChoiceList);// 最晚到店时间列表
		model.addAttribute("hotelMessageVO", result.getValue());
		String url = UrlHelper.getUrl(rootPath, "/item/list") ;
		message.setValue(url);
		return message;

	}



	/**
	 * 初始化最晚到店时间
	 * @return
	 */
	public List<MultiChoice> initMultiChoiceList(HotelMessageVO hotelMessageVO){
		List<String> choiseTime = new ArrayList<String>();
		if(hotelMessageVO!=null&&hotelMessageVO.getLatestArriveTime().size()>0){
			choiseTime = hotelMessageVO.getLatestArriveTime();
		}
		List<MultiChoice> multiChoiceList = new ArrayList<MultiChoice>();
		for(int i=0;i<lastTimeArr.length;i++){
			MultiChoice multiChoice = new MultiChoice();
			multiChoice.setId(lastTimeArr[i]);//id
			multiChoice.setTitle("时间");
			multiChoice.setTValue(lastTimeArr[i]);
			multiChoice.setValue(lastTimeArr[i]+DATE_END);
			multiChoice.setStrDate(lastTimeArr[i]+DATE_END);
			int currentTime = lastTimeArr[i];
			if(currentTime<12){
				currentTime+=24;
				multiChoice.setStrDate(currentTime+DATE_END);//传给中台日期时间,次日加24
			}
			multiChoice.setChoiceNo(false);
			if(!CollectionUtils.isEmpty(choiseTime)){
				for (String time :choiseTime){
					if(multiChoice.getStrDate().equals(time)){
						/**设置选中标识**/
						multiChoice.setChoiceNo(true);
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
		if(hotelMessageVO.getPayType()==0){
			return "付款方式不能为空";
		}
		if(hotelMessageVO.getCancelLimit()==0){
			return "退订限制为空";
		}

		if(StringUtils.isBlank(hotelMessageVO.getDescription())){
			return "退订规则不能为空";
		}
		if(StringUtils.isBlank(hotelMessageVO.getStoreLastTime())){
			return "最晚到店时间不能为空";
		}
		/*if(hotelMessageVO.getStartBookTimeLimit()==0){
			return "提前预定天数不能为空";
		}*/
		if(hotelMessageVO.getRoomId()==0){
			return "房型信息不能为空";
		}
		if(StringUtils.isBlank(hotelMessageVO.getUUID())){
			return "UUID不能为空 ";
		}

		return null;

	}



	public HotelManageService getHotelManageService() {
		return hotelManageService;
	}

	public void setHotelManageService(HotelManageService hotelManageService) {
		this.hotelManageService = hotelManageService;
	}

	public MerchantItemCategoryService getMerchantItemCategoryService() {
		return merchantItemCategoryService;
	}

	public void setMerchantItemCategoryService(MerchantItemCategoryService merchantItemCategoryService) {
		this.merchantItemCategoryService = merchantItemCategoryService;
	}

	public CacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public static void main(String[] args) {

		String str = "hello world <br>";//数据库
		System.out.println("error:"+str);
		String s = HtmlUtils.htmlEscape("hello world <br>");
		System.out.println("处理后:"+s);
		String s2 = HtmlUtils.htmlUnescape(s);
		System.out.println("不转义:"+s2);
		String json = "{\"supplier_calendar\":{\"seller_id\":\"2088102122524333\",\"hotel_id\":\"123\",\"bizSkuInfo\":[{\"sku_id\":10012,\"state\":\"update\",\"stock_num\":10,\"price\":\"8.8\",\"vTxt\":\"2088101117955611\"},{\"sku_id\":10012,\"state\":\"update\",\"stock_num\":10,\"price\":\"8.8\",\"vTxt\":\"2088101117955611\"}]}}";
		String json2="{\"seller_id\":\"2088102122524333\",\"hotel_id\":\"123\",\"bizSkuInfo\":[{\"sku_id\":10012,\"state\":\"add\",\"stock_num\":10,\"price\":\"8.8\",\"vTxt\":\"2088101117955611\"},{\"sku_id\":10012,\"state\":\"update\",\"stock_num\":10,\"price\":\"8.8\",\"vTxt\":\"2088101117955611\"},\n" +
				"{\"sku_id\":10012,\"state\":\"delete\",\"stock_num\":10,\"price\":\"8.8\",\"vTxt\":\"2088101117955611\"},\n" +
				"{\"sku_id\":10012,\"state\":\"bb\",\"stock_num\":10,\"price\":\"8.8\",\"vTxt\":\"2088101117955611\"},\n" +
				"{\"sku_id\":10012,\"state\":\"delete\",\"stock_num\":10,\"price\":\"8.8\",\"vTxt\":\"2088101117955611\"}]}";
		JSONObject jsonObject = JSONObject.parseObject(json) ;
		JSONArray jsonArray = jsonObject.getJSONArray("biz_list");
		String supplier_calendar = jsonObject.get("supplier_calendar").toString();
		System.out.println(supplier_calendar);

		SupplierCalendarTemplate template = (SupplierCalendarTemplate)CommonJsonUtil.jsonToObject(json2, SupplierCalendarTemplate.class);
		System.out.println(template.getHotel_id());
		BizSkuInfo[] bizSkuInfos = template.getBizSkuInfo();
		System.out.println(bizSkuInfos.length);
		for (BizSkuInfo biz :bizSkuInfos){
			switch (biz.getState()) {
				case "update":
					System.out.println("update");
					break;

				case "delete":
					System.out.println("delete");
					break;

				case "add":
					System.out.println("add");
					break;
				default:
					System.out.println("default");
			}


		}

		long time =DateCommon.Date2Timestamp("2016-6-9 00:00:00");

		System.out.println(time);
		String mm = "1465142400000";
		System.out.println(mm.substring(0,10));

	}
}