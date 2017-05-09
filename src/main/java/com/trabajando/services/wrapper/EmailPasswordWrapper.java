package com.trabajando.services.wrapper;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@XmlRootElement
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class EmailPasswordWrapper implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3433033158775390579L;
	
	@ApiModelProperty(value = "Email o username del usuario.", required = true)
	private String email;
	
	@ApiModelProperty(value = "Contraseña del usuario.", required = true)
	private String password;
	
	@ApiModelProperty(value = "Campo binario que indica si se debe mantener la sesión abierta.", required = true)
	private int keepSession;
	
	public EmailPasswordWrapper() {}
	
	public EmailPasswordWrapper(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getKeepSession() {
		return keepSession;
	}
	public void setKeepSession(int keepSession) {
		this.keepSession = keepSession;
	}

	@Override
	public String toString() {
		return "EmailPasswordWrapper [email=" + email + ", password="
				+ password + ", keepSession=" + keepSession + "]";
	}
}
