package com.arquitectura.metricas.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.arquitectura.dto.ErrorMessage;
import com.arquitectura.dto.MetricsHealth;
import com.arquitectura.dto.MetricsPosition;
import com.arquitectura.dto.TopicEnum;
import com.arquitectura.metricas.service.IProducerService;

@Named
@Path("/")
@Controller
public class RegisterMetricsRest {
	public static final String CODE = "NO_FOUND";
    public static final String DESCRIPCION = "Error acediendo al servidor";
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterMetricsRest.class);
    
    @Autowired
    private IProducerService producerService;
    
	@POST
    @Path("/metrics-position")
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public Response registerLocalization(MetricsPosition  metrics){
	       DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	       String dateString = format.format(new Date());
		   metrics.setBeginDate(dateString);
		try {
			producerService.sendPosition(TopicEnum.POSITION_TOPIC, metrics);
		} catch (Exception e) {
			LOGGER.error("Error receiving requet metrics-position [{}]", 
					metrics.toString(), e);
			return Response.status(HttpStatus.NOT_FOUND.value()).entity(new ErrorMessage(CODE,DESCRIPCION)).build();
		}
		return Response.ok().build();
	}
	

	@POST
    @Path("/metrics-health")
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Response saveInformationHealth(MetricsHealth metrics){
       DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
       String dateString = format.format(new Date());
	    metrics.setBeginDate(dateString);
	   try {
		   producerService.sendHealth(TopicEnum.HEALTH_TOPIC, metrics);
	    } catch (Exception e) {
	    	LOGGER.error("Error receiving requet metrics-health [{}]", 
					metrics.toString(), e);
	    	return Response.status(HttpStatus.NOT_FOUND.value()).entity(new ErrorMessage(CODE,DESCRIPCION)).build();
	    }
	   return Response.ok().build();
	}
	
}
