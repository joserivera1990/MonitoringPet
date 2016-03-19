package com.arquitectura.metricas.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.arquitectura.dto.MetricsHealth;
import com.arquitectura.dto.MetricsPosition;
import com.arquitectura.dto.TopicEnum;
/**
 *
 * @author sergioleottau
 */
@Service
public class ProducerService implements IProducerService {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerService.class);
    
    
    /**
     * Propiedades del productor
     */
    private Properties props;

    @PostConstruct
    public void init() {
        System.out.println("init ingresando");
        props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

    }

    @Override
    public void sendHealth(TopicEnum topicEnum, MetricsHealth metricRequest) throws Exception {
    	props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    	 try (Producer<String, String> producer = new KafkaProducer<>(props)) {
        	ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(metricRequest);
            producer.send(new ProducerRecord<>(topicEnum.getId(),
                    metricRequest.getIdCollar(), 
                    json));
        } catch (Exception e) {
            LOGGER.error("Error trying to send the message [{}] to kafka", 
                    metricRequest.toString(), e);
            throw e;
        }

    }
    
    @Override
    public void sendPosition(TopicEnum topicEnum, MetricsPosition metricRequest) throws Exception {
    	props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    	 try (Producer<String, String> producer = new KafkaProducer<>(props)) {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(metricRequest);
            producer.send(new ProducerRecord<>(topicEnum.getId(),
                    metricRequest.getIdCollar(), 
                    json));
        } catch (Exception e) {
            LOGGER.error("Error trying to send the message [{}] to kafka", 
                    metricRequest, e);
            throw e;
        }

    }

}