package com.codechallenge.discogs_client.service;

import com.codechallenge.discogs_client.exceptions.DiscogsApiException;
import com.codechallenge.discogs_client.model.*;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DiscogsApiClientImpl implements DiscogsApiClient {

    private static final String DISCOGS_API_URL = "https://api.discogs.com/";

    private static final Logger log = LoggerFactory.getLogger(DiscogsApiClientImpl.class);

    private ObjectMapper objectMapper;

    private RestTemplate restTemplate;

    @Value("${discogs.apiToken}")
    private String apiToken;

    public DiscogsApiClientImpl() {
        this.objectMapper = new ObjectMapper();
        this.restTemplate = new RestTemplate();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public List<ArtistDTO> searchArtist(final String artistName) {
        final String url = DISCOGS_API_URL + "database/search?q=" + artistName + "&type=artist";
        try {
            DiscogsSearchResponse searchResult = makeApiCall(url, HttpMethod.GET, DiscogsSearchResponse.class);
            if (Objects.requireNonNull(searchResult).getResults() != null) {
                return searchResult.getResults().stream()
                        .map(discogsArtist ->
                                new ArtistDTO(discogsArtist.getTitle(),
                                        "/api/artist/save?id=" + discogsArtist.getId() + "&page=1&per_page=30"))
                        .collect(Collectors.toList());
            }

        } catch (Exception e) {
            log.error("Error fetching artists by  name {}", artistName, e);
            throw new DiscogsApiException("Error fetching artist by name {}" + artistName, e);
        }

        return null;
    }

    @Override
    public DiscogsArtist getArtistById(final int id) {
        String url = DISCOGS_API_URL + "artists/" + id;
        try {
            return makeApiCall(url, HttpMethod.GET, DiscogsArtist.class);
        } catch (Exception e) {
            log.error("Error fetching artist by id {}", id, e);
            throw new DiscogsApiException("Error fetching artist by id {}" + id, e);
        }
    }

    @Override
    public DiscogsReleasesResponse getReleasesForArtist(final int artistId, final int page, final int perPage) {
        String url = DISCOGS_API_URL + "artists/" + artistId + "/releases?page=" + page + "&per_page=" + perPage;
        try {
            return makeApiCall(url, HttpMethod.GET, DiscogsReleasesResponse.class);
        } catch (Exception e) {
            log.error("Error fetching release by artist id {}", artistId, e);
            throw new DiscogsApiException("Error fetching release by artist id {}" + artistId, e);
        }
    }

    @Override
    public DiscogsRelease getRelease(final int releaseId) {
        String url = DISCOGS_API_URL + "releases/" + releaseId;
        try {
            return makeApiCall(url, HttpMethod.GET, DiscogsRelease.class);
        } catch (Exception e) {
            log.error("Error fetching release by id {}", releaseId, e);
            throw new DiscogsApiException("Error fetching release by id {}" + releaseId, e);
        }
    }

    @Override
    public DiscogsMaster getMaster(final int masterId) {
        String url = DISCOGS_API_URL + "masters/" + masterId;
        try {
            return makeApiCall(url, HttpMethod.GET, DiscogsMaster.class);
        } catch (Exception e) {
            log.error("Error fetching masters by id {}", masterId, e);
            throw new DiscogsApiException("Error fetching masters by id {}" + masterId, e);
        }
    }

    /**
     * Method to make the api call.
     *
     * @param url The url to call.
     * @param method Method of the call.
     * @param responseType Type response.
     * @return The Entity response.
     * @param <T> Generic for Response type
     */
    private <T> T makeApiCall(final String url, final HttpMethod method, final Class<T> responseType) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Discogs token=" + apiToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(url, method, entity, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                String responseBody = response.getBody();

                if (responseBody == null || responseBody.trim().isEmpty()) {
                    throw new DiscogsApiException("Empty response");
                }
                if (responseBody.contains("not found")) {
                    throw new DiscogsApiException("Resource in Discogs not found");
                }
                try {
                    return objectMapper.readValue(responseBody, responseType);
                } catch (Exception e) {
                    throw new DiscogsApiException("Json cannot parse to type " + responseType, e);
                }
            } else {
                throw new DiscogsApiException("Error in response from API status code {}" + response.getStatusCode());
            }
        } catch (RuntimeException e) {
            log.error("Error occurred making API call to url {}", url, e);
            throw new DiscogsApiException("Error occurred making API call to " + url, e);
        }
    }
}
