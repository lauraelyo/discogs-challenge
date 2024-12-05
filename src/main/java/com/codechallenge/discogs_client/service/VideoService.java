package com.codechallenge.discogs_client.service;

import com.codechallenge.discogs_client.entity.Video;

import java.util.List;
import java.util.Optional;

public interface VideoService {
    /**
     * Get the videos list.
     * @return The list of videos.
     */
    List<Video> getAllVideos();

    /**
     * Get the video by id.
     *
     * @param id video id.
     * @return The video.
     */
    Optional<Video> getVideoById(Long id);

    /**
     * Save a Video.
     * @param video the video to save.
     * @return the saved video.
     */
    Video saveVideo(Video video);

    /**
     * Get a video by title.
     *
     * @param title Video title.
     * @return Video.
     */
    Optional<Video> getVideoByTitle(String title);
}
