package com.codechallenge.discogs_client.service;

import com.codechallenge.discogs_client.entity.*;
import com.codechallenge.discogs_client.exceptions.DiscogsApiException;
import com.codechallenge.discogs_client.model.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiscogsApiServiceImpl implements DiscogsApiService {

    private static final Logger log = LoggerFactory.getLogger(DiscogsApiServiceImpl.class);


    private final DiscogsApiClient discogsApiClient;
    private final ArtistService artistService;
    private final ReleaseService releaseService;
    private final MasterService masterService;
    private final TrackService trackService;
    private final VideoService videoService;

    @Autowired
    public DiscogsApiServiceImpl(final DiscogsApiClient discogsApiClient, final ArtistService artistService,
                                 final ReleaseService releaseService, final MasterService masterService,
                                 final TrackService trackService, final VideoService videoService) {
        this.discogsApiClient = discogsApiClient;
        this.artistService = artistService;
        this.releaseService = releaseService;
        this.masterService = masterService;
        this.trackService = trackService;
        this.videoService = videoService;
    }

    @Override
    public List<ArtistDTO> searchArtistAndList(final String artistName) {
        return discogsApiClient.searchArtist(artistName);
    }

    @Override
    public void saveArtistAndInfo(final int id, int page, int perPage) {
        try {
            final DiscogsArtist discogsArtist = discogsApiClient.getArtistById(id);

            final Artist artist = getOrCreateArtist(discogsArtist);
            saveInfoRelated(artist, discogsArtist.getId(), page, perPage);
        } catch (Exception e) {
           log.error("Error occurs saving artist and info: {}", e.getMessage());
           throw new DiscogsApiException("Failed to save Artist and info related", e);
        }
    }

    @Override
    public Artist findByIdWithRelatedInfo(Long artistId) {
        Optional<Artist> artist = this.artistService.findByIdWithRelatedInfo(artistId);
        return artist.orElseThrow(() -> new DiscogsApiException("Artist not foud with id: " + artistId));
    }

    /**
     * Get or create an artist.
     *
     * @param discogsArtist Artist from discogs.
     * @return Artist.
     */
    private Artist getOrCreateArtist(DiscogsArtist discogsArtist) {
        final Optional<Artist> artistDb = this.artistService.getArtistByName(discogsArtist.getName());
        return artistDb.orElseGet(() -> saveArtist(discogsArtist));
    }

    /**
     * Save the info related to the artist.
     *
     * @param artist The artist to save.
     * @param artistId the Api id.
     * @param page page to consult.
     * @param perPage releases by page.
     */
    private void saveInfoRelated(final Artist artist, final int artistId, int page, int perPage) {
        try {
            // make the call for releases to validate the data is updated in case of new releases
            DiscogsReleasesResponse releases = this.discogsApiClient.getReleasesForArtist(artistId, page, perPage);

            if (releases != null && releases.getReleases() != null) {
                releases.getReleases().forEach(release -> discogsReleaseToRelease(release, artist));
            } else {
                log.warn("No releases found for artist {}", artistId);
            }
        } catch (Exception e) {
            log.error("Error occurs saving info related to the artist {}", e.getMessage());
            throw new DiscogsApiException("Error occurs saving info related to the artist", e);
        }
    }

    /**
     * Save the info related to the Release.
     *
     * @param discogsRelease Discogs Release from api.
     * @param artist the Artist.
     */
    @Transactional
    private void discogsReleaseToRelease(final DiscogsRelease discogsRelease, Artist artist) {
        final int releaseId = discogsRelease.getMainRelease() != null ? discogsRelease.getMainRelease() : discogsRelease.getId();
        // If the release doesn't exist proceed to save the release.
        if (this.releaseService.getReleaseByTitle(discogsRelease.getTitle()).isEmpty()) {
            final DiscogsRelease discogsRelease1 = this.discogsApiClient.getRelease(releaseId);
            Master master = discogsRelease1.getMasterId() != null ? saveMasterIfNotExist(discogsRelease1) : null;

            Release release = getOrCreateRelease(discogsRelease1, master);
            addReleaseToArtist(release, artist);
        }
    }

    /**
     * Save a master if not exist.
     *
     * @param discogsRelease the release from api discogs.
     * @return the master
     */
    private Master saveMasterIfNotExist(DiscogsRelease discogsRelease) {
        final DiscogsMaster discogsMaster = this.discogsApiClient.getMaster(discogsRelease.getMasterId());
        Optional<Master> masterDb = this.masterService.getMasterByTitle(discogsMaster.getTitle());
        return masterDb.orElseGet(() -> saveMaster(discogsMaster));
    }

    /**
     * Get or create a Release.
     *
     * @param discogsRelease The release from the api discogs.
     * @param master The master.
     * @return the Release.
     */
    private Release getOrCreateRelease(DiscogsRelease discogsRelease, Master master) {
        final Optional<Release> releaseDb = this.releaseService.getReleaseByTitle(discogsRelease.getTitle());
        return releaseDb.orElseGet(() -> saveRelease(discogsRelease, master));
    }

    /**
     * Add an artist to the release.
     *
     * @param release the release.
     * @param artist the artist.
     */
    private void addReleaseToArtist(Release release, Artist artist) {
        if (artist.getReleases().isEmpty() || !artist.getReleases().contains(release)) {
            artist.getReleases().add(release);
            this.artistService.saveArtist(artist);
        }
    }

    /**
     * Save a release.
     *
     * @param discogsRelease the release from discogs.
     * @param master the master related to release.
     * @return the Release.
     */
    private Release saveRelease(DiscogsRelease discogsRelease, Master master) {
        final Release release = getRelease(discogsRelease, master);
        if (discogsRelease.getVideos() != null && !discogsRelease.getVideos().isEmpty()) {
            release.getVideos().addAll(discogsRelease.getVideos().stream()
                    .map(this::getOrCreateVideo)
                    .toList());
        }
        if (discogsRelease.getTracklist() != null && !discogsRelease.getTracklist().isEmpty()) {
            release.getTracklist().addAll(discogsRelease.getTracklist().stream()
                    .map(this::getOrCreateTrack)
                    .toList());
        }
        return this.releaseService.saveRelease(release);
    }

    private static Release getRelease(DiscogsRelease discogsRelease, Master master) {
        final Release release = new Release();
        release.setReleased(discogsRelease.getReleased());
        release.setTitle(discogsRelease.getTitle());
        release.setCountry(discogsRelease.getCountry());
        release.setResourceUrl(discogsRelease.getResourceUrl());
        release.setGenres(discogsRelease.getGenres());
        release.setStyles(discogsRelease.getStyles());
        release.setUri(discogsRelease.getUri());
        if (master != null) {
            release.setMaster(master);
        }
        release.setYear(discogsRelease.getYear());
        return release;
    }

    /**
     * Get or create a video.
     *
     * @param discogsVideo video from api discogs.
     * @return the Video.
     */
    private Video getOrCreateVideo(DiscogsVideo discogsVideo) {
        Optional<Video> video = this.videoService.getVideoByTitle(discogsVideo.getTitle());
        return video.orElseGet(() -> saveVideo(discogsVideo));

    }

    /**
     * Save a video.
     *
     * @param discogsVideo video from api discogs.
     * @return the Video.
     */
    private Video saveVideo(DiscogsVideo discogsVideo){
        final Video video = new Video(discogsVideo.getUri(),
                discogsVideo.getTitle(),
                discogsVideo.getDescription(),
                discogsVideo.getDuration(),
                discogsVideo.getEmbed());
        return this.videoService.saveVideo(video);
    }

    private Track getOrCreateTrack(DiscogsTrack discogsTrack) {
        Optional<Track> track = this.trackService.getTrackByTitle(discogsTrack.getTitle());
        return track.orElseGet(() -> saveTrack(discogsTrack));

    }

    /**
     * Save a Track.
     *
     * @param discogsTrack Track from api.
     * @return Track.
     */
    private Track saveTrack(DiscogsTrack discogsTrack) {
        final Track track = new Track(discogsTrack.getPosition(),
                discogsTrack.getType(), discogsTrack.getDuration(), discogsTrack.getTitle());
        return this.trackService.saveTrack(track);
    }

    /**
     * Save a master.
     *
     * @param discogsMaster Master from api discogs.
     * @return the Master.
     */
    private Master saveMaster(final DiscogsMaster discogsMaster) {
        final Master master = new Master();
        master.setStyles(discogsMaster.getStyles());
        master.setYear(discogsMaster.getYear());
        master.setTitle(discogsMaster.getTitle());
        master.setGenres(discogsMaster.getGenres());
        master.setResourceUrl(discogsMaster.getResourceUrl());
        if (discogsMaster.getVideos() != null && !discogsMaster.getTracklist().isEmpty()) {
            master.getVideos().addAll(discogsMaster.getVideos().stream()
                    .map(this::getOrCreateVideo)
                    .toList());
        }
        if (discogsMaster.getTracklist() != null && !discogsMaster.getTracklist().isEmpty()) {
            master.getTracklist().addAll(discogsMaster.getTracklist().stream()
                    .map(this::getOrCreateTrack)
                    .toList());
        }
        return this.masterService.saveMaster(master);
    }

    /**
     * Save an artist.
     *
     * @param discogsArtist Artist from api discogs.
     * @return The Artist.
     */
    private Artist saveArtist(final DiscogsArtist discogsArtist) {
        final Artist artist = new Artist();
        artist.setName(discogsArtist.getName());
        artist.setProfile(discogsArtist.getProfile());
        artist.setResourceUrl(discogsArtist.getResourceUrl());
        artist.setRealName(discogsArtist.getRealName());
        artist.setNameVariations(discogsArtist.getNameVariations());
        this.artistService.saveArtist(artist);
        return artist;
    }
}
