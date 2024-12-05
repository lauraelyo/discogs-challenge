package com.codechallenge.discogs_client.service;

import com.codechallenge.discogs_client.entity.Artist;
import com.codechallenge.discogs_client.repository.ArtistRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistServiceImpl(final ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override
    public List<Artist> getAllArtists() {
        return this.artistRepository.findAll();
    }

    @Override
    public Optional<Artist> getArtistById(final Long id) {
        return this.artistRepository.findById(id);
    }

    @Override
    public void saveArtist(final Artist artist) {
        this.artistRepository.save(artist);
    }

    @Override
    public Optional<Artist> getArtistByName(String name) {
        return this.artistRepository.findArtistByName(name);
    }

    @Override
    @Transactional
    public Optional<Artist> findByIdWithRelatedInfo(Long artistId) {
        return this.artistRepository.findByIdWithRelatedInfo(artistId);
    }
}
