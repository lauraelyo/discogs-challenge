package com.codechallenge.discogs_client.controller;

import com.codechallenge.discogs_client.entity.Artist;
import com.codechallenge.discogs_client.model.ArtistComparisonDTO;
import com.codechallenge.discogs_client.model.ArtistDTO;
import com.codechallenge.discogs_client.service.ArtistComparisonService;
import com.codechallenge.discogs_client.service.DiscogsApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artist")
public class DiscogsController {

    private final DiscogsApiService discogsApiService;

    private final ArtistComparisonService artistComparisonService;

    @Autowired
    public DiscogsController(final DiscogsApiService discogsApiService,
                             final ArtistComparisonService artistComparisonService) {
        this.discogsApiService = discogsApiService;
        this.artistComparisonService = artistComparisonService;
    }
    @GetMapping("/search")
    public List<ArtistDTO> searchArtistAndList(final @RequestParam String artistName) {
        return discogsApiService.searchArtistAndList(artistName);
    }

    @PostMapping("/save")
    public HttpStatus getArtistAndSave(final @RequestParam int id,
                                       final @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                       final @RequestParam(name = "per_page", required = false, defaultValue = "20") int perPage) {
        discogsApiService.saveArtistAndInfo(id, page, perPage);
        return HttpStatus.OK;
    }

    @GetMapping("/details")
    public Artist getArtistWithRelatedInfo(final @RequestParam Long artistId) {
        return discogsApiService.findByIdWithRelatedInfo(artistId);
    }

    @PostMapping("/compare")
    public List<ArtistComparisonDTO> compareArtist(@RequestBody List<Long> artistIds) {
        return artistComparisonService.compareArtist(artistIds);
    }
}
