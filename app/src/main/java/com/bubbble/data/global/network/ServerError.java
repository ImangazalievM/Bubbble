package com.bubbble.data.global.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServerError {

    @SerializedName("httpStatusCode")
    @Expose
    private int statusCode;
    @SerializedName("message")
    @Expose
    private String message;

    public ServerError() {
        this.statusCode = -1;
        this.message = "Unknown error";
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

}
