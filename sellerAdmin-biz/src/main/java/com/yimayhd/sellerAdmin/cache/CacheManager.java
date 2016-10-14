package com.yimayhd.sellerAdmin.cache;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.taobao.tair.DataEntry;
import com.taobao.tair.Result;
import com.taobao.tair.ResultCode;
import com.taobao.tair.TairManager;

/**
 * @author: wuzhengfei@pajk.cn
 * @date: 2014年6月22日 下午11:02:16
 * 
 */
public class CacheManager {
	private static final Logger log = LoggerFactory.getLogger(CacheManager.class);

	private TairManager tairManager;

	private int namespace;

	public boolean addToTair(String key, Serializable serializable) {
		int expireTime = 0;
		int version = 0;
		return addToTair(key, serializable, version, expireTime);
	}

	/**
	 * 插入到tair中
	 * 
	 * @param serializable
	 * @param key
	 * @param expireTime
	 * @return
	 */
	public boolean addToTair(String key, Serializable serializable, int expireTime) {
		int version = 0;
		return addToTair(key, serializable, version, expireTime);
	}

	/**
	 * 插入到tair中
	 * 
	 * @param key
	 * @param serializable
	 * @param version
	 * @param expireTime
	 * @return
	 */
	public boolean addToTair(String key, Serializable serializable, int version, int expireTime) {
		ResultCode putRc = tairManager.put(namespace, key, serializable, version, expireTime);
		if (putRc.isSuccess()) {
			log.debug("add data to tair success!  key={}, expireTime={}, version={} ", key, expireTime,version);
			return true;
		} else {
			log.error("add data to tair fail!  key={}, expireTime={}, version={}, result={} ", key, expireTime,version, JSON.toJSONString(putRc));
			return false;
		}
	}

	public Object getFormTair(String key) {
		Result<DataEntry> result = null;
		try {
			result = tairManager.get(namespace, key);
		} catch (Exception e) {
			log.error("get data from tair fail!    key={}", key, e);
		}
		if (result != null) {
			if (ResultCode.SUCCESS == result.getRc() && result.getValue() != null) {
				return result.getValue().getValue();
			}
			log.warn("get data from tair null, key={} ,result={}", key, JSON.toJSONString(result) );
		}
		return null;
	}

	public boolean isExist(String key) {
		Result<DataEntry> result = null;
		try {
			result = tairManager.get(namespace, key);
		} catch (Exception e) {
			log.error("get data from tair fail!    key={}", key, e );
			return false;
		}
		ResultCode rc = result.getRc();
		if (ResultCode.SUCCESS.equals(rc)) {
			return true;
		}
		return false;
	}

	/**
	 * 从tair中删除
	 * 
	 * @param key
	 * @return
	 */
	public boolean deleteFromTair(String key) {
		ResultCode rc = tairManager.delete(namespace, key);
		if (ResultCode.SUCCESS == rc) {
			log.debug("delete data from tair success !    key={}" + key);
			return true;
		} else {
			log.error("delete data from tair fail !    key={}, result={}", key, JSON.toJSONString(rc) );
			return false;
		}
	}

	/**
	 * 计数器功能
	 * 
	 * @param key
	 * @param delta
	 *            ： 每次增长的值，非法（<=0）时，设为1
	 * @return
	 */
	public int incr(String key, int delta) {
		int expireTime= 0 ;
		return incr(key, delta, expireTime);
	}
	/**
	 * 
	 * @param key
	 * @param delta
	 * @param expireTime
	 * @return
	 */
	public int incr(String key, int delta, int expireTime) {
		int defaultValue = 0 ;
		if (delta <= 0) {
			delta = 1;
		}
		return incr(key, delta, defaultValue, expireTime);
	}

	/**
	 * 原子性计数器减功能
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public int dec(String key, int value) {
		Result<Integer> result = tairManager.decr(namespace, key, value, 0, 0);
		ResultCode rc = result.getRc();
		if (ResultCode.SUCCESS == rc) {
			return result.getValue();
		} else {
			log.error("tair inc fail !    key={}, delta={}, Result={}", key, JSON.toJSONString(result));
			return -1;
		}
	}

	/**
	 * 计数器功能
	 * 
	 * @param key
	 * @param delta
	 *            ： 每次增长的值，非法（<=0）时，设为1
	 * @return
	 */

	public int incr(String key, int delta, int defaultValue, int expireTime) {
		if (delta <= 0) {
			delta = 1;
		}
		if( expireTime < 0 ){
			expireTime = 0 ;
		}
		Result<Integer> rs = tairManager.incr(namespace, key, delta, defaultValue, expireTime);
		ResultCode rc = rs.getRc();
		if (ResultCode.SUCCESS == rc) {
			return rs.getValue();
		} else {
			log.error("tair inc fail !    key={}, delta={}, Result={}", key, JSON.toJSONString(rs));
			return 0;
		}
	}

	/**
	 * 计数器功能
	 *
	 * @param key
	 * @param delta
	 *            ： 自由步进值,自由过期时间
	 * @return
	 */

	public int incrFree(String key, int delta, int defaultValue, int expireTime) {
		Result<Integer> rs = tairManager.incr(namespace, key, delta, defaultValue, expireTime);
		ResultCode rc = rs.getRc();
		if (ResultCode.SUCCESS == rc) {
			return rs.getValue();
		} else {
			log.error("tair inc fail !    key={}, delta={}, Result={}", key, JSON.toJSONString(rs));
			return 0;
		}
	}
	

	public <T> Map<String, T> getBatchFormTair(List<String> keys) {
		Result<List<DataEntry>> result = null;
		try {
			result = tairManager.mget(namespace, keys);
		} catch (Exception e) {
			log.error("get data from tair fail!    keys={}", JSON.toJSONString(keys) ,  e);
		}
		if (result != null) {
			if ( ResultCode.SUCCESS == result.getRc() || ResultCode.PARTSUCC == result.getRc() ) {
				List<DataEntry> entries = result.getValue();
				if( !CollectionUtils.isEmpty(entries) ){
					Map<String, T> map = new HashMap<String, T>();
					for( DataEntry entry : entries ){
						String key = (String) entry.getKey() ;
						T t = (T) entry.getValue() ;
						map.put(key, t);
					}
					return map ;
				}
			}
		}
		return null;
	}

	

	public TairManager getTairManager() {
		return tairManager;
	}

	public void setTairManager(TairManager tairManager) {
		this.tairManager = tairManager;
	}

	public int getNamespace() {
		return namespace;
	}

	public void setNamespace(int namespace) {
		this.namespace = namespace;
	}

}
