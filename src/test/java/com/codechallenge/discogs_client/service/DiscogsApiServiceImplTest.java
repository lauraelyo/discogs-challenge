package com.codechallenge.discogs_client.service;

import com.codechallenge.discogs_client.entity.Artist;
import com.codechallenge.discogs_client.model.ArtistDTO;
import com.codechallenge.discogs_client.model.DiscogsArtist;
import com.codechallenge.discogs_client.model.DiscogsReleasesResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiscogsApiServiceImplTest {

    @Mock
    private DiscogsApiClient discogsApiClient;

    @Mock
    private ArtistService artistService;

    @Mock
    private ReleaseService releaseService;

    @Mock
    private MasterService masterService;

    @Mock
    private TrackService trackService;

    @Mock
    private VideoService videoService;

    @InjectMocks
    private DiscogsApiServiceImpl discogsApiService;

    @Test
    void searchArtistAndList() {
        String artistName = "Nirvana";
        ArtistDTO mockArtistDTO = new ArtistDTO("Nirvana", "/api/artist/save?id=1");
        when(discogsApiClient.searchArtist(eq(artistName))).thenReturn(List.of(mockArtistDTO));

        List<ArtistDTO> artistDTOS = discogsApiService.searchArtistAndList(artistName);

        assertNotNull(artistDTOS);
        assertEquals(1, artistDTOS.size());
        assertEquals("Nirvana", artistDTOS.get(0).getTitle());
    }

    @Test
    void saveArtistAndInfo() {
        int artistId = 123;
        int page = 1;
        int perPage = 10;

        DiscogsArtist mockDiscogsArtist = new DiscogsArtist();
        mockDiscogsArtist.setId(artistId);
        mockDiscogsArtist.setName("Nirvana");

        Artist mockArtist = new Artist();
        mockArtist.setProfile("Some profile");
        mockArtist.setName("Nirvana");

        DiscogsReleasesResponse mockReleasesResponse = new DiscogsReleasesResponse();

        when(discogsApiClient.getArtistById(eq(artistId))).thenReturn(mockDiscogsArtist);
        when(artistService.getArtistByName(eq("Nirvana"))).thenReturn(Optional.of(mockArtist));
        when(discogsApiClient.getReleasesForArtist(eq(artistId), eq(page), eq(perPage)))
                .thenReturn(mockReleasesResponse);

        assertDoesNotThrow(() -> discogsApiService.saveArtistAndInfo(artistId, page, perPage));

        assertEquals("Nirvana", discogsApiClient.getArtistById(artistId).getName());
    }

    @Test
    void findByIdWithRelatedInfo() {
        Long artistId = 1L;

        Artist mockArtist = new Artist();
        mockArtist.setRealName("some name");
        mockArtist.setName("Adele");

        when(artistService.findByIdWithRelatedInfo(eq(artistId))).thenReturn(Optional.of(mockArtist));

        Artist result = discogsApiService.findByIdWithRelatedInfo(artistId);

        assertNotNull(result);
        assertEquals("some name", result.getRealName());
        assertEquals("Adele", result.getName());
    }
}