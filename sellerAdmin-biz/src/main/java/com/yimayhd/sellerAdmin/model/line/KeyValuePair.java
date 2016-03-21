package com.yimayhd.sellerAdmin.model.line;

/**
 * 键值对
 * 
 * @author yebin
 *
 * @param <K>
 * @param <V>
 */
public class KeyValuePair<K, V> {
	private K key;
	private V value;

	public KeyValuePair(K key, V value) {
		this.key = key;
		this.value = value;
	}

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

}
