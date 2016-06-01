package com.yimayhd.sellerAdmin.model.HotelManage;


import java.io.Serializable;
import java.util.List;

/**
 * Created by wangdi on 16/5/24.
 */
public class RoomMessageVO implements Serializable {

    private static final long serialVersionUID = 6247666067320834527L;

    private long	    roomId; //房型ID
    private String	    name;//房型名称
    private long	    hotelId;//酒店ID
    private long        rno;//序号按创建时间升序
    private String      bed;//床型
    private double      area;//面积
    private String      areaStr;//面积str
    private int     people ;//人数
    private List<Integer> network;//网络
    private String      networkStr;//
    private int     window;//窗
    private String      windowStr;//
    private List<String> pics;//照片地址


    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getHotelId() {
        return hotelId;
    }

    public void setHotelId(long hotelId) {
        this.hotelId = hotelId;
    }

    public long getRno() {
        return rno;
    }

    public void setRno(long rno) {
        this.rno = rno;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public List<Integer> getNetwork() {
        return network;
    }

    public void setNetwork(List<Integer> network) {
        this.network = network;
    }

    public String getNetworkStr() {
        return networkStr;
    }

    public void setNetworkStr(String networkStr) {
        this.networkStr = networkStr;
    }

    public int getWindow() {
        return window;
    }

    public void setWindow(int window) {
        this.window = window;
    }

    public String getWindowStr() {
        return windowStr;
    }

    public void setWindowStr(String windowStr) {
        this.windowStr = windowStr;
    }

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }

    public String getAreaStr() {
        return areaStr;
    }

    public void setAreaStr(String areaStr) {
        this.areaStr = areaStr;
    }
}
