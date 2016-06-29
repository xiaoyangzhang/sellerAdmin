//package com.yimayhd.sellerAdmin.tair;
//
//import java.io.Serializable;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.alibaba.fastjson.JSON;
//import com.taobao.tair.DataEntry;
//import com.taobao.tair.Result;
//import com.taobao.tair.ResultCode;
//import com.taobao.tair.TairManager;
//
///**  
// * @author: wuzhengfei@pajk.cn  
// * @date: 2014年6月22日 下午11:02:16
// * 
// */
//public class TcCacheManager {
//	private static final Logger log = LoggerFactory.getLogger(TcCacheManager.class);
//
//	private TairManager tairManager ;
//	
//	private int namespace;
//	
//	public boolean addToTair(String key, Serializable serializable ){
//		int expireTime = 0 ;
//		return addToTair(key, serializable, expireTime);
//	}
//	
//	/**
//	 * 插入到tair中
//	 * @param serializable
//	 * @param key
//	 * @param expireTime
//	 * @return
//	 */
//	public boolean addToTair(String key, Serializable serializable , int expireTime){
//		int version = 0 ;
//		return addToTair(key, serializable, version, expireTime);
//	}
//	
//	
//	public boolean addToTair(String key, Serializable serializable, int version, int expireTime){
//		ResultCode resultCode = tairManager.put(namespace, key, serializable, version, expireTime);
//		if( resultCode.isSuccess() ){
//			log.info("add data to tair success! namespace="+namespace+"    key="+key+"   expireTime="+expireTime+" version="+version);
//			return true;
//		}else{
//			log.error("add data to tair fail! namespace="+namespace+"    key="+key+"   expireTime="+expireTime+" version="+version+"  ResultCode="+JSON.toJSONString(resultCode));
//			return false;
//		}
//	}
//	
//	
//	
//	
//	public Object getFormTair(String key){
//		if (key == null) {
//			return null;
//		}
//		Result<DataEntry> result = null ;
//    	try {
//    		result = tairManager.get(namespace, key);
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("get data from tair fail!    key="+key +"; \t\t msg="+e.getMessage());
//		}
//    	//如果ResultCode.SUCCESS，那么返回缓存数据，如果返回的数据已过期，那么更新缓存数据，否则从数据库重新更新数据
//		if (result == null || result.getValue() == null || result.getValue().getValue() == null) {
//			return null;
//		}
//		return result.getValue().getValue();
//	}
//
//	public String getStringFromTair(String key) {
//		Object ob = getFormTair(key);
//		if (ob != null) {
//			return (String) ob;
//		}
//		return null;
//	}
//	
//	/**
//	 * 从tair中删除
//	 * @param key
//	 * @return
//	 */
//	public boolean deleteFromTair(String key){
//		ResultCode rc = tairManager.delete(namespace, key);
//		if( ResultCode.SUCCESS == rc ){
//			log.debug("delete data from tair success !    key="+key);
//			return true ;
//		}else{
//			log.error("delete data from tair fail !    key="+key+"   ResultCode="+JSON.toJSONString(rc));
//			return false;
//		}
//	}
//	
//	/**
//	 * 计数器功能
//	 * @param key 
//	 * @param delta ： 每次增长的值，非法（<=0）时，设为1
//	 * @return
//	 */
//	public boolean inc(String key, int delta){
//		if( delta <= 0 ){
//			delta = 1 ;
//		}
//		Result<Integer> rs = tairManager.incr(namespace, key, delta, 0, 0);
//		ResultCode rc = rs.getRc();
//		if( ResultCode.SUCCESS == rc ){
////			log.debug("tair inc success !    key="+key);
//			return true ;
//		}else{
//			log.error("tair inc fail !    key="+key+"    delta="+delta+"  ResultCode="+JSON.toJSONString(rc));
//			return false;
//		}
//	}
//
//
//
//    /**
//     * 计数器功能
//     * @param key
//     * @param delta ： 每次增长的值，非法（<=0）时，设为1
//     * @return
//     */
//
//    public int inc(String key,int delta,int defaultValue){
//        if( delta <= 0 ){
//            delta = 1 ;
//        }
//        Result<Integer> rs = tairManager.incr(namespace, key, delta, defaultValue, 0);
//        ResultCode rc = rs.getRc();
//        if( ResultCode.SUCCESS == rc ){
//            return rs.getValue();
//        }else{
//            log.error("tair inc fail !    key="+key+"    delta="+delta+"  ResultCode="+JSON.toJSONString(rc));
//            return 0;
//        }
//    }
//	
////	public boolean addItemsToTair(String key, List<? extends Serializable> items, int maxCount, int expireTime){
////		int version = 0 ;
//////		System.err.println();
//////		byte[] bs = Json.serialize(items);
////		ResultCode putRc = tairManager.addItems(namespace, key, items, maxCount, version, expireTime);
////		if( putRc.isSuccess() ){
////			log.debug("add items to tair success! namespace="+namespace+"    key="+key+"   expireTime="+expireTime+" version="+version);
////			return true;
////		}else{
////			log.debug("add items to tair fail! namespace="+namespace+"    key="+key+"   expireTime="+expireTime+" version="+version);
////			return false;
////		}
////	}
////	
////	public List<? extends Serializable> getItemsFromTair(String key, int pageNo, int pageSize){
////		int offset = ( pageNo - 1) * pageSize ;
////		if( offset < 0 ){
////			offset = 0 ;
////		}
////		Result<DataEntry> rs = tairManager.getItems(namespace, key, offset, pageSize);
////		if( rs == null ){
////			return null ;
////		}
////		ResultCode rc = rs.getRc() ;
////		if( rc.isSuccess() ){
////			log.debug("get items to tair success! namespace="+namespace+"    key="+key+"   pageNo="+pageNo+" pageSize="+pageSize);
////			Object obj = rs.getValue() ;
////			if( obj != null ){
////				return (List<? extends Serializable>)obj ;
////			}
////			return null;
////		}else{
////			log.debug("get items to tair fail! namespace="+namespace+"    key="+key+"   pageNo="+pageNo+" pageSize="+pageSize);
////			return null;
////		}
////	}
////	
////	
////	public boolean removeItems(String key, int offset, int count){
////		ResultCode putRc = tairManager.removeItems(namespace, key, offset, count);
////		if( putRc.isSuccess() ){
////			log.debug("remove items from tair success! namespace="+namespace+"    key="+key+"   offset="+offset+" count="+count);
////			return true;
////		}else{
////			log.debug("remove items from tair fail! namespace="+namespace+"    key="+key+"   offset="+offset+" count="+count);
////			return false;
////		}
////	}
////	
////	public int getItemCount(String key){
////		Result<Integer> rs = tairManager.getItemCount(namespace, key);
////		ResultCode rc = rs.getRc() ;
////		if( rc.isSuccess() ){
////			log.debug("get items count success! namespace="+namespace+"    key="+key);
////			Integer count = rs.getValue() ;
////			if( count != null ){
////				return  count;
////			}
////			return 0;
////		}else{
////			log.debug("get items count fail! namespace="+namespace+"    key="+key);
////			return 0;
////		}
////	}
//	
//	
//
//	public TairManager getTairManager() {
//		return tairManager;
//	}
//
//    public void setTairManager(TairManager tairManager) {
//		this.tairManager = tairManager;
//	}
//
//	public int getNamespace() {
//		return namespace;
//	}
//
//	public void setNamespace(int namespace) {
//		this.namespace = namespace;
//	}
//}
