package com.codechallenge.discogs_client.service;

import com.codechallenge.discogs_client.entity.Video;
import com.codechallenge.discogs_client.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;

    @Autowired
    public VideoServiceImpl(final VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @Override
    public List<Video> getAllVideos() {
        return this.videoRepository.findAll();
    }

    @Override
    public Optional<Video> getVideoById(final Long id) {
        return this.videoRepository.findById(id);
    }

    @Override
    public Video saveVideo(final Video video) {
        return this.videoRepository.save(video);
    }

    @Override
    public Optional<Video> getVideoByTitle(String title) {
        return this.videoRepository.getVideoByTitle(title);
    }
}
