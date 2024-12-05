package com.codechallenge.discogs_client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DiscogsMaster {

    private int year;
    @JsonProperty("resource_url")
    private String resourceUrl;
    private String title;
    private List<String> genres;
    private List<String> styles;
    @JsonProperty("videos")
    private List<DiscogsVideo> videos;
    private List<DiscogsTrack> tracklist;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getStyles() {
        return styles;
    }

    public void setStyles(List<String> styles) {
        this.styles = styles;
    }

    public List<DiscogsVideo> getVideos() {
        return videos;
    }

    public void setVideos(List<DiscogsVideo> videos) {
        this.videos = videos;
    }

    public List<DiscogsTrack> getTracklist() {
        return tracklist;
    }

    public void setTracklist(List<DiscogsTrack> tracklist) {
        this.tracklist = tracklist;
    }
}
