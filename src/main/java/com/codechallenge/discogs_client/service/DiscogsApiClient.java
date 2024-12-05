package com.codechallenge.discogs_client.service;

import com.codechallenge.discogs_client.model.*;

import java.util.List;

public interface DiscogsApiClient {
    /**
     * Search an artist in the api.
     * @param artistName artist name.
     * @return SearchResponse results.
     */
    List<ArtistDTO> searchArtist(final String artistName);

    /**
     * Get an artist by id.
     * @param id the artist id.
     * @return the Artist.
     */
    DiscogsArtist getArtistById(final int id);

    /**
     * Get artists Releases.
     *
     * @param artistId artist id.
     * @param page page to consult.
     * @param perPage releases by page.
     * @return ReleasesResponse.
     */
    DiscogsReleasesResponse getReleasesForArtist(final int artistId, int page, int perPage);

    /**
     * Get Main Release
     * @param releaseId the main release
     * @return DiscogsRelease
     */
    DiscogsRelease getRelease(final int releaseId);

    /**
     * Get the master.
     * @param masterId the master id.
     * @return The master.
     */
    DiscogsMaster getMaster(final int masterId);
}
