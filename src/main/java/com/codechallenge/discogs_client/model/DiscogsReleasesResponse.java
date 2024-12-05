package com.codechallenge.discogs_client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DiscogsReleasesResponse {

    @JsonProperty("releases")
    private List<DiscogsRelease> releases;

    public List<DiscogsRelease> getReleases() {
        return releases;
    }

    public void setReleases(List<DiscogsRelease> releases) {
        this.releases = releases;
    }
}
