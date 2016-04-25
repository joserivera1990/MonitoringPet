package com.arquitectura.metricas.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

@Service
public class RedisService implements IRedisService {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerService.class);
    public static final String SERVER_LOCAL = "localhost";
      
    @Override
	public String getRegister(String idCollar,String redisServer){
		  String value = "";
		  try {
			  Jedis jedis = new Jedis(redisServer, 6379);
		      value = jedis.get(idCollar);
		 } catch (JedisConnectionException e) {
			  LOGGER.error("Error trying get connection redis", e);
		      throw e;
		 } 

		return value;
	}
}
