package com.arquitectura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MetricsPosition {
	
	private String idCollar;
	
	private String latitude;
	
	private String longitude;
	
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
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	@Override
	public String toString() {
		return "MetricsPosition [idCollar=" + idCollar + ", latitude="
				+ latitude + ", longitude=" + longitude + ", beginDate="
				+ beginDate + "]";
	}
    

}
