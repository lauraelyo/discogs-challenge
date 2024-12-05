package com.codechallenge.discogs_client.service;

import com.codechallenge.discogs_client.model.ArtistComparisonDTO;

import java.util.List;

public interface ArtistComparisonService {
    /**
     * Compare artists.
     *
     * @param artistsIds ids to compare.
     * @return ArtistComparisonDTO.
     */
    List<ArtistComparisonDTO> compareArtist(List<Long> artistsIds);

}
