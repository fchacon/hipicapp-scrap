package com.trabajando.services.wrapper;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Wrapper que recibe una lista de ids para republicar una o más ofertas.
 * @author Carlos Dettoni
 *
 */
@XmlRootElement
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class RepublishRequestWrapper extends BaseSOAWrapper implements Serializable {

	private static final long serialVersionUID = 3146714288436511569L;

	@ApiModelProperty(value="Lista de Ids de oferta a republicar.", required=true)
	private List<Long> jobIds;
	
	public RepublishRequestWrapper() {}

	public List<Long> getJobIds() {
		return jobIds;
	}

	public void setJobIds(List<Long> jobIds) {
		this.jobIds = jobIds;
	}

	@Override
	public String toString() {
		return "RepublishRequestWrapper [jobIds=" + jobIds + "]";
	}
	
}