package com.codechallenge.discogs_client.service;

import com.codechallenge.discogs_client.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiscogsApiClientImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    @Spy
    private DiscogsApiClientImpl discogsApiClient = new DiscogsApiClientImpl();


    @Test
    void searchArtist() {
        String artistName = "Nirvana";
        String url = "https://api.discogs.com/database/search?q=" + artistName + "&type=artist";

        DiscogsSearchResponse mockResponse = new DiscogsSearchResponse();
        DiscogsArtist result = new DiscogsArtist();
        result.setId(1);
        result.setTitle("Nirvana");
        mockResponse.setResults(List.of(result));

        when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class)))
                .thenReturn(new ResponseEntity<>("{\"results\":[{\"id\":1,\"title\":\"Nirvana\"}]}", HttpStatus.OK));

        List<ArtistDTO> artists = discogsApiClient.searchArtist(artistName);

        assertNotNull(artists);
        assertEquals(1, artists.size());
        assertEquals("Nirvana", artists.get(0).getTitle());
        verify(restTemplate, times(1)).exchange(eq(url), eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class));
    }

    @Test
    void getArtistById() {
        int artistId = 123;
        String url = "https://api.discogs.com/artists/" + artistId;

        DiscogsArtist mockArtist = new DiscogsArtist();
        mockArtist.setId(artistId);
        mockArtist.setName("Test Artist");

        when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class)))
                .thenReturn(new ResponseEntity<>("{\"id\":123,\"name\":\"Nirvana\"}", HttpStatus.OK));

        DiscogsArtist artist = discogsApiClient.getArtistById(artistId);

        assertNotNull(artist);
        assertEquals(artistId, artist.getId());
        assertEquals("Nirvana", artist.getName());
    }

    @Test
    void getReleasesForArtist() {
        int artistId = 123;
        int page = 1;
        int perPage = 10;
        String url = "https://api.discogs.com/artists/" + artistId + "/releases?page=" + page + "&per_page=" + perPage;

        when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class)))
                .thenReturn(new ResponseEntity<>("{\"pagination\":{},\"releases\":[{\"id\": 123}]}", HttpStatus.OK));

        DiscogsReleasesResponse releasesResponse = discogsApiClient.getReleasesForArtist(artistId, page, perPage);

        assertNotNull(releasesResponse);
        assertEquals(1, releasesResponse.getReleases().size());
        assertEquals(123, releasesResponse.getReleases().get(0).getId());
    }

    @Test
    void getRelease() {
        int releaseId = 123;
        String url = "https://api.discogs.com/releases/" + releaseId;

        DiscogsRelease mockRelease = new DiscogsRelease();
        mockRelease.setId(releaseId);

        when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class)))
                .thenReturn(new ResponseEntity<>("{\"id\":123}", HttpStatus.OK));

        DiscogsRelease release = discogsApiClient.getRelease(releaseId);
        assertNotNull(release);
        assertEquals(releaseId, release.getId());
    }

    @Test
    void getMaster() {
        int masterId = 456;
        String url = "https://api.discogs.com/masters/" + masterId;

        DiscogsMaster mockMaster = new DiscogsMaster();
        mockMaster.setTitle("Some name");

        when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class)))
                .thenReturn(new ResponseEntity<>("{\"title\":\"Some name\"}", HttpStatus.OK));

        DiscogsMaster master = discogsApiClient.getMaster(masterId);
        assertNotNull(master);
        assertEquals("Some name", master.getTitle());
    }
}
