package com.yimayhd.sellerAdmin.controller.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.yimayhd.ic.client.util.JsonUtils;
import com.yimayhd.membercenter.client.domain.merchant.MerchantItemCategoryDO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.client.service.MerchantItemCategoryService;
import com.yimayhd.sellerAdmin.constant.Constant;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.ic.client.model.domain.item.CategoryDO;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.base.result.WebOperateResult;
import com.yimayhd.sellerAdmin.base.result.WebResult;
import com.yimayhd.sellerAdmin.base.result.WebReturnCode;
import com.yimayhd.sellerAdmin.enums.BizItemStatus;
import com.yimayhd.sellerAdmin.enums.BizItemType;
import com.yimayhd.sellerAdmin.model.enums.ItemOperate;
import com.yimayhd.sellerAdmin.model.item.ItemListItemVO;
import com.yimayhd.sellerAdmin.model.query.ItemListQuery;
import com.yimayhd.sellerAdmin.service.CategoryService;
import com.yimayhd.sellerAdmin.service.item.ItemService;
import com.yimayhd.sellerAdmin.vo.menu.CategoryVO;
import com.yimayhd.stone.enums.DomainType;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.enums.UserOptions;

/**
 * 商品管理
 *
 * @author yebin
 */
@Controller
@RequestMapping("/item")
public class ItemController extends BaseController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ItemQueryService itemQueryService;
    @Autowired
    private MerchantItemCategoryService merchantItemCategoryService;

    /**
     * 商品列表
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public String list(ItemListQuery query) throws Exception {
        long sellerId = getCurrentUserId();
        if (sellerId <= 0) {
            log.warn("未登录");
            throw new BaseException("请登陆后重试");
        }
        WebResult<PageVO<ItemListItemVO>> result = itemService.getItemList(sellerId, query);
        if (!result.isSuccess()) {
            throw new BaseException(result.getResultMsg());
        }
        put("pageVo", result.getValue());
        put("itemTypeList", BizItemType.values());
        put("itemStatusList", BizItemStatus.values());
        put("query", query);
        return "/system/comm/itemList";
    }

    @RequestMapping(value = "/cateList")
    public String cateList() {
        return "/system/comm/category";
    }

    @ResponseBody
    @RequestMapping(value = "/getcate")
    public List<CategoryVO> getcate(HttpServletRequest request,String categoryId) {
        // 判断是否是达人
        UserDO user = sessionManager.getUser(request);
        long option = user.getOptions();
        boolean isTalentA = UserOptions.CERTIFICATED.has(option);
        boolean isTalentB = UserOptions.USER_TALENT.has(option);
        
       // String cateId = get("categoryId");
        List<CategoryVO> list = null;
        if (StringUtils.isBlank(categoryId)) {
            WebResult<CategoryDO> webResult = categoryService.getCategoryByDomainId(Constant.DOMAIN_JIUXIU);
            if (null != webResult && webResult.getValue() != null) {
                list = categoryDoTOVo(webResult.getValue().getChildren(), isTalentA || isTalentB);
            }
        } else {
//            int categoryId = 0;
//            try {
//                categoryId = Integer.parseInt(cateId);
//            } catch (Exception e){
//                log.error("cateId={} is null", cateId);
//                list = new ArrayList<>();
//                return list;
//            }
            // 查询某节点下的子节点
            WebResult<CategoryDO> webResult = categoryService.getCategoryById(Integer.parseInt(categoryId));
            if (null != webResult && webResult.getValue() != null) {
                list = categoryDoTOVo(webResult.getValue().getChildren(), false);
            }
        }
        if (!isTalentA && !isTalentB) { // 身份为商户时进行商品类目权限过滤
            List<CategoryVO> filteredList = new ArrayList<>();
            MemResult<List<MerchantItemCategoryDO>> merchantItemCategoryResult = merchantItemCategoryService.findMerchantItemCategoriesBySellerId(Constant.DOMAIN_JIUXIU, user.getId());
            outer:
            for (CategoryVO categoryVO : list) {
                inner:
                for (MerchantItemCategoryDO merchantItemCategoryDO : merchantItemCategoryResult.getValue()) {
                    try {
                        CategoryDO categoryDO = categoryService.getCategoryDOById(merchantItemCategoryDO.getItemCategoryId());
                        while (categoryVO.getCategoryId() != categoryDO.getId()) { // 根据商户有权限的山品类目递归查找到根节点,如果所有类目都没权限,判断list集中和的下一个类目
                            categoryDO = categoryService.getCategoryDOById(categoryDO.getParentId());
                            if (null == categoryDO) {
                                continue inner;
                            }
                        }
                        // 如果匹配到权限,加入新的集合中,并对list的下一个类目进行判断
                        filteredList.add(categoryVO);
                        continue outer;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return filteredList;
        } else {
            List<CategoryVO> filteredList = new ArrayList<>();
            List<Long> idList = new ArrayList<>();
            idList.add(204L);
            idList.add(205L);
            idList.add(207L);
            outer:
            for (CategoryVO categoryVO : list) {
                inner:
                for (Long id : idList) {
                    try {
                        CategoryDO categoryDO = categoryService.getCategoryDOById(id);
                        while (categoryVO.getCategoryId() != categoryDO.getId()) { // 根据商户有权限的山品类目递归查找到根节点,如果所有类目都没权限,判断list集中和的下一个类目
                            categoryDO = categoryService.getCategoryDOById(categoryDO.getParentId());
                            if (null == categoryDO) {
                                continue inner;
                            }
                        }
                        // 如果匹配到权限,加入新的集合中,并对list的下一个类目进行判断
                        filteredList.add(categoryVO);
                        continue outer;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return filteredList;
        }
    }

    private List<CategoryVO> categoryDoTOVo(List<CategoryDO> childrenList, boolean isTalent) {
        List<CategoryVO> list = new ArrayList<CategoryVO>();
        if (CollectionUtils.isEmpty(childrenList)) {
            return list;
        }
        for (CategoryDO categoryDO : childrenList) {
            if (isTalent) {
                if (categoryDO.getCategoryFeature().getItemType() != ItemType.NORMAL.getValue()) {
                    CategoryVO vo = new CategoryVO();
                    vo.setCategoryId(categoryDO.getId());
                    vo.setIsLeaf(categoryDO.getLeaf());
                    vo.setLevel(categoryDO.getLevel());
                    vo.setCategoryName(categoryDO.getName());
                    list.add(vo);
                }
            } else {
                CategoryVO vo = new CategoryVO();
                vo.setCategoryId(categoryDO.getId());
                vo.setIsLeaf(categoryDO.getLeaf());
                vo.setLevel(categoryDO.getLevel());
                vo.setCategoryName(categoryDO.getName());
                list.add(vo);
            }
        }
        return list;
    }

    @RequestMapping(value = "/category/{categoryId}/create")
    public String createItem(@PathVariable(value = "categoryId") long categoryId) throws Exception {
        CategoryDO categoryDO = categoryService.getCategoryDOById(categoryId);
        if (categoryId <= 0 || categoryDO == null) {
            log.warn("无效categoryId");
            throw new BaseException("无效categoryId");
        }
        ItemType itemType = ItemType.get(categoryDO.getCategoryFeature().getItemType());
        if (itemType == null) {
            log.error("category {} ItemType is null", categoryId);
            throw new BaseException("ItemType is null");
        }
        // TODO YEBIN 待开发
        if (ItemType.FREE_LINE.equals(itemType) || ItemType.TOUR_LINE.equals(itemType)
                || ItemType.FREE_LINE_ABOARD.equals(itemType) || ItemType.TOUR_LINE_ABOARD.equals(itemType)) {
            return redirect("/line/category/" + categoryId + "/create/");
        } else if (ItemType.CITY_ACTIVITY.equals(itemType)) {
            return redirect("/cityactivity/toAdd?categoryId=" + categoryId);
        } else if (ItemType.NORMAL.equals(itemType)) {
            return redirect("/barterItem/common/toAdd?categoryId=" + categoryId);
        } else if (ItemType.HOTEL.equals(itemType)) {
            return redirect("/hotel/addHotelMessageVOByDataView?categoryId=" + categoryId);
        } else if (ItemType.SPOTS.equals(itemType)) {
            return redirect("/scenic/addScenicManageView?categoryId=" + categoryId);
        } else if (ItemType.POINT_MALL.equals(itemType)) {
            return redirect("/integralMall/toAdd?categoryId=" + categoryId);
        }  else {
            throw new BaseException("unsupport ItemType " + itemType.name());
        }
    }

    @RequestMapping(value = "/{id}/shelve")
    public
    @ResponseBody
    WebOperateResult shelve(@PathVariable("id") long id) {
        long sellerId = getCurrentUserId();
        WebOperateResult result = checkBeforeOperate(id, sellerId);
        if (result != null) {
            return result;
        }
        //if (ItemOperate.SHELVE.name().equalsIgnoreCase(operate)) {
        return itemService.shelve(sellerId, id);
//		} else if (ItemOperate.UNSHELVE.name().equalsIgnoreCase(operate)) {
//			return itemService.unshelve(sellerId, id);
//		} else if (ItemOperate.DELETE.name().equalsIgnoreCase(operate)) {
//			return itemService.delete(sellerId, id);
//		} else {
//			return WebOperateResult.failure(WebReturnCode.PARAM_ERROR, "unsupported operate");
//		}


    }

    @RequestMapping(value = "/{id}/unshelve")
    public
    @ResponseBody
    WebOperateResult unshelve(@PathVariable("id") long id) {
        long sellerId = getCurrentUserId();
        WebOperateResult result = checkBeforeOperate(id, sellerId);
        if (result != null) {
            return result;
        }
//		if (ItemOperate.SHELVE.name().equalsIgnoreCase(operate)) {
//			return itemService.shelve(sellerId, id);
//		} else if (ItemOperate.UNSHELVE.name().equalsIgnoreCase(operate)) {
        return itemService.unshelve(sellerId, id);
//		} else if (ItemOperate.DELETE.name().equalsIgnoreCase(operate)) {
//			return itemService.delete(sellerId, id);
//		} else {
//			return WebOperateResult.failure(WebReturnCode.PARAM_ERROR, "unsupported operate");
//		}


    }

    @RequestMapping(value = "/{id}/delete")
    public
    @ResponseBody
    WebOperateResult delete(@PathVariable("id") long id) {
        long sellerId = getCurrentUserId();
        WebOperateResult result = checkBeforeOperate(id, sellerId);
        if (result != null) {
            return result;
        }
//		if (ItemOperate.SHELVE.name().equalsIgnoreCase(operate)) {
//			return itemService.shelve(sellerId, id);
//		} else if (ItemOperate.UNSHELVE.name().equalsIgnoreCase(operate)) {
//			return itemService.unshelve(sellerId, id);
//		} else if (ItemOperate.DELETE.name().equalsIgnoreCase(operate)) {
        return itemService.delete(sellerId, id);
//		} else {
//			return WebOperateResult.failure(WebReturnCode.PARAM_ERROR, "unsupported operate");
//		}


    }

    private WebOperateResult checkBeforeOperate(long id, long sellerId) {
        //long sellerId = getCurrentUserId();
        if (sellerId <= 0) {
            log.warn("未登录");
            return WebOperateResult.failure(WebReturnCode.SYSTEM_ERROR_MERCHANT_TALENT);
        }
        List<Long> itemIds = new ArrayList<Long>();
        itemIds.add(id);
        ICResult<List<ItemDO>> itemQueryResult = itemQueryService.getItemByIds(itemIds);
        if (itemQueryResult.getModule() != null && itemQueryResult.getModule().size() > 0 && itemQueryResult.getModule().get(0).getSellerId() != sellerId) {

            log.warn("不支持的操作");
            return WebOperateResult.failure(WebReturnCode.PARAM_ERROR, "unsupported operate");
        }
        return null;
        //return sellerId;
    }

    @RequestMapping(value = "/batchshelve")
    public
    @ResponseBody
    WebOperateResult batchPerate(@RequestParam("itemIds[]") Long[] itemIds) {
        long sellerId = getCurrentUserId();
        if (sellerId <= 0) {
            log.warn("未登录");
            return WebOperateResult.failure(WebReturnCode.SYSTEM_ERROR_MERCHANT_TALENT);
        }
        if (ArrayUtils.isEmpty(itemIds)) {
            log.warn("itemIds is null");
            return WebOperateResult.failure(WebReturnCode.PARAM_ERROR);
        }

        List<Long> itemIdList = checkBeforeBatchOperate(itemIds, sellerId);
        if (itemIdList.size() == 0) {
            log.warn("size of itemIdList is 0");
            return WebOperateResult.failure(WebReturnCode.PARAM_ERROR);
        }
//		if (result != null) {
//			return result;
//		}
        //if (ItemOperate.SHELVE.name().equalsIgnoreCase(operate)) {
        return itemService.batchShelve(sellerId, itemIdList);
//		} else if (ItemOperate.UNSHELVE.name().equalsIgnoreCase(operate)) {
//			return itemService.batchUnshelve(sellerId, Arrays.asList(itemIds));
//		} else if (ItemOperate.DELETE.name().equalsIgnoreCase(operate)) {
//			return itemService.batchDelete(sellerId, Arrays.asList(itemIds));
//		} else {
//			return WebOperateResult.failure(WebReturnCode.PARAM_ERROR, "unsupported operate");
//		}
    }

    private List<Long> checkBeforeBatchOperate(Long[] itemIds, long sellerId) {
//		if (sellerId <= 0) {
//			log.warn("未登录");
//			return WebOperateResult.failure(WebReturnCode.SYSTEM_ERROR_MERCHANT_TALENT);
//		}
//		if (ArrayUtils.isEmpty(itemIds)) {
//			log.warn("itemIds is null");
//			return WebOperateResult.failure(WebReturnCode.PARAM_ERROR);
//		}
        ICResult<List<ItemDO>> itemsResult = itemQueryService.getItemByIds(Arrays.asList(itemIds));
        List<Long> itemIdList = new ArrayList<Long>();
        if (itemsResult.getModule() != null && itemsResult.getModule().size() > 0) {
            for (ItemDO itemDO : itemsResult.getModule()) {
                if (itemDO.getSellerId() == sellerId) {
//					log.error("当前用户不能操作其他用户的商品");
//					return WebOperateResult.failure(WebReturnCode.PARAM_ERROR, "unsupported operate");
                    itemIdList.add(itemDO.getId());
                }
            }
        }
        return itemIdList;
    }

    @RequestMapping(value = "/batchunshelve")
    public
    @ResponseBody
    WebOperateResult batchUnshelve(@RequestParam("itemIds[]") Long[] itemIds) {
        long sellerId = getCurrentUserId();
        if (sellerId <= 0) {
            log.warn("未登录");
            return WebOperateResult.failure(WebReturnCode.SYSTEM_ERROR_MERCHANT_TALENT);
        }
        if (ArrayUtils.isEmpty(itemIds)) {
            log.warn("itemIds is null");
            return WebOperateResult.failure(WebReturnCode.PARAM_ERROR);
        }
        List<Long> itemIdList = checkBeforeBatchOperate(itemIds, sellerId);
        if (itemIdList.size() == 0) {
            log.warn("size of itemIdList is 0");
            return WebOperateResult.failure(WebReturnCode.PARAM_ERROR);
        }
//		if (result != null) {
//			return result;
//		}
//		if (ItemOperate.SHELVE.name().equalsIgnoreCase(operate)) {
//			return itemService.batchShelve(sellerId, Arrays.asList(itemIds));
//		} else if (ItemOperate.UNSHELVE.name().equalsIgnoreCase(operate)) {
        return itemService.batchUnshelve(sellerId, itemIdList);
//		} else if (ItemOperate.DELETE.name().equalsIgnoreCase(operate)) {
//			return itemService.batchDelete(sellerId, Arrays.asList(itemIds));
//		} else {
//			return WebOperateResult.failure(WebReturnCode.PARAM_ERROR, "unsupported operate");
//		}
    }

    @RequestMapping(value = "/batchdelete")
    public
    @ResponseBody
    WebOperateResult batchDelete(@RequestParam("itemIds[]") Long[] itemIds) {
        long sellerId = getCurrentUserId();
        if (sellerId <= 0) {
            log.warn("未登录");
            return WebOperateResult.failure(WebReturnCode.SYSTEM_ERROR_MERCHANT_TALENT);
        }
        if (ArrayUtils.isEmpty(itemIds)) {
            log.warn("itemIds is null");
            return WebOperateResult.failure(WebReturnCode.PARAM_ERROR);
        }
        List<Long> itemIdList = checkBeforeBatchOperate(itemIds, sellerId);
        if (itemIdList.size() == 0) {
            log.warn("size of itemIdList is 0");
            return WebOperateResult.failure(WebReturnCode.PARAM_ERROR);
        }
//		if (result != null) {
//			return result;
//		}
//		if (ItemOperate.SHELVE.name().equalsIgnoreCase(operate)) {
//			return itemService.batchShelve(sellerId, Arrays.asList(itemIds));
//		} else if (ItemOperate.UNSHELVE.name().equalsIgnoreCase(operate)) {
//			return itemService.batchUnshelve(sellerId, Arrays.asList(itemIds));
//		} else if (ItemOperate.DELETE.name().equalsIgnoreCase(operate)) {
        return itemService.batchDelete(sellerId, itemIdList);
//		} else {
//			return WebOperateResult.failure(WebReturnCode.PARAM_ERROR, "unsupported operate");
//		}
    }

    @RequestMapping(value = "/{id}/type/{type}/edit")
    public String detail(@PathVariable(value = "id") long itemId, @PathVariable(value = "type") int itemType, @RequestParam(required = false) int categoryId) {
        // TODO YEBIN 待开发
        if (ItemType.FREE_LINE.getValue() == itemType || ItemType.TOUR_LINE.getValue() == itemType
                || ItemType.TOUR_LINE_ABOARD.getValue() == itemType
                || ItemType.FREE_LINE_ABOARD.getValue() == itemType) {
            return redirect("/line/edit/" + itemId + "/");
        } else if (ItemType.CITY_ACTIVITY.getValue() == itemType) {
            return redirect("/cityactivity/edit/" + itemId);
        } else if (ItemType.NORMAL.getValue() == itemType) {
            return redirect("/barterItem/common/edit/" + itemId);
        } else if (ItemType.HOTEL.getValue() == itemType) {
            return redirect("/hotel/editHotelMessageView?operationFlag=update&itemId=" + itemId + "&categoryId=" + categoryId);
        } else if (ItemType.SPOTS.getValue() == itemType) {
            return redirect("/scenic/editScenicManageView?operationFlag=update&itemId=" + itemId + "&categoryId=" + categoryId);
        } else {
            throw new BaseException("unsupport ItemType " + itemType);
        }
    }

    @RequestMapping(value = "/{id}/type/{type}/view")
    public String view(@PathVariable(value = "id") long itemId, @PathVariable(value = "type") int itemType, @RequestParam(required = false) int categoryId) {
        // TODO YEBIN 待开发
        if (ItemType.FREE_LINE.getValue() == itemType || ItemType.TOUR_LINE.getValue() == itemType
                || ItemType.TOUR_LINE_ABOARD.getValue() == itemType
                || ItemType.FREE_LINE_ABOARD.getValue() == itemType) {
            return redirect("/line/view/" + itemId + "/");
        } else if (ItemType.CITY_ACTIVITY.getValue() == itemType) {
            return redirect("/cityactivity/view/" + itemId);
        } else if (ItemType.NORMAL.getValue() == itemType) {
            return redirect("/barterItem/common/view/" + itemId);
        } else if (ItemType.HOTEL.getValue() == itemType) {
            return redirect("/hotel/editHotelMessageView?operationFlag=detail&itemId=" + itemId + "&categoryId=" + categoryId);
        } else if (ItemType.SPOTS.getValue() == itemType) {
            return redirect("/scenic/editScenicManageView?operationFlag=detail&itemId=" + itemId + "&categoryId=" + categoryId);
        } else {
            throw new BaseException("unsupport ItemType " + itemType);
        }
    }
}