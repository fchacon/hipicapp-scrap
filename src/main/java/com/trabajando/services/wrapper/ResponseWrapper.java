package com.trabajando.services.wrapper;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@XmlRootElement
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResponseWrapper implements Serializable {

	/**
	 * Clase que contiene la respuesta del servicio , siempre y cuando sea propia del proyecto, si es de SOA se ocupa la de jersey
	 */
	private static final long serialVersionUID = 6670827453379325968L;
	private Object data;
	private ErrorWrapper error;
	private String status;
	private String message;
	private Integer httpStatus;
	
	//Número de resultados entregados por el request
	private Long totalRecords;
	
	//Número de resultados por página
	private Long rowsNumber;
	
	//Página actual
	private Long pageNumber;
	
	//Número de páginas resultantes
	private Long totalPages;
	
	public ResponseWrapper() {}

	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.status = "OK";
		this.data = data;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.status = "OK";
		this.message = message;
	}
	
	public Integer getHttpStatus() {
		return httpStatus;
	}
	
	public void setHttpStatus(Integer httpStatus) {
		this.httpStatus = httpStatus;
	}

	public Long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public Long getRowsNumber() {
		return rowsNumber;
	}

	public void setRowsNumber(Long rowsNumber) {
		this.rowsNumber = rowsNumber;
	}

	public Long getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Long pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Long getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Long totalPages) {
		this.totalPages = totalPages;
	}

	public ErrorWrapper getError() {
		return error;
	}
	
	public void setError(ErrorWrapper error) {
		this.status = "NOK";
		this.error = error;
	}
	
	public void setError(int code, String message) {
		this.status = "NOK";
		this.error = new ErrorWrapper(code, message);
	}

	@Override
	public String toString() {
		return "ResponseWrapper [data=" + data + ", error=" + error
				+ ", status=" + status + ", message=" + message
				+ ", httpStatus=" + httpStatus + ", totalRecords="
				+ totalRecords + ", rowsNumber=" + rowsNumber + ", pageNumber="
				+ pageNumber + ", totalPages=" + totalPages + "]";
	}
}
