package com.backbase.openbank.service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;

public abstract class ObjectMapperUtil {

    private static final Logger LOG = LogManager.getLogger(ObjectMapperUtil.class);

    public static ObjectMapper getInstance() {
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .enable(SerializationFeature.INDENT_OUTPUT);
    }

    public static <T> String getJsonString(T object) {
        try {
            return getInstance().writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            LOG.error("Failed to parse Response", e);
        }

        return null;
    }

    public static <T> String convertResponseToJson(ResponseEntity<T> response) {
        return "\n----------- Response -----------\n" +
                "HTTP Status: " + response.getStatusCode() + "\n" +
                "Response Body: \n"
                + getJsonString(response.getBody()) +
                "\n--------------------------------\n";
    }
}
