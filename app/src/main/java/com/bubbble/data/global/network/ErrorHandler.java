package com.bubbble.data.global.network;

import com.google.gson.Gson;

import javax.inject.Inject;

import retrofit2.Response;

public class ErrorHandler {

    private Gson gson;

    @Inject
    public ErrorHandler(Gson gson) {
        this.gson = gson;
    }

    public ServerError parseError(Response<?> response) {
        try {
            return gson.fromJson(response.errorBody().string(), ServerError.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            response.errorBody().close();
        }
    }
}
