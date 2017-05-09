package com.trabajando.services.ws;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.trabajando.services.bc.OfferBC;
import com.trabajando.services.wrapper.RepublishRequestWrapper;
import com.trabajando.services_core.wrapper.ResponseWrapper;
import com.trabajando.services_core.ws.BaseWS;

@Path("/offers")
@Api(value="Offers")
public class OfferWS extends BaseWS {
	static final Logger log = Logger.getLogger(OfferWS.class);
	
	@GET
	@Path("/get")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value="Ver una oferta de empleo.")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Server error") })
	public Response get(@ApiParam(value="ID de la oferta", required = true) @QueryParam("jobId") long jobId,
						@ApiParam(value="Token de sesión", required = true) @HeaderParam("sessionToken") String sessionToken) {
		
		ResponseWrapper response = OfferBC.getInstance(sessionToken).get(jobId);
		return doRespondService(response);
	}
	
	@PUT
	@Path("/republish")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Republica oferta.")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Server error") })
	public Response republish(@ApiParam(value="Datos de la oferta.", required = true) RepublishRequestWrapper republishRequest,
								@ApiParam(value="Token de sesión", required=true) @HeaderParam("sessionToken") String sessionToken) {
		
		ResponseWrapper response = OfferBC.getInstance(sessionToken).republish(republishRequest);
		return doRespondService(response);
	}
}
