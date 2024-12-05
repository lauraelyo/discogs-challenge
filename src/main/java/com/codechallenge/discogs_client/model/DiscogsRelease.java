package com.codechallenge.discogs_client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DiscogsRelease {

    private int id;
    private int year;
    private String title;
    @JsonProperty("resource_url")
    private String resourceUrl;
    private String uri;
    private String country;
    private String released;
    @JsonProperty("main_release")
    private Integer mainRelease;
    @JsonProperty("master_id")
    private Integer masterId;
    private List<String> genres;
    private List<String> styles;
    @JsonProperty("videos")
    private List<DiscogsVideo> videos;
    private List<DiscogsTrack> tracklist;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public Integer getMainRelease() {
        return mainRelease;
    }

    public void setMainRelease(Integer mainRelease) {
        this.mainRelease = mainRelease;
    }

    public Integer getMasterId() {
        return masterId;
    }

    public void setMasterId(Integer masterId) {
        this.masterId = masterId;
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
