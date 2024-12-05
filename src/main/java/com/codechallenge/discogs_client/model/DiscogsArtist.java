package com.codechallenge.discogs_client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DiscogsArtist {

    private String name;
    private String title;
    @JsonProperty("resource_url")
    private String resourceUrl;
    @JsonProperty("real_name")
    private String realName;
    private String profile;
    @JsonProperty("name_variations")
    private List<String> nameVariations;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public List<String> getNameVariations() {
        return nameVariations;
    }

    public void setNameVariations(List<String> nameVariations) {
        this.nameVariations = nameVariations;
    }
}
