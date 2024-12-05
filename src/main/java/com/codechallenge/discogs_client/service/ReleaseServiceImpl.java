package com.codechallenge.discogs_client.service;

import com.codechallenge.discogs_client.entity.Release;
import com.codechallenge.discogs_client.repository.ReleaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReleaseServiceImpl implements ReleaseService{

    private final ReleaseRepository releaseRepository;

    @Autowired
    public ReleaseServiceImpl(ReleaseRepository releaseRepository) {
        this.releaseRepository = releaseRepository;
    }

    @Override
    public List<Release> getAllReleases() {
        return this.releaseRepository.findAll();
    }

    @Override
    public Optional<Release> getReleaseById(final Long id) {
        return this.releaseRepository.findById(id);
    }

    @Override
    public Release saveRelease(final Release release) {
        return this.releaseRepository.save(release);
    }

    @Override
    public Optional<Release> getReleaseByTitle(final String title) {
        return this.releaseRepository.getReleaseByTitle(title);
    }
}
