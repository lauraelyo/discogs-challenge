package com.codechallenge.discogs_client.repository;

import com.codechallenge.discogs_client.entity.Release;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReleaseRepository extends JpaRepository<Release, Long> {
    /**
     * Get the release by title.
     * @param title Title release.
     * @return the Release if existed.
     */
    Optional<Release> getReleaseByTitle(String title);
}
