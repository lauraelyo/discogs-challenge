package com.codechallenge.discogs_client.service;

import com.codechallenge.discogs_client.entity.Track;
import com.codechallenge.discogs_client.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrackServiceImpl implements TrackService {

    private final TrackRepository trackRepository;

    @Autowired
    public TrackServiceImpl(final TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Override
    public List<Track> getAllTracks() {
        return this.trackRepository.findAll();
    }

    @Override
    public Optional<Track> getTrackById(final Long id) {
        return this.trackRepository.findById(id);
    }

    @Override
    public Track saveTrack(Track track) {
        return this.trackRepository.save(track);
    }

    @Override
    public Optional<Track> getTrackByTitle(String title) {
        return this.trackRepository.getTrackByTitle(title);
    }
}
