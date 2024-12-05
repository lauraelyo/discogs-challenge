package com.codechallenge.discogs_client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DiscogsTrack {

    private String position;
    @JsonProperty("type_")
    private String type;
    private String duration;
    private String title;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}