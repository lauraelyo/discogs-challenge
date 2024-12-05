package com.codechallenge.discogs_client.service;

import com.codechallenge.discogs_client.entity.Artist;
import com.codechallenge.discogs_client.entity.Release;
import com.codechallenge.discogs_client.exceptions.DiscogsApiException;
import com.codechallenge.discogs_client.model.ArtistComparisonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArtistComparisonServiceImpl implements ArtistComparisonService {

    private final ArtistService artistService;

    @Autowired
    public ArtistComparisonServiceImpl(final  ArtistService artistService) {
        this.artistService = artistService;
    }

    @Override
    public List<ArtistComparisonDTO> compareArtist(List<Long> artistsIds) {
        List<ArtistComparisonDTO> artistComparisonDTOS = new ArrayList<>();

        for(Long artisId : artistsIds) {
            final Artist artist = this.artistService.getArtistById(artisId).orElseThrow(()-> new DiscogsApiException("User not found"));
            final List<Release> releases = artist.getReleases();
            final int numReleases = releases.size();
            final int activeYears = calculateActiveYears(releases);
            final List<String> mostCommonGenres = commonGenres(releases);
            final List<String> mostCommonStyles = commonStyles(releases);
            final ArtistComparisonDTO artistComparisonDTO = new ArtistComparisonDTO();
            artistComparisonDTO.setArtistName(artist.getName());
            artistComparisonDTO.setActiveYears(activeYears);
            artistComparisonDTO.setReleases(numReleases);
            artistComparisonDTO.setMostCommonGenres(mostCommonGenres);
            artistComparisonDTO.setMostCommonStyles(mostCommonStyles);
            artistComparisonDTOS.add(artistComparisonDTO);
        }
        return artistComparisonDTOS;
    }

    /**
     * Calculate active years.
     *
     * @param releases release.
     * @return the active years.
     */
    private int calculateActiveYears(final List<Release> releases) {
        if (releases.isEmpty()) {
            return 0;
        }
        final int lastRelease = releases.stream().max(Comparator.comparingInt(Release::getYear)).orElseThrow().getYear();
        final int firstRelease = releases.stream().min(Comparator.comparingInt(Release::getYear)).orElseThrow().getYear();
        return  lastRelease - firstRelease;
    }

    /**
     * Get the top 3 for genres
     *
     * @param releases the artist releases
     * @return the top 3 genres
     */
    private List<String> commonGenres(List<Release> releases) {
        Map<String, Long> genreFrequency = new HashMap<>();

        releases.forEach(release ->
                genreFrequency
                        .putAll(release.getGenres()
                                .stream()
                                .collect(Collectors
                                        .groupingBy(e->e, Collectors.counting()))));

        return genreFrequency.entrySet()
                .stream()
                .sorted(Comparator.comparingLong(Map.Entry::getValue))
                .limit(3) //top 3
                .map(Map.Entry::getKey).toList();
    }

    /**
     * Get the top 3 for styles
     *
     * @param releases the artist releases
     * @return the top 3 styles
     */
    private List<String> commonStyles(List<Release> releases) {
        Map<String, Long> genreFrequency = new HashMap<>();

        releases.forEach(release ->
                genreFrequency
                        .putAll(release.getStyles()
                                .stream()
                                .collect(Collectors
                                        .groupingBy(e->e, Collectors.counting()))));

        return genreFrequency.entrySet()
                .stream()
                .sorted(Comparator.comparingLong(Map.Entry::getValue))
                .limit(3) //top 3
                .map(Map.Entry::getKey).toList();
    }
}
