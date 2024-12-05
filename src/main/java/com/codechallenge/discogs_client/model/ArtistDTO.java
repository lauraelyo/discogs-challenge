package com.codechallenge.discogs_client.model;


public class ArtistDTO {

    private String title;
    private String uri;

    public ArtistDTO(final String title, final String s) {
        this.title = title;
        this.uri = s;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
