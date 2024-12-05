package com.codechallenge.discogs_client.model;

import java.util.List;

public class ArtistComparisonDTO {

    private String artistName;

    private int releases;

    private int activeYears;

    private List<String> mostCommonGenres;

    private List<String> mostCommonStyles;

    public List<String> getMostCommonStyles() {
        return mostCommonStyles;
    }

    public void setMostCommonStyles(List<String> mostCommonStyles) {
        this.mostCommonStyles = mostCommonStyles;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public int getReleases() {
        return releases;
    }

    public void setReleases(int releases) {
        this.releases = releases;
    }

    public int getActiveYears() {
        return activeYears;
    }

    public void setActiveYears(int activeYears) {
        this.activeYears = activeYears;
    }

    public List<String> getMostCommonGenres() {
        return mostCommonGenres;
    }

    public void setMostCommonGenres(List<String> mostCommonGenres) {
        this.mostCommonGenres = mostCommonGenres;
    }
}
