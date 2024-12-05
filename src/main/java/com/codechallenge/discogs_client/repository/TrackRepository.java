package com.codechallenge.discogs_client.repository;

import com.codechallenge.discogs_client.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {
    Optional<Track> getTrackByTitle(String title);
}
