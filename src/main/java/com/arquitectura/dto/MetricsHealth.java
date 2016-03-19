package com.arquitectura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MetricsHealth {
	
	private String idCollar;
	
	private String heartRate;
	
	private String breathingRate;
	
	@JsonInclude(Include.NON_NULL)
	private String beginDate;
	
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getIdCollar() {
		return idCollar;
	}
	public void setIdCollar(String idCollar) {
		this.idCollar = idCollar;
	}
	public String getHeartRate() {
		return heartRate;
	}
	public void setHeartRate(String heartRate) {
		this.heartRate = heartRate;
	}
	public String getBreathingRate() {
		return breathingRate;
	}
	public void setBreathingRate(String breathingRate) {
		this.breathingRate = breathingRate;
	}
	@Override
	public String toString() {
		return "MetricsHealth [idCollar=" + idCollar + ", heartRate="
				+ heartRate + ", breathingRate=" + breathingRate
				+ ", beginDate=" + beginDate + "]";
	}


}
