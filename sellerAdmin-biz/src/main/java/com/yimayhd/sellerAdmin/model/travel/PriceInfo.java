package com.yimayhd.sellerAdmin.model.travel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemFeature;
import com.yimayhd.ic.client.model.domain.item.ItemSkuDO;
import com.yimayhd.ic.client.model.domain.share_json.LinePropertyType;
import com.yimayhd.ic.client.model.param.item.ItemSkuPVPair;

/**
 * 价格信息
 * 
 * @author yebin
 *
 */
public class PriceInfo {
	private static final int LIMIT_UNIT = 3600 * 24;
	private List<PackageInfo> tcs;// 套餐
	private int limit;// 提前几天
	private String importantInfosCode;
	private Set<Long> updatedSKU;
	private Set<Long> deletedSKU;
	private long itemId;

	public PriceInfo() {
	}

	public PriceInfo(ItemDO itemDO, List<ItemSkuDO> itemSkuList) {
		this.itemId = itemDO.getId();
		if (itemDO != null && StringUtils.isNotBlank(itemDO.getFeature())) {
			ItemFeature feature = new ItemFeature(itemDO.getFeature());
			this.limit = feature.getStartBookTimeLimit() / LIMIT_UNIT;
			this.importantInfosCode = feature.getAgreement();
		}
		this.tcs = new ArrayList<PackageInfo>();
		if (CollectionUtils.isNotEmpty(itemSkuList)) {
			Map<String, ItemSkuPVPair> piMap = new LinkedHashMap<String, ItemSkuPVPair>();
			Map<Long, ItemSkuPVPair> pdMap = new LinkedHashMap<Long, ItemSkuPVPair>();
			Map<Long, ItemSkuPVPair> pbMap = new LinkedHashMap<Long, ItemSkuPVPair>();
			Map<String, Map<Long, Map<Long, ItemSkuDO>>> treeMap = new LinkedHashMap<String, Map<Long, Map<Long, ItemSkuDO>>>();
			for (ItemSkuDO sku : itemSkuList) {
				ItemSkuPVPair tcPair = null;
				ItemSkuPVPair dayPair = null;
				ItemSkuPVPair personPair = null;
				if (StringUtils.isNotBlank(sku.getProperty())) {
					List<ItemSkuPVPair> pairs = sku.getItemSkuPVPairList();
					for (ItemSkuPVPair itemSkuPVPair : pairs) {
						if (itemSkuPVPair.getPId() == LinePropertyType.TRAVEL_PACKAGE.getType()) {
							tcPair = itemSkuPVPair;
							if (!piMap.containsKey(tcPair.getVTxt())) {
								piMap.put(tcPair.getVTxt(), tcPair);
							}
						}
						if (itemSkuPVPair.getPId() == LinePropertyType.DEPART_DATE.getType()) {
							dayPair = itemSkuPVPair;
							if (!pdMap.containsKey(dayPair.getVTxt())) {
								long time = Long.parseLong(dayPair.getVTxt());
								pdMap.put(time, dayPair);
							}
						}
						if (itemSkuPVPair.getPId() == LinePropertyType.PERSON.getType()) {
							personPair = itemSkuPVPair;
							if (!pbMap.containsKey(personPair.getVId())) {
								pbMap.put(personPair.getVId(), personPair);
							}
						}
					}
					Map<Long, Map<Long, ItemSkuDO>> tc = null;
					if (treeMap.containsKey(tcPair.getVTxt())) {
						tc = treeMap.get(tcPair.getVTxt());
					} else {
						tc = new LinkedHashMap<Long, Map<Long, ItemSkuDO>>();
						treeMap.put(tcPair.getVTxt(), tc);
					}
					Map<Long, ItemSkuDO> day = null;
					long time = Long.parseLong(dayPair.getVTxt());
					if (tc.containsKey(time)) {
						day = tc.get(time);
					} else {
						day = new LinkedHashMap<Long, ItemSkuDO>();
						tc.put(time, day);
					}
					day.put(personPair.getVId(), sku);
				}
			}
			// 完成数据组装
			for (Entry<String, Map<Long, Map<Long, ItemSkuDO>>> pi : treeMap.entrySet()) {
				ItemSkuPVPair tcPair = piMap.get(pi.getKey());
				List<PackageDay> packageDays = new ArrayList<PackageDay>();
				for (Entry<Long, Map<Long, ItemSkuDO>> pd : pi.getValue().entrySet()) {
					ItemSkuPVPair dayPair = pdMap.get(pd.getKey());
					List<PackageBlock> packageBlocks = new ArrayList<PackageBlock>();
					for (Entry<Long, ItemSkuDO> pb : pd.getValue().entrySet()) {
						ItemSkuPVPair personPair = pbMap.get(pb.getKey());
						ItemSkuDO sku = pb.getValue();
						// TODO discount暂不实现
						packageBlocks
								.add(new PackageBlock(sku.getId(), personPair, sku.getPrice(), sku.getStockNum(), 0));
					}
					packageDays.add(new PackageDay(dayPair, pd.getKey(), packageBlocks));
				}
				tcs.add(new PackageInfo(tcPair, packageDays));
			}
		}
	}

