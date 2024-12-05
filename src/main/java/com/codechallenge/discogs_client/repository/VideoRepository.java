package com.codechallenge.discogs_client.repository;

import com.codechallenge.discogs_client.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    Optional<Video> getVideoByTitle(String title);

}
