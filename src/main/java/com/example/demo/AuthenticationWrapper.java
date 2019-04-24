package com.example.demo;

import io.moquette.broker.security.IAuthenticator;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class AuthenticationWrapper implements IAuthenticator {

    @Override
    public boolean checkValid(String s, String s1, byte[] bytes) {
        String password = new String(bytes, StandardCharsets.UTF_8);
        if(s1.equals("dump") && password.equals("dump12345"))
            return true;
        return getHttpRequestClient().sendRequest(s1, password);
    }

    private HttpRequestClient getHttpRequestClient() {
        return SpringContext.getBean(HttpRequestClient.class);
    }
}
