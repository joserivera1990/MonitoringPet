package com.arquitectura.metricas.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

@Service
public class RedisService implements IRedisService {
	
    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerService.class);
    
        
    @Override
	public String getRegister(String idCollar)  throws Exception{
		  System.out.println("idCollar"+idCollar);
		  String value = "";
		 try {
			 Jedis jedis = new Jedis("localhost"); 
		      value = jedis.get(idCollar);
		} catch (Exception e) {
			  LOGGER.error("Error trying get data redis", e);
		      throw e;
		}

		return value;
	}
}
