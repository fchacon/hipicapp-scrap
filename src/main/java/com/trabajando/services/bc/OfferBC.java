package com.trabajando.services.bc;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.trabajando.services.wrapper.RepublishRequestWrapper;

import com.trabajando.services_core.bc.BaseBC;
//import com.trabajando.services_core.entity.User;
import com.trabajando.services_core.wrapper.ResponseWrapper;

public class OfferBC extends BaseBC {
	
	static final Logger log = Logger.getLogger(OfferBC.class);
	
	private static OfferBC offerBC;
	public static OfferBC getInstance(String token) {
		if (offerBC == null)
			offerBC = new OfferBC();
		
		offerBC.setToken(token);
		return offerBC;
	}
	
	private OfferBC() {}
	
	/**
	 * Método que retorna los datos de una oferta
	 * @author fchacon
	 * @param jobId ID de la oferta.
	 * @return ResponseWrapper json de respuesta del servicio SOA.
	 */
	public ResponseWrapper get(long jobId) {
		
		//TODO Ver tema de permisos
//		User user = getUser();
//		if(user == null) {
//			return getForbiddenResponse(getLocale(user));
//		}
		
		ResponseWrapper response = new ResponseWrapper();
		
		//Estos datos debieran obtenerse de sesión/configuración
		String locale = "es_CL"; 
		String country = "CL";
		String domainId = "857";
		String client = "NPP";
		String token = "NPP";
		
		Map<String, String> serviceParameters = new HashMap<String, String>();
		serviceParameters.put("country", country);
		serviceParameters.put("domainid", domainId);
		serviceParameters.put("jobid", String.valueOf(jobId));
		serviceParameters.put("client", client);
		serviceParameters.put("token", token);
		
		//Esta URL debiera obtenerse desde un archivo .properties
		String url = "http://192.168.1.224:8085/jobs-v1.4/rest/json/getjob";
		
		try {
			String soaResponse = doGet(url, serviceParameters);
			getDataFromJSON(soaResponse, response);
			
		} catch (JSONException | ClientHandlerException | UniformInterfaceException e) {
			log.error("Ha ocurrido un error: "+e.getMessage());
			response.setError(-1, t("services.error.internal", locale));
		}
		
		return response;
	}
	
	/**
	 * Método controlador encargado de llamar a servicio SOA que republica una oferta.
	 * @param republishRequest objeto que contiene el id del dominio, id de la empresa e id de la oferta.
	 * @return ResponseWrapper json de respuesta del servicio SOA.
	 */
	public ResponseWrapper republish(RepublishRequestWrapper republishRequest) {
		
		// TODO ver permisos
//		User user = getUser();
//		if (user == null) {
//			return getForbiddenResponse(getLocale(user));
//		}
		
		ResponseWrapper response = new ResponseWrapper();
		
		//Estos datos debieran obtenerse de sesión/configuración
		String locale = "es_CL"; 
		String country = "CL";
		String client = "NPP";
		String token = "NPP";
		
		//Esta URL debiera obtenerse desde un archivo .properties
		String url = "http://192.168.1.224:8085/jobs-v1.4/rest/json/republish";
		
		try {
			republishRequest.setClient(client);
			republishRequest.setToken(token);
			republishRequest.setCountry(country);
			JSONObject requestSOA = new JSONObject(republishRequest);
			
			String soaResponse = doPut(url, requestSOA.toString());
			
			getDataFromJSON(soaResponse, response);

		} catch (JSONException | ClientHandlerException | UniformInterfaceException e){
			log.error("Ha ocurrido un error: "+e.getMessage());
			response.setError(-1, t("services.error.internal", locale));
		}
		
		return response;
	}
}
