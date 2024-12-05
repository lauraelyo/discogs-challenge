package com.codechallenge.discogs_client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DiscogsSearchResponse {

    @JsonProperty("results")
    private List<DiscogsArtist> results;

    public List<DiscogsArtist> getResults() {
        return results;
    }

    public void setResults(List<DiscogsArtist> results) {
        this.results = results;
    }
}
