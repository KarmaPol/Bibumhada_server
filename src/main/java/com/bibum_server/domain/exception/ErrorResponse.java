package com.bibum_server.domain.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ErrorResponse {

    private final String code;
    private final String message;
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private Map<String, String> validation = new HashMap<>();

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public void addValidation(String field, String message) {
        validation.put(field, message);
    }
}
