package com.trabajando.services.wrapper;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@XmlRootElement
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class BaseSOAWrapper implements Serializable {
	

	/**
	 * Clase que indica valores base para hacer la llamada a SOA
	 */
	private static final long serialVersionUID = -7149442334451779055L;
	@ApiModelProperty(hidden=true)
	protected String country;
	@ApiModelProperty(hidden=true)
	protected String client;
	@ApiModelProperty(hidden=true)
	protected String token;
	@ApiModelProperty(hidden=true)
	protected Long domainId;
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getDomainId() {
		return domainId;
	}
	public void setDomainId(Long domainId) {
		this.domainId = domainId;
	}
	@Override
	public String toString() {
		return "BaseSOA [country=" + country + ", client=" + client
				+ ", token=" + token + ", domainId=" + domainId + "]";
	}
}
