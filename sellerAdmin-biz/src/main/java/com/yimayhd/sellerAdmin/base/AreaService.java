package com.yimayhd.sellerAdmin.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yimayhd.sellerAdmin.model.AreaVO;
import com.yimayhd.user.client.domain.AreaDO;
import com.yimayhd.user.client.domain.CityDO;
import com.yimayhd.user.client.domain.ProvinceDO;
import com.yimayhd.user.client.service.UserAddressService;

public class AreaService {

	private static AreaService instance = null;
	
	//private ProvinceCityAreaResult provinceCityAreaResult;
	
	private static List<ProvinceDO> listProvinceDO = null;
	
	private static List<CityDO> listCityDO = null;
	
	private static List<AreaDO> listAreaDO = null;
	
	private static com.yimayhd.user.client.service.UserAddressService userAddressService = null;
	
	private AreaService() {
		
		/**
		 * 网络抖动    远程调用异常    
		 *  dubbo  check = true
		 * 一期完成后添加
		 */
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext.getServletContext();
		ApplicationContext appCtx = WebApplicationContextUtils.getWebApplicationContext(servletContext);		
		
/*		ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext(
        "classpath*:spring-consumer-singleton.xml");*/
		userAddressService = (UserAddressService) appCtx.getBean("userAddressServiceRef");
		
		listProvinceDO = userAddressService.getProvinceCityArea().getProvinceDOList();
		listCityDO = userAddressService.getProvinceCityArea().getCityDOList();
		listAreaDO = userAddressService.getProvinceCityArea().getAreaDOList();
		
	}
	
	public static AreaService getInstance() {
		
		if (instance == null) {
			
			synchronized(AreaService.class) {
				
				if (instance == null) {
					instance = new AreaService();
				}
				
			}
			
		}
		
		return instance;
		
	}

	public List<ProvinceDO> getListProvinceDO() {
		return listProvinceDO;
	}

	public List<CityDO> getListCityDO() {
		return listCityDO;
	}

	public List<AreaDO> getListAreaDO() {
		return listAreaDO;
	}
	
	public List<AreaVO> getAreaByIDAndType(String type, String parentCode) {
		
		List<AreaVO> resultList = new ArrayList<AreaVO>();
		// 省
		// parentCode可以传空
		if (AreaType.PROVINCE.equals(type)) {

			List<ProvinceDO> provinceList = listProvinceDO;
			Iterator<ProvinceDO> iterator = provinceList.iterator();
			while (iterator.hasNext()) {

				ProvinceDO pTemp = iterator.next();
				AreaVO aTemp = new AreaVO();
				aTemp.setCode(pTemp.getProvinceCode());
				aTemp.setName(pTemp.getProvinceName());
				resultList.add(aTemp);
			}

			return resultList;
		}

		// 市
		if (AreaType.CITY.equals(type)) {

			List<CityDO> cityList = listCityDO;
			Iterator<CityDO> iterator = cityList.iterator();
			while (iterator.hasNext()) {
				CityDO cTemp = iterator.next();
				if (parentCode.equals(cTemp.getFatherCode())) {
					AreaVO aTemp = new AreaVO();
					aTemp.setCode(cTemp.getCityCode());
					aTemp.setName(cTemp.getCityName());
					aTemp.setParentCode(cTemp.getFatherCode());
					resultList.add(aTemp);									
				}
			}
			return resultList;

		}
		
		// 县
		if (AreaType.COUNTY.equals(type)) {			
			
			List<AreaDO> areaList = listAreaDO;
			Iterator<AreaDO> iterator = areaList.iterator();
			while (iterator.hasNext()) {
				AreaDO cTemp = iterator.next();
				if (parentCode.equals(cTemp.getFatherCode())) {
					AreaVO aTemp = new AreaVO();
					aTemp.setCode(cTemp.getAreaCode());
					aTemp.setName(cTemp.getAreaName());
					aTemp.setParentCode(cTemp.getFatherCode());
					resultList.add(aTemp);									
				}
			}
			return resultList;			
			
		}
		return resultList;		
	}
	
}
