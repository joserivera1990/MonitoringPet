package com.arquitectura.dto;

import java.util.ArrayList;
import java.util.List;


/**
 * Enumeracion de los topicos
 *
 * @author sergioleottau
 */
public enum TopicEnum {

    HEALTH_TOPIC("HEALTH_TOPIC"),
    POSITION_TOPIC("POSITION_TOPIC"),
    RETRY_HEALTH_TOPIC("RETRY_HEALTH_TOPIC"),
    RETRY_POSITION_TOPIC("RETRY_POSITION_TOPIC");

    private String id;

    private TopicEnum(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    /**
     * Retorna una lista con los identificadores de los tipocos.
     *
     * @return
     */
    public static List<String> getIdArray() {

        List<String> ids = new ArrayList<>();

        for (TopicEnum topicEnum : TopicEnum.values()) {
            ids.add(topicEnum.getId());
        }

        return ids;
    }

}

