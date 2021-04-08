package com.example.homegym.ui.response;

import lombok.Getter;

@Getter
public class CommonResponse {
    private String message;

    public CommonResponse(String message) {
        this.message = message;
    }
}
