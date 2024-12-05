package com.codechallenge.discogs_client.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String realName;

    @Column(columnDefinition="TEXT")
    private String profile;

    private String resourceUrl;

    @ElementCollection
    private List<String> nameVariations;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Release> releases = new ArrayList<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public List<String> getNameVariations() {
        return nameVariations;
    }

    public void setNameVariations(List<String> nameVariations) {
        this.nameVariations = nameVariations;
    }

    public List<Release> getReleases() {
        return releases;
    }

    public void setReleases(List<Release> releases) {
        this.releases = releases;
    }
}
