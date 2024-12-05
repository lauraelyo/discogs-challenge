package com.codechallenge.discogs_client.service;

import com.codechallenge.discogs_client.entity.Artist;

import java.util.List;
import java.util.Optional;

public interface ArtistService {
    /**
     * Get all the artist.
     * @return List of artists.
     */
    List<Artist> getAllArtists();

    /**
     * Get an artist by id.
     * @return the artist.
     */
    Optional<Artist> getArtistById(Long id);

    /**
     * Save an artist.
     *
     * @param artist the Artist to save
     */
    void saveArtist(Artist artist);

    /**
     * Retrieve an artist by name.
     *
     * @param name Artist Name.
     * @return the artist.
     */
    Optional<Artist> getArtistByName(String name);

    /**
     * Retrieve an artist by id and all the information related.
     *
     * @param artistId artist id.
     * @return The artist.
     */
    Optional<Artist> findByIdWithRelatedInfo(Long artistId);

}
