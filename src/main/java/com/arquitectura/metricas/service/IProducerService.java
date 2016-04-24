package com.arquitectura.metricas.service;

import com.arquitectura.dto.MetricsHealth;
import com.arquitectura.dto.MetricsPosition;
import com.arquitectura.dto.TopicEnum;

public interface IProducerService {

	void sendPosition(TopicEnum topicEnum, MetricsPosition metricRequest);
	
	void sendHealth(TopicEnum topicEnum, MetricsHealth metricRequest);
}