	public List<PackageInfo> getTcs() {
		return tcs;
	}

	public void setTcs(List<PackageInfo> tcs) {
		this.tcs = tcs;
	}

	public int getLimit() {
		return limit;
	}

	public int getLimitBySecond() {
		return limit * LIMIT_UNIT;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getStartTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (CollectionUtils.isNotEmpty(tcs)) {
			List<PackageMonth> months = tcs.get(0).getMonths();
			if (CollectionUtils.isNotEmpty(months)) {
				List<PackageDay> days = months.get(0).getDays();
				if (CollectionUtils.isNotEmpty(days)) {
					return sdf.format(new Date(days.get(0).getTime()));
				}
			}
		}
		return "";
	}

	public String getEndTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (CollectionUtils.isNotEmpty(tcs)) {
			List<PackageMonth> months = tcs.get(0).getMonths();
			if (CollectionUtils.isNotEmpty(months)) {
				List<PackageDay> days = months.get(months.size() - 1).getDays();
				if (CollectionUtils.isNotEmpty(days)) {
					return sdf.format(new Date(days.get(days.size() - 1).getTime()));
				}
			}
		}
		return "";
	}

	public List<ItemSkuDO> toItemSkuDOList(long categoryId, long sellerId) {
		List<ItemSkuDO> itemSkuDOs = new ArrayList<ItemSkuDO>();
		if (CollectionUtils.isNotEmpty(this.tcs)) {
			for (PackageInfo packageInfo : this.tcs) {
				ItemSkuPVPair itemSkuPVPair1 = packageInfo.toItemSkuPVPair();
				if (CollectionUtils.isNotEmpty(packageInfo.getMonths())) {
					for (PackageMonth packageMonth : packageInfo.getMonths()) {
						if (CollectionUtils.isNotEmpty(packageMonth.getDays())) {
							for (PackageDay packageDay : packageMonth.getDays()) {
								ItemSkuPVPair itemSkuPVPair2 = packageDay.toItemSkuPVPair();
								if (CollectionUtils.isNotEmpty(packageMonth.getDays())) {
									for (PackageBlock packageBlock : packageDay.getBlocks()) {
										ItemSkuPVPair itemSkuPVPair3 = packageBlock.toItemSkuPVPair();
										List<ItemSkuPVPair> itemSkuPVPairs = new ArrayList<ItemSkuPVPair>();
										itemSkuPVPairs.add(itemSkuPVPair1);
										itemSkuPVPairs.add(itemSkuPVPair2);
										itemSkuPVPairs.add(itemSkuPVPair3);
										ItemSkuDO itemSkuDO = new ItemSkuDO();
										itemSkuDO.setId(packageBlock.getSkuId());
										itemSkuDO.setTitle(itemSkuPVPair1.getVTxt() + "," + itemSkuPVPair3.getVTxt());
										itemSkuDO.setCategoryId(categoryId);
										itemSkuDO.setSellerId(sellerId);
										itemSkuDO.setItemSkuPVPairList(itemSkuPVPairs);
										itemSkuDO.setPrice(packageBlock.getPrice());
										itemSkuDO.setStockNum(packageBlock.getStock());
										itemSkuDOs.add(itemSkuDO);
									}
								}
							}
						}
					}
				}
			}
		}
		return itemSkuDOs;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public String getImportantInfosCode() {
		return importantInfosCode;
	}

	public void setImportantInfosCode(String importantInfosCode) {
		this.importantInfosCode = importantInfosCode;
	}

	public Set<Long> getUpdatedSKU() {
		return updatedSKU;
	}

	public void setUpdatedSKU(Set<Long> updatedSKU) {
		this.updatedSKU = updatedSKU;
	}

	public Set<Long> getDeletedSKU() {
		return deletedSKU;
	}

	public void setDeletedSKU(Set<Long> deletedSKU) {
		this.deletedSKU = deletedSKU;
	}
}
