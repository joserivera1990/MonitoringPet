package com.arquitectura.metricas.rest;

import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.arquitectura.dto.ErrorMessage;
import com.arquitectura.dto.MetricsHealth;
import com.arquitectura.dto.MetricsPosition;

@Named
@Path("/")
@Controller
public class RegisterMetricsRest {

	@POST
    @Path("/metrics-position")
    @Consumes(MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public Response registerLocalization(MetricsPosition  metrics){
	   return Response.ok().build();
	}
	

	@POST
    @Path("/metrics-health")
    @Consumes(MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Response saveInformationHealth(MetricsHealth metrics){
	   
	   return Response.ok().build();
	}
	
}
