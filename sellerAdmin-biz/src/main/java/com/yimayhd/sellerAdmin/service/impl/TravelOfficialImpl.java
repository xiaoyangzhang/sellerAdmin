package com.yimayhd.sellerAdmin.service.impl;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.enums.BaseStatus;
import com.yimayhd.sellerAdmin.base.PageVO;
import com.yimayhd.sellerAdmin.model.TravelOfficial;
import com.yimayhd.sellerAdmin.model.query.TravelOfficialListQuery;
import com.yimayhd.sellerAdmin.service.TravelOfficialService;
import com.yimayhd.sellerAdmin.service.UserRPCService;
import com.yimayhd.snscenter.client.domain.SnsTravelSpecialtyDO;
import com.yimayhd.snscenter.client.domain.TravelJsonDO;
import com.yimayhd.snscenter.client.dto.TravelSpecialAddDTO;
import com.yimayhd.snscenter.client.dto.TravelSpecialDTO;
import com.yimayhd.snscenter.client.result.BasePageResult;
import com.yimayhd.snscenter.client.result.BaseResult;
import com.yimayhd.snscenter.client.service.SnsCenterService;
import com.yimayhd.user.client.domain.UserDO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/11/10.
 */
public class TravelOfficialImpl implements TravelOfficialService{

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private SnsCenterService snsCenterService;

    @Autowired
    UserRPCService userRPCService;

    @Override
    public PageVO<SnsTravelSpecialtyDO> getList(TravelOfficialListQuery travelOfficialListQuery) throws Exception {
        TravelSpecialDTO travelSpecialDTO = new TravelSpecialDTO();
        if(!StringUtils.isEmpty(travelOfficialListQuery.getTitle())){  // 游记名称
            travelSpecialDTO.setTravelName(travelOfficialListQuery.getTravelName());
        }

        if(!StringUtils.isEmpty(travelOfficialListQuery.getStartDate())){
            long startDade = paraseTime(travelOfficialListQuery.getStartDate());
            travelSpecialDTO.setStartTime(startDade);
        }

        if(!StringUtils.isEmpty(travelOfficialListQuery.getEndDate())){
            long endDate = paraseTime(travelOfficialListQuery.getEndDate());
            travelSpecialDTO.setEndTime(endDate);
        }

        if(!StringUtils.isEmpty(travelOfficialListQuery.getPoiContent())){
            travelSpecialDTO.setPoiContent(travelOfficialListQuery.getPoiContent());
        }

        if(!StringUtils.isEmpty(travelOfficialListQuery.getNickName())){
            travelSpecialDTO.setNickName(travelOfficialListQuery.getNickName());
        }

        travelSpecialDTO.setPageSize(travelOfficialListQuery.getPageSize());
        travelSpecialDTO.setPageNo(travelOfficialListQuery.getPageNo());
        BasePageResult<SnsTravelSpecialtyDO> result = snsCenterService.getTravelSpecialPage(travelSpecialDTO);

        PageVO<SnsTravelSpecialtyDO> pageVO = new PageVO<SnsTravelSpecialtyDO>(travelOfficialListQuery.getPageNo(), travelOfficialListQuery.getPageSize(),0);;
        if(result != null && result.isSuccess()){
            pageVO = new PageVO<SnsTravelSpecialtyDO>(travelOfficialListQuery.getPageNo(), travelOfficialListQuery.getPageSize(),result.getTotalCount(),result.getList());
        }else{
            log.error("snsCenterService.getTravelSpecialPage is null,result="+JSON.toJSONString(result)+"|parameter="+JSON.toJSONString(travelSpecialDTO));
        }
        return pageVO;
    }

    @Override
    public TravelOfficial getById(long id) throws Exception {

        BaseResult<SnsTravelSpecialtyDO> result = snsCenterService.getTravelSpecialInfoBySubjectId(id);
        TravelOfficial travelOfficial = new TravelOfficial();
        if(result!= null && result.isSuccess()){
            SnsTravelSpecialtyDO snsTravelSpecialtyDO = result.getValue();
            travelOfficial = convertTravelOfficial(snsTravelSpecialtyDO);
        }else{
            log.error("snsCenterService.getTravelSpecialInfoBySubjectId is null,result="+result+"|paramenter="+id);
        }
        return travelOfficial;
    }

    @Override
    public TravelOfficial add(TravelOfficial travelOfficial) throws Exception {
        TravelSpecialAddDTO travelSpecialAddDTO = convertTravelSpecialAddDTO(travelOfficial);
        BaseResult<SnsTravelSpecialtyDO> res = snsCenterService.addTravelSpecialInfo(travelSpecialAddDTO);
        if(null != res && res.isSuccess() && null != res.getValue()){
            travelOfficial.setId(res.getValue().getId());
            return travelOfficial;
        }else{
            log.error("snsCenterService.addTravelSpecialInfo failure,paramenter="+JSON.toJSONString(travelOfficial));
        }
        return null;
    }

