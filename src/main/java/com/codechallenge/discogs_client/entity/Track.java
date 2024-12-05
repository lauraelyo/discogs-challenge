package com.codechallenge.discogs_client.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String position;
    private String type;
    private String duration;
    private String title;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Release> release = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Master> masters = new ArrayList<>();

    public Track() {
    }

    public Track(final String position, final String type, final String duration, final String title) {
        this.position = position;
        this.type = type;
        this.duration = duration;
        this.title = title;
    }

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

    public List<Release> getRelease() {
        return release;
    }

    public void setRelease(List<Release> release) {
        this.release = release;
    }

    public List<Master> getMasters() {
        return masters;
    }

    public void setMasters(List<Master> masters) {
        this.masters = masters;
    }
}
