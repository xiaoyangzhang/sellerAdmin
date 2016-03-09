package com.yimayhd.sellerAdmin.model.travel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.yimayhd.sellerAdmin.constant.Constant;
import org.apache.commons.lang.StringUtils;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.ic.client.model.domain.LineDO;
import com.yimayhd.ic.client.model.domain.share_json.MasterRecommend;
import com.yimayhd.ic.client.model.domain.share_json.NeedKnow;
import com.yimayhd.ic.client.model.enums.ItemPicUrlsKey;
import com.yimayhd.ic.client.model.enums.LineOwnerType;
import com.yimayhd.resourcecenter.model.enums.RegionLevel;

/**
 * 基本信息
 * 
 * @author yebin
 * 
 */
public class BaseInfo {
	private long id;// ID
	private int type;// 类型
	private int orderNum;
	private String name;// 产品名称
	private String productImageApp;// App产品封面图
	private String productImagePc;// Pc产品封面图
	private String tripImage;// 行程封面
	private String orderImage;// 订单封面
	private List<Long> tags;// 标签
	private long fromLevel;
	private long fromId;// 出发地
	private String fromName;
	private long toLevel;
	private long toId;// 目的地
	private String toName;
	private int publisherType;// 发布者类型
	private long publisherId;// 发布者Id
	private List<String> masters;
	private long price;
	private long memberPrice;
	private String highlights;// 亮点
	private MasterRecommend recommond;// 代言
	private NeedKnow needKnow;// 报名须知

	public BaseInfo() {
	}

	public BaseInfo(LineDO line, List<ComTagDO> comTagDOs) {
		this.id = line.getId();
		this.type = line.getType();
		this.orderNum = line.getOrderNum();
		this.name = line.getName();
		String productImageApp = line.getPicUrls(ItemPicUrlsKey.BIG_LIST_PIC);
		if (StringUtils.isNotBlank(productImageApp)) {
			this.productImageApp = productImageApp;
		} else {
			this.productImageApp = line.getLogoUrl();
		}
		String productImagePc = line.getPicUrls(ItemPicUrlsKey.PC_BIG_LIST_PIC);
		if (StringUtils.isNotBlank(productImagePc)) {
			this.productImagePc = productImagePc;
		}
		String tripImage = line.getPicUrls(ItemPicUrlsKey.COVER_PICS);
		if (StringUtils.isNotBlank(tripImage)) {
			this.tripImage = tripImage;
		} else {
			this.tripImage = line.getCoverUrl();
		}
		String orderImage = line.getPicUrls(ItemPicUrlsKey.SMALL_LIST_PIC);
		if (StringUtils.isNotBlank(orderImage)) {
			this.orderImage = orderImage;
		}
		tags = new ArrayList<Long>();
		if (comTagDOs != null) {
			for (ComTagDO comTagDO : comTagDOs) {
				tags.add(comTagDO.getId());
			}
		}
		if (line.getStartProvinceId() > 0) {
			this.fromId = line.getStartProvinceId();
		} else if (line.getStartCityId() > 0) {
			this.fromId = line.getStartCityId();
		} else if (line.getStartTownId() > 0) {
			this.fromId = line.getStartTownId();
		}
		if (line.getDestProvinceId() > 0) {
			this.toId = line.getDestProvinceId();
		} else if (line.getDestCityId() > 0) {
			this.toId = line.getDestCityId();
		} else if (line.getDestTownId() > 0) {
			this.toId = line.getDestTownId();
		}
		this.publisherType = line.getOwnerType();
		this.publisherId = line.getOwnerId();
		this.price = line.getPrice();
		this.memberPrice = line.getMemberPrice();
		this.highlights = line.getDescription();
		this.recommond = line.getRecommend();
		this.needKnow = line.getNeedKnow();
		this.masters = line.getRcmdMasters();
	}

	/**
	 * 包含某个tag
	 * 
	 * @param id
	 * @return
	 */
	public boolean containsTag(long id) {
		if (id <= 0 || tags == null || tags.size() <= 0) {
			return false;
		}
		return tags.contains(id);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTripImage() {
		return tripImage;
	}

	public void setTripImage(String tripImage) {
		this.tripImage = tripImage;
	}

	public List<Long> getTags() {
		return tags;
	}

	public void setTags(List<Long> tags) {
		this.tags = tags;
	}

	public int getPublisherType() {
		return publisherType;
	}

	public void setPublisherType(int publisherType) {
		this.publisherType = publisherType;
	}

	public String getHighlights() {
		return highlights;
	}

	public void setHighlights(String highlights) {
		this.highlights = highlights;
	}

	public long getFromId() {
		return fromId;
	}

	public void setFromId(long fromId) {
		this.fromId = fromId;
	}

	public long getToId() {
		return toId;
	}

	public void setToId(long toId) {
		this.toId = toId;
	}

	public long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(long publisherId) {
		this.publisherId = publisherId;
	}

	public MasterRecommend getRecommond() {
		return recommond;
	}

	public void setRecommond(MasterRecommend recommond) {
		this.recommond = recommond;
	}

	public long getFromLevel() {
		return fromLevel;
	}

	public void setFromLevel(long fromLevel) {
		this.fromLevel = fromLevel;
	}

	public long getToLevel() {
		return toLevel;
	}

	public void setToLevel(long toLevel) {
		this.toLevel = toLevel;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public LineDO toLineDO() {
		// 初始化
		LineDO line = new LineDO();
		line.setId(this.id);
		return modifyLineDO(line);
	}

	/**
	 * 修改LineDO
	 * 
	 * @param line
	 * @return
	 */
	public LineDO modifyLineDO(LineDO line) {
		line.setType(this.type);
		line.setOrderNum(this.orderNum);
		line.setName(this.name);
		// image
		line.setCoverUrl(this.tripImage);
		line.addPicUrls(ItemPicUrlsKey.COVER_PICS, this.tripImage);
		line.setLogoUrl(this.productImageApp);
		line.addPicUrls(ItemPicUrlsKey.BIG_LIST_PIC, this.productImageApp);
		line.addPicUrls(ItemPicUrlsKey.SMALL_LIST_PIC, this.orderImage);
		line.addPicUrls(ItemPicUrlsKey.PC_BIG_LIST_PIC, this.productImagePc);
		line.setPictures(Arrays.asList(this.tripImage));
		if (this.fromLevel == RegionLevel.PROVINCE.getLevel()) {
			line.setStartProvinceId(this.fromId);
		} else if (this.fromLevel == RegionLevel.CITY.getLevel()) {
			line.setStartCityId(this.fromId);
		} else if (this.fromLevel == RegionLevel.TOWN.getLevel()) {
			line.setStartTownId(this.fromId);
		}
		line.setStartCityName(this.fromName);
		if (this.toLevel == RegionLevel.PROVINCE.getLevel()) {
			line.setDestProvinceId(this.toId);
		} else if (this.toLevel == RegionLevel.CITY.getLevel()) {
			line.setDestCityId(this.toId);
		} else if (this.toLevel == RegionLevel.TOWN.getLevel()) {
			line.setDestTownId(this.toId);
		}
		line.setDestCityName(this.toName);
		line.setOwnerType(LineOwnerType.DEFAULT.getType());
		line.setOwnerId(this.publisherId);
		line.setRcmdMasters(this.masters);
		line.setPrice(this.price);
		line.setMemberPrice(this.memberPrice);
		line.setDescription(this.highlights);
		line.setRecommend(this.recommond);
		line.setNeedKnow(this.needKnow);
		line.setPhoneNum(Arrays.asList(Constant.YIMAY_CUSTOMER_SERVICE));
		return line;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public long getMemberPrice() {
		return memberPrice;
	}

	public void setMemberPrice(long memberPrice) {
		this.memberPrice = memberPrice;
	}

	public List<String> getMasters() {
		return masters;
	}

	public void setMasters(List<String> masters) {
		this.masters = masters;
	}

	public NeedKnow getNeedKnow() {
		return needKnow;
	}

	public void setNeedKnow(NeedKnow needKnow) {
		this.needKnow = needKnow;
	}

	public String getOrderImage() {
		return orderImage;
	}

	public void setOrderImage(String orderImage) {
		this.orderImage = orderImage;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public String getProductImageApp() {
		return productImageApp;
	}

	public void setProductImageApp(String productImageApp) {
		this.productImageApp = productImageApp;
	}

	public String getProductImagePc() {
		return productImagePc;
	}

	public void setProductImagePc(String productImagePc) {
		this.productImagePc = productImagePc;
	}
}
