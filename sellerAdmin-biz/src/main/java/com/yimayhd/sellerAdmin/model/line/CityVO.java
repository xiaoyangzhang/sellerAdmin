package com.yimayhd.sellerAdmin.model.line;


/**
 * 
 * 城市VO
 * 
 * @author yebin
 *
 */
public class CityVO extends TagDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -522963027064686042L;
	private String code;
	private City city;
	private long pid;
	private int pcode;
	private String pname;

	public CityVO() {
		super();
	}

	public CityVO(long id, String name, City city) {
		super(id, name);
		this.city = city;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public int getPcode() {
		return pcode;
	}

	public void setPcode(int pcode) {
		this.pcode = pcode;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}


}
