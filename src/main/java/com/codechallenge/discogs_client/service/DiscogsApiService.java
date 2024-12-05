package com.codechallenge.discogs_client.service;


import com.codechallenge.discogs_client.entity.Artist;
import com.codechallenge.discogs_client.model.ArtistDTO;

import java.util.List;

public interface DiscogsApiService {
    /**
     * Search and artist in the api and save the related info.
     *
     * @param artistName artist name.
     * @return List of Artists.
     */
    List<ArtistDTO> searchArtistAndList(final String artistName);

    /**
     * Save the artist and the info related.
     *
     * @param id   The artist id.
     * @param page the page to consult.
     * @param perPage releases by page.
     */
    void saveArtistAndInfo(final int id, final int page, final int perPage);

    /**
     * Find an artist by id and related info.
     *
     * @param artistId Artist id.
     * @return the artist.
     */
    Artist findByIdWithRelatedInfo(final Long artistId);
}
