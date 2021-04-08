package com.example.homegym.ui.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ValidationErrorResponse extends CommonResponse {
    private Map<String, String> validationErrors;

    public ValidationErrorResponse() {
        super("Validation Error");
    }
}
