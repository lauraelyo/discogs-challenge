package com.codechallenge.discogs_client.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uri;
    private String title;
    private String description;
    private int duration;
    private boolean embed;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Release> release = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Master> master = new ArrayList<>();

    public Video() {
    }

    public Video(final String uri,
                 final String title,
                 final String description, final int duration, final boolean embed) {
        this.uri = uri;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.embed = embed;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isEmbed() {
        return embed;
    }

    public void setEmbed(boolean embed) {
        this.embed = embed;
    }

    public List<Release> getRelease() {
        return release;
    }

    public void setRelease(List<Release> release) {
        this.release = release;
    }

    public List<Master> getMaster() {
        return master;
    }

    public void setMaster(List<Master> master) {
        this.master = master;
    }
}
