package com.codechallenge.discogs_client.service;

import com.codechallenge.discogs_client.entity.Track;

import java.util.List;
import java.util.Optional;

public interface TrackService {
    /**
     * Get all the tracks.
     * @return Track list.
     */
    List<Track> getAllTracks();

    /**
     * Get the track by id.
     *
     * @param id track id.
     * @return The track.
     */
    Optional<Track> getTrackById(Long id);

    /**
     * Save the track.
     * @param track the track to save.
     * @return the saved track.
     */
    Track saveTrack(Track track);

    /**
     * Get track by title.
     *
     * @param title the title.
     * @return the Track.
     */
    Optional<Track> getTrackByTitle(String title);

}
