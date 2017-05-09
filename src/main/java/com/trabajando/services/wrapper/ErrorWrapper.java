package com.trabajando.services.wrapper;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@XmlRootElement
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ErrorWrapper implements Serializable {
	
	/**
	 * Clase que contiene un codigo y mensaje de error
	 */
	private static final long serialVersionUID = 4741888170937063074L;
	private long code;
	private String message;
	
	public ErrorWrapper() {
	}
	public ErrorWrapper(int code, String message) {
		this.code = code;
		this.message = message;
	}
	public long getCode() {
		return code;
	}
	public void setCode(long code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "ErrorWrapper [code=" + code + ", message=" + message + "]";
	}
	
}
