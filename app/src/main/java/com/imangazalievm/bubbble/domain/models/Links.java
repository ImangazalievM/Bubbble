package com.imangazalievm.bubbble.domain.models;

public class Links {

    private String web;
    private String twitter;

    public Links(String web, String twitter) {
        this.web = web;
        this.twitter = twitter;
    }

    public String getWeb() {
        return web;
    }

    public String getTwitter() {
        return twitter;
    }
}
