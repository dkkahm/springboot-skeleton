package com.example.homegym.controller;

import com.example.homegym.ui.request.AccountRequest;
import com.example.homegym.ui.response.AccountResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class HomeControllerTest {

    @Value("${homegym.security.token}")
    String homegymSecurityToken;

    @Value("${homegym.defaults.language}")
    String defaultLanguage;

    @Autowired
    TestRestTemplate testRestTemplate;

    @BeforeEach
    public void cleanup() {
        testRestTemplate.getRestTemplate().getInterceptors().clear();
    }

    @Test
    public void test_whenTokenIsValid_receiveOk() {
        authenticate(homegymSecurityToken);
        ResponseEntity<String> response = testRestTemplate.getForEntity("/test", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void test_whenTokenIsInvalid_receiveBadRequest() {
        authenticate("invalid-token");
        ResponseEntity<String> response = testRestTemplate.getForEntity("/test", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void test_whenTokenIsEmpty_receiveBadRequest() {
        ResponseEntity<String> response = testRestTemplate.getForEntity("/test", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void ex_receiveBadRequest() {
        ResponseEntity<String> response = testRestTemplate.getForEntity("/ex", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void account_whenAccountIsValid_receiveOk() {
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountId("1234");

        ResponseEntity<Object> response = testRestTemplate.postForEntity("/account", accountRequest, Object.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void account_whenAccountIdIsNotTooLong_receiveBadRequest() {
        AccountRequest accountRequest = new AccountRequest();

        String valueOf64Chars = IntStream.rangeClosed(1, 64).mapToObj(x -> "a").collect(Collectors.joining());
        accountRequest.setAccountId(valueOf64Chars);

        ResponseEntity<Object> response = testRestTemplate.postForEntity("/account", accountRequest, Object.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void account_whenAccountIsValid_receiveAccountId() {
        String accountId = "1234";

        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountId(accountId);

        ResponseEntity<AccountResponse> response = testRestTemplate.postForEntity("/account", accountRequest, AccountResponse.class);

        assertThat(response.getBody().getAccountId()).isEqualTo(accountId);
    }

    @Test
    public void account_whenAccountIdIsNull_receiveBadRequest() {
        AccountRequest accountRequest = new AccountRequest();

        ResponseEntity<Object> response = testRestTemplate.postForEntity("/account", accountRequest, Object.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void account_whenAccountIdIsShort_receiveBadRequest() {
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountId("123");

        ResponseEntity<Object> response = testRestTemplate.postForEntity("/account", accountRequest, Object.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void account_whenAccountIdIsLong_receiveBadRequest() {
        AccountRequest accountRequest = new AccountRequest();

        String valueOf65Chars = IntStream.rangeClosed(1, 65).mapToObj(x -> "a").collect(Collectors.joining());
        accountRequest.setAccountId(valueOf65Chars);

        ResponseEntity<Object> response = testRestTemplate.postForEntity("/account", accountRequest, Object.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void lang_receiveOk() {
        ResponseEntity<String> response = testRestTemplate.getForEntity("/lang", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void lang_whenAcceptLanguageIsNotProvided_receiveDefaultLanguage() {
        ResponseEntity<String> response = testRestTemplate.getForEntity("/lang", String.class);

        assertThat(response.getBody()).isEqualTo(defaultLanguage);
    }

    @Test
    public void lang_whenAcceptLanguageIstProvided_receiveProvidedLanguage() {
        String lang = "ab";
        language(lang);
        ResponseEntity<String> response = testRestTemplate.getForEntity("/lang", String.class);

        assertThat(response.getBody()).isEqualTo(lang);
    }

    @Test
    public void lang_whenAcceptLanguageWithCountryIstProvided_receiveProvidedLanguage() {
        String lang = "ab";
        language(lang + "-KR");
        ResponseEntity<String> response = testRestTemplate.getForEntity("/lang", String.class);

        assertThat(response.getBody()).isEqualTo(lang);
    }

    private void authenticate(String token) {
        testRestTemplate.getRestTemplate().getInterceptors().add((req, body, execution) -> {
            req.getHeaders()
                    .add("Authorization", "Bearer " + token);
            return execution.execute(req, body);
        });
    }

    private void language(String language) {
        testRestTemplate.getRestTemplate().getInterceptors().add((req, body, execution) -> {
            req.getHeaders()
                    .add("Accept-Language", language);
            return execution.execute(req, body);
        });
    }
}
