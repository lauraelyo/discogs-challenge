package com.codechallenge.discogs_client.service;

import java.util.List;
import java.util.Optional;

import com.codechallenge.discogs_client.entity.Release;

public interface ReleaseService {
    /**
     * Get all the releases
     * @return Release List
     */
    List<Release> getAllReleases();

    /**
     * Get the release by id.
     *
     * @param id release id.
     * @return a Release.
     */
    Optional<Release> getReleaseById(Long id);

    /**
     * Save the release.
     * @param release Release to save.
     * @return the saved Release.
     */
    Release saveRelease(Release release);

    /**
     * Retrieve the Release by title.
     * @param title title release.
     * @return the Release.
     */
    Optional<Release> getReleaseByTitle(String title);
}
