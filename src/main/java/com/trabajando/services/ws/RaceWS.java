package com.trabajando.services.ws;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.json.JSONException;

import com.trabajando.services.bc.RaceBC;
import com.trabajando.services_core.wrapper.ResponseWrapper;
import com.trabajando.services_core.ws.BaseWS;

@Path("/races")
@Api(value="Races")
public class RaceWS extends BaseWS {

	static final Logger log = Logger.getLogger(RaceWS.class);
	
	@GET
	@Path("/getRaceDayLinks")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value="Obtiene los links de las fechas que hubo carreras")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Server error") })
	public Response getRaceDayLinks(
			@ApiParam(value="Año", required = true) @QueryParam("year") Long year,
			@ApiParam(
				value="Idioma a utilizar para respuesta de este servicio", 
				required=false) @HeaderParam("locale") String locale) throws JSONException {
		
		ResponseWrapper response = RaceBC.getInstance(null).getRaceDayLinks(year, locale);
		return doRespondService(response);
	}
	
	@GET
	@Path("/getResultLinks")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value="Obtiene los links de los resultados de carreras")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Server error") })
	public Response getResultLinks(
			@ApiParam(value="Año", required = true) @QueryParam("year") Long year,
			@ApiParam(
				value="Idioma a utilizar para respuesta de este servicio", 
				required=false) @HeaderParam("locale") String locale) throws JSONException {
		
		ResponseWrapper response = RaceBC.getInstance(null).getResultLinks(year, locale);
		return doRespondService(response);
	}
	
	@POST
	@Path("/saveResultLinks")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value="Guarda los links de los resultados de carreras")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Server error") })
	public Response saveResultLinks(
			@ApiParam(value="Año", required = true) @QueryParam("year") Long year,
			@ApiParam(
				value="Idioma a utilizar para respuesta de este servicio", 
				required=false) @HeaderParam("locale") String locale) throws JSONException {
		
		ResponseWrapper response = RaceBC.getInstance(null).saveResultLinks(year, locale);
		return doRespondService(response);
	}
	
	@POST
	@Path("/parseRaces")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value="Parsea y guarda los resultados de carreras")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Server error") })
	public Response parseRaces(
			@ApiParam(value="Año", required = true) @QueryParam("year") Long year,
			@ApiParam(
				value="Idioma a utilizar para respuesta de este servicio", 
				required=false) @HeaderParam("locale") String locale) throws JSONException {
		
		ResponseWrapper response = RaceBC.getInstance(null).parseRaces(year, locale);
		return doRespondService(response);
	}
}