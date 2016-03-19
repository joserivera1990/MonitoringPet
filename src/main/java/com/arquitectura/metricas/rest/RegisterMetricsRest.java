package com.arquitectura.metricas.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.arquitectura.dto.TopicEnum;
import com.arquitectura.metricas.service.IProducerService;
import com.arquitectura.metricas.service.ProducerService;

@Named
@Path("/")
@Controller
public class RegisterMetricsRest {
	public static final String CODE = "NO_FOUND";
    public static final String DESCRIPCION = "Error acediendo al servidor";
    
	@POST
    @Path("/metrics-position")
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseStatus(HttpStatus.OK)
	public Response registerLocalization(MetricsPosition  metrics){
		IProducerService producer = new ProducerService();
	       DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	       String DateToStr = format.format(new Date());
		   metrics.setBeginDate(DateToStr);
		try {
			producer.sendPosition(TopicEnum.POSITION_TOPIC, metrics);
		} catch (Exception e) {
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
	   IProducerService producer = new ProducerService();
       DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
       String DateToStr = format.format(new Date());
	    metrics.setBeginDate(DateToStr);
	   try {
		producer.sendHealth(TopicEnum.HEALTH_TOPIC, metrics);
	    } catch (Exception e) {
	    	return Response.status(HttpStatus.NOT_FOUND.value()).entity(new ErrorMessage(CODE,DESCRIPCION)).build();
	    }
	   return Response.ok().build();
	}
	
}