    private TravelSpecialAddDTO convertTravelSpecialAddDTO(TravelOfficial travelOfficial){
        TravelSpecialAddDTO travelSpecialAddDTO = new TravelSpecialAddDTO();
        travelSpecialAddDTO.setBackImg(travelOfficial.getBackImg());
        travelSpecialAddDTO.setGmtCreated(travelOfficial.getPublishDate());
        travelSpecialAddDTO.setPreface(travelOfficial.getPreface());
        travelSpecialAddDTO.setTitle(travelOfficial.getTitle());
        travelSpecialAddDTO.setTravelJsonDO(imgContentJsonToTravelJsonDO(travelOfficial.getImgContentJson()));
        travelSpecialAddDTO.setUserId(travelOfficial.getCreateId());
        travelSpecialAddDTO.setPreface(travelOfficial.getPreface());
        travelSpecialAddDTO.setTravelSpecialId(travelOfficial.getId());
        return travelSpecialAddDTO;
    }

    private TravelOfficial convertTravelOfficial(SnsTravelSpecialtyDO snsTravelSpecialtyDO){
        TravelOfficial travelOfficialData = new TravelOfficial();
        if(snsTravelSpecialtyDO != null ){
            travelOfficialData.setId(snsTravelSpecialtyDO.getId());
            travelOfficialData.setTitle(snsTravelSpecialtyDO.getTitle() == null? "" : snsTravelSpecialtyDO.getTitle());
            travelOfficialData.setPublishDate(snsTravelSpecialtyDO.getGmtCreated() == null ? null : snsTravelSpecialtyDO.getGmtCreated());
            travelOfficialData.setBackImg(snsTravelSpecialtyDO.getBackImg() == null ? null :snsTravelSpecialtyDO.getBackImg() );;
            travelOfficialData.setPreface(snsTravelSpecialtyDO.getPreface() == null ? null :snsTravelSpecialtyDO.getPreface());
            travelOfficialData.setImgContentJson(snsTravelSpecialtyDO.getImgContentJson() == null ? null :snsTravelSpecialtyDO.getImgContentJson());
            travelOfficialData.setListTravelJsonDO(convertTravelJsonDO(snsTravelSpecialtyDO.getImgContentJson()));
            travelOfficialData.setCreateUserName(getUserName(snsTravelSpecialtyDO.getCreateId()));
            travelOfficialData.setCreateId(snsTravelSpecialtyDO.getCreateId());
        }
        return travelOfficialData;
    }

    private SnsTravelSpecialtyDO convertSnsTravelSpecialtyDO(TravelOfficial travelOfficial){
        SnsTravelSpecialtyDO snsTravelSpecialtyDO = new SnsTravelSpecialtyDO();
        if(travelOfficial == null){
            return null;
        }
        snsTravelSpecialtyDO.setTitle(travelOfficial.getTitle()== null ? null : travelOfficial.getTitle());
        return snsTravelSpecialtyDO;
    }

    public List<TravelJsonDO> imgContentJsonToTravelJsonDO(String imgContentJson){
        if(org.apache.commons.lang3.StringUtils.isEmpty(imgContentJson)){
            return null;
        }
        try {
            System.out.println(imgContentJson);
            imgContentJson=imgContentJson.replaceAll(" ","");
            System.out.println(imgContentJson);
            List<TravelJsonDO> list  = JSON.parseArray(imgContentJson, TravelJsonDO.class);
            return list;
        } catch (Exception e) {
            log.error("imgContentJsonToTravelJsonDO error,imgContentJson="+JSON.toJSONString(imgContentJson),e);
        }
        return null;
    }




    @Override
    public boolean modify(TravelOfficial travelOfficial) throws Exception {
        TravelSpecialAddDTO travelSpecialAddDTO = convertTravelSpecialAddDTO(travelOfficial);
        BaseResult<SnsTravelSpecialtyDO> res = snsCenterService.updateTravelSpecialInfo(travelSpecialAddDTO);
        if(null != res && res.isSuccess()){
            return true;
        }else{
            log.error("snsCenterService.updateTravelSpecialInfo failure,paramenter="+JSON.toJSONString(travelOfficial));
        }
        return false;
    }

    private long paraseTime(String time) throws Exception{
        long rs = 0;
        if(!StringUtils.isEmpty(time)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date =  sdf.parse(time);
            rs = date.getTime();
        }
        return rs;
    }




    public String getUserName(long id){
        UserDO ud = userRPCService.getUserById(id);
        if(null != ud ){
            return ud.getName();
        }else{
            log.error("userRPCService.getUserById failure,paramenter="+id);
        }
        return null;
    }
    private List<TravelJsonDO> convertTravelJsonDO(String json){
        if(StringUtils.isEmpty(json)){
            return null;
        }
        List<TravelJsonDO> list = JSON.parseArray(json,TravelJsonDO.class);
        return list;
    }


    @Override
    public boolean batchUpOrDownStatus(List<Long> ids, int status) throws Exception{
        if(CollectionUtils.isEmpty(ids)){
            throw new Exception("batchUpOrDownStatus ,parameters [ids] cannot be empty");
        }
        TravelSpecialDTO travelSpecialDTO = new TravelSpecialDTO();
        travelSpecialDTO.setList(ids);
        if(status==BaseStatus.AVAILABLE.getType()){
            travelSpecialDTO.setState(BaseStatus.DELETED.getType());
        }else{
            travelSpecialDTO.setState(BaseStatus.AVAILABLE.getType());
        }
        BaseResult<Boolean> res = snsCenterService.updateTravelStateByIds(travelSpecialDTO);
        if(null != res && res.isSuccess() && res.getValue() ){
            return true;
        }else{
            log.error("snsCenterService.updateTravelStateByIds failure,paramenter="+ JSON.toJSONString(travelSpecialDTO));
        }
        return false;
    }
}
