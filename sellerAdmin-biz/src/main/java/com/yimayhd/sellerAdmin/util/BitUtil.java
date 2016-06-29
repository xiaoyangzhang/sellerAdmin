package com.yimayhd.sellerAdmin.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.yimayhd.ic.client.model.domain.FacilityIconDO;

/**
 * Created by Administrator on 2015/11/20.
 */
public class BitUtil {
    /*
	* index: 表示facilityBits对应map中key从（index*64）到（index*64 + 63）的范围，
	* 如index为0时，表示facilityBits代表了map中前64个facility
	*/
    public static List<String> convertFacility(long facilityBits, Map<Integer, FacilityIconDO> map, int index) {
        if(map == null)
            return null;
        List<String> facilities = new ArrayList<String>();
        long mask = 1;
        for(int i = 0;i < 64;i++) {
            if(map.get(i + index * 64) == null)
                break;
            if((facilityBits & mask) == 1) {
                facilities.add(map.get(i + index * 64).getName());
            }
            facilityBits = facilityBits >>> 1;
        }
        return facilities;
    }
    /*
	* list转bit
	* 如index为0时，表示facilityBits代表了map中前64个facility
	*/
    public static long convertLong(List<String> facilityList,int index) {
        if(facilityList == null || facilityList.size() == 0){
            return 0;
        }
        //排序
        Collections.sort(facilityList);
        long facilityBits = 0;
        for (String facility:facilityList){
            facilityBits = facilityBits | 1 << Integer.parseInt(facility);
        }

        return facilityBits;
    }
     
     public static void main(String[] args) {
    	 
/*         testDO.setHotelFacility(12l);
         testDO.setRoomFacility(13l);
         testDO.setRoomService(10l);
*/    	 
/*    	 System.out.println(BitUtil.longToBytes(12l).toString());
    	 byte[] bytes = BitUtil.longToBytes(12l);
    	 
    	 for (int i = 0; i < bytes.length; i ++) {
    		 
    		 System.out.print(bytes[i]);
    		 
    	 }*/
    	 
    	 System.out.println(Long.parseLong(Long.toBinaryString(12L),2));
    	 
     }
     
}
