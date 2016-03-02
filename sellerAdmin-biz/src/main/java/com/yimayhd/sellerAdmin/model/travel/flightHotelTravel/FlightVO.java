package com.yimayhd.sellerAdmin.model.travel.flightHotelTravel;

import java.util.Date;

import com.yimayhd.ic.client.model.domain.share_json.FlightDetail;

/**
 * 往返航班
 * 
 * @author yebin
 *
 */
public class FlightVO {
	// 去
	private Date goOff;
	private String goFromCity;
	private String goFromAirport;
	private String goToCity;
	private String goToAirport;
	private String goAirways;
	private String goFlightNumber;
	private Date goPlanStart;
	private Date goPlanEnd;
	// 回
	private Date backOff;
	private String backFromCity;
	private String backFromAirport;
	private String backToCity;
	private String backToAirport;
	private String backAirways;
	private String backFlightNumber;
	private Date backPlanStart;
	private Date backPlanEnd;
	private String noteInfo;

	public FlightVO() {
	}

	public FlightVO(FlightDetail detail) {
		this.goFromCity = detail.getForwardDepartCity();
		this.goFromAirport = detail.getForwardDepartStation();
		this.goToCity = detail.getForwardArriveCity();
		this.goToAirport = detail.getForwardArriveStation();
		this.goAirways = detail.getForwardCompanyName();
		this.goFlightNumber = detail.getForwardFlightNum();
		this.goPlanStart = new Date(detail.getForwardDepartTime());
		this.goPlanEnd = new Date(detail.getForwardArriveTime());

		this.backFromCity = detail.getReturnDepartCity();
		this.backFromAirport = detail.getReturnDepartStation();
		this.backToCity = detail.getReturnArriveCity();
		this.backToAirport = detail.getReturnArriveStation();
		this.backAirways = detail.getReturnCompanyName();
		this.backFlightNumber = detail.getReturnFlightNum();
		this.backPlanStart = new Date(detail.getReturnDepartTime());
		this.backPlanEnd = new Date(detail.getReturnArriveTime());
	}

	public Date getGoOff() {
		return goOff;
	}

	public void setGoOff(Date goOff) {
		this.goOff = goOff;
	}

	public String getGoFromCity() {
		return goFromCity;
	}

	public void setGoFromCity(String goFromCity) {
		this.goFromCity = goFromCity;
	}

	public String getGoFromAirport() {
		return goFromAirport;
	}

	public void setGoFromAirport(String goFromAirport) {
		this.goFromAirport = goFromAirport;
	}

	public String getGoToCity() {
		return goToCity;
	}

	public void setGoToCity(String goToCity) {
		this.goToCity = goToCity;
	}

	public String getGoToAirport() {
		return goToAirport;
	}

	public void setGoToAirport(String goToAirport) {
		this.goToAirport = goToAirport;
	}

	public String getGoAirways() {
		return goAirways;
	}

	public void setGoAirways(String goAirways) {
		this.goAirways = goAirways;
	}

	public String getGoFlightNumber() {
		return goFlightNumber;
	}

	public void setGoFlightNumber(String goFlightNumber) {
		this.goFlightNumber = goFlightNumber;
	}

	public Date getGoPlanStart() {
		return goPlanStart;
	}

	public void setGoPlanStart(Date goPlanStart) {
		this.goPlanStart = goPlanStart;
	}

	public Date getGoPlanEnd() {
		return goPlanEnd;
	}

	public void setGoPlanEnd(Date goPlanEnd) {
		this.goPlanEnd = goPlanEnd;
	}

	public Date getBackOff() {
		return backOff;
	}

	public void setBackOff(Date backOff) {
		this.backOff = backOff;
	}

	public String getBackFromCity() {
		return backFromCity;
	}

	public void setBackFromCity(String backFromCity) {
		this.backFromCity = backFromCity;
	}

	public String getBackFromAirport() {
		return backFromAirport;
	}

	public void setBackFromAirport(String backFromAirport) {
		this.backFromAirport = backFromAirport;
	}

	public String getBackToCity() {
		return backToCity;
	}

	public void setBackToCity(String backToCity) {
		this.backToCity = backToCity;
	}

	public String getBackToAirport() {
		return backToAirport;
	}

	public void setBackToAirport(String backToAirport) {
		this.backToAirport = backToAirport;
	}

	public String getBackAirways() {
		return backAirways;
	}

	public void setBackAirways(String backAirways) {
		this.backAirways = backAirways;
	}

	public String getBackFlightNumber() {
		return backFlightNumber;
	}

	public void setBackFlightNumber(String backFlightNumber) {
		this.backFlightNumber = backFlightNumber;
	}

	public Date getBackPlanStart() {
		return backPlanStart;
	}

	public void setBackPlanStart(Date backPlanStart) {
		this.backPlanStart = backPlanStart;
	}

	public Date getBackPlanEnd() {
		return backPlanEnd;
	}

	public void setBackPlanEnd(Date backPlanEnd) {
		this.backPlanEnd = backPlanEnd;
	}

	public String getNoteInfo() {
		return noteInfo;
	}

	public void setNoteInfo(String noteInfo) {
		this.noteInfo = noteInfo;
	}

}
