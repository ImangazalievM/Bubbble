package com.imangazalievm.bubbble.domain.models;

public class Images {

    private String hidpi;
    private String normal;
    private String teaser;

    public Images() {
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
        return hidpi != null ? hidpi : normal;
    }

}