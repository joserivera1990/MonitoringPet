package com.arquitectura.metricas.rest;

import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

import com.arquitectura.dto.ErrorMessage;
import com.arquitectura.dto.MetricsHealth;
import com.arquitectura.dto.MetricsPosition;

@Named
@Path("/")
@Controller
public class SearchMetricsRest {
	public static final String CODE = "NO_FOUND";
    public static final String DESCRIPCION = "No existe informacion del collar";
    
	@GET
	@Path("metrics-position")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLocalization(@QueryParam("idCollar") String idCollar){

        MetricsPosition metric = new MetricsPosition();
        metric.setIdCollar(idCollar);
        metric.setLatitude("1212.1212");
        metric.setLongitude("12.12");
        if(metric != null){
	         return Response.status(HttpStatus.NOT_FOUND.value()).entity(new ErrorMessage(CODE,DESCRIPCION)).build();
	       }
		return Response.ok(metric).build();
	}
	
	@GET
	@Path("metrics-health")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInformationHealth(@QueryParam("idCollar") String idCollar){

	    MetricsHealth metricas = new MetricsHealth();
	    metricas.setBreathingRate("2323.22");
	    metricas.setHeartRate("sddd");
	    metricas.setIdCollar(idCollar);
	    if(metricas == null){
	         return Response.status(HttpStatus.NOT_FOUND.value()).entity(new ErrorMessage(CODE,DESCRIPCION)).build();
	       }
		return Response.ok(metricas).build();
	}
}
