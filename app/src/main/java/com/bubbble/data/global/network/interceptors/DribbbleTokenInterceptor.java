package com.bubbble.data.global.network.interceptors;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class DribbbleTokenInterceptor implements Interceptor {

    private String token;

    private static final String KEY_AUTH = "Authorization";

    public DribbbleTokenInterceptor(String token) {
        this.token = "Bearer " + token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request tokenRequest = originalRequest.newBuilder()
                .addHeader(KEY_AUTH, token)
                .build();
        return chain.proceed(tokenRequest);
    }

}