package com.yimayhd.sellerAdmin.model.HotelManage;

import com.yimayhd.ic.client.model.domain.item.RoomFeature;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by wangdi on 16/5/24.
 */
public class RoomMessageVO implements Serializable {

    private static final long serialVersionUID = 6247666067320834527L;

    private long				roomId;
    private String				name;
    private long				hotelId;
    private int					domain;
    private int 				status;
    private Double    area;//面积
    private Integer peopleNum ;//人数
    // 床型,面积,网络,窗,加床,楼层,人数
    private RoomFeature feature;
    private long				version;
    private List<String> pics;
    private Date gmtModified;
    private Date				gmtCreated;



}
