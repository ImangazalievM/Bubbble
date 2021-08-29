package com.imangazalievm.bubbble.domain.global.models;

public class Images {

    private String hidpi;
    private String normal;
    private String teaser;

    public Images() {
    }

    public Images(String hidpi, String normal, String teaser) {
        this.hidpi = hidpi;
        this.normal = normal;
        this.teaser = teaser;
    }

    public String getHidpi() {
        return hidpi;
    }

    public String getNormal() {
        return normal;
    }

    public String getTeaser() {
        return teaser;
    }

    public String best() {
        return hidpi != null ? hidpi : normal != null ? normal : teaser;
    }

}