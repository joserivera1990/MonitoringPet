package com.arquitectura.metricas.rest;

import java.io.IOException;

import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

import com.arquitectura.dto.ErrorMessage;
import com.arquitectura.dto.MetricsHealth;
import com.arquitectura.dto.MetricsPosition;
import com.arquitectura.metricas.service.IRedisService;
import com.arquitectura.metricas.service.ProducerService;
import com.arquitectura.metricas.service.RedisService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Named
@Path("/")
@Controller
public class SearchMetricsRest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProducerService.class);
	public static final String CODE = "NO_FOUND";
    public static final String DESCRIPCION = "No existe informacion del collar";
    public static final String HEALTH = "HEALTH";
    public static final String POSITION = "POSITION";
    public static final String DESCRIPCION_ERROR = "Error acediendo al servidor";
    public static final String DESCRIPCION_JSON = "Error serializando datos";
	
    @GET
	@Path("metrics-position")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLocalization(@QueryParam("idCollar") String idCollar){
				
		ObjectMapper mapper = new ObjectMapper();
		IRedisService redis = new RedisService();
		String register = "";
		MetricsPosition metric = new MetricsPosition();
		try {
			register = redis.getRegister(idCollar+"-"+POSITION);
	        if(register == null){
		         return Response.status(HttpStatus.NOT_FOUND.value()).entity(new ErrorMessage(CODE,DESCRIPCION)).build();
		    }	        
	        metric = mapper.readValue(register, MetricsPosition.class);			
		} catch(JsonParseException | JsonMappingException e){
            LOGGER.error("Error serializando objeto", e);
			return Response.status(HttpStatus.NOT_FOUND.value()).entity(new ErrorMessage(CODE,DESCRIPCION_JSON)).build();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			LOGGER.error("Error en getLocalization :", e);
			return Response.status(HttpStatus.NOT_FOUND.value()).entity(new ErrorMessage(CODE,e.getMessage())).build();
		}

		return Response.ok(metric).build();
	}
	
	@GET
	@Path("metrics-health")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInformationHealth(@QueryParam("idCollar") String idCollar){
		ObjectMapper mapper = new ObjectMapper();
		IRedisService redis = new RedisService();
		String register = "";
		try {
		    register = redis.getRegister(idCollar+"-"+HEALTH);
		    if(register == null){
		         return Response.status(HttpStatus.NOT_FOUND.value()).entity(new ErrorMessage(CODE,DESCRIPCION)).build();
		    }
		    MetricsHealth metricas = mapper.readValue(register, MetricsHealth.class);
			return Response.ok(metricas).build();
		} catch(JsonParseException | JsonMappingException e){
			LOGGER.error("Error serializando objeto", e);
			return Response.status(HttpStatus.NOT_FOUND.value()).entity(new ErrorMessage(CODE,DESCRIPCION_JSON)).build();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			LOGGER.error("Error en getInformationHealth :", e);
			return Response.status(HttpStatus.NOT_FOUND.value()).entity(new ErrorMessage(CODE,e.getMessage())).build();
		}
	   
	}
}
