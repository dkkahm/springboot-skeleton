package com.example.homegym.controller;

import com.example.homegym.exceptions.HomeGymException;
import com.example.homegym.ui.request.AccountRequest;
import com.example.homegym.ui.response.AccountResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "OK";
    }

    @GetMapping("/test")
    public String test() {
        return "OK";
    }

    @GetMapping("/ex")
    public String ex() {
        throw new HomeGymException("ex");
    }

    @PostMapping("/account")
    public AccountResponse account(@Valid @RequestBody AccountRequest accountRequest) {
        return AccountResponse.builder().accountId(accountRequest.getAccountId()).build();
    }

    @GetMapping("/lang")
    public String lang(HttpServletRequest request) {
        String language = (String)request.getAttribute("language");
        return language;
    }
}
