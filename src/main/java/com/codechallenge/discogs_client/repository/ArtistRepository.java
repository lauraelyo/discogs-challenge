package com.codechallenge.discogs_client.repository;

import com.codechallenge.discogs_client.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    /**
     * Find Artist by Name.
     *
     * @param name Artist name.
     * @return The Artist.
     */
    Optional<Artist> findArtistByName(String name);

    @Query("SELECT DISTINCT a FROM Artist a " +
            "LEFT JOIN FETCH a.releases r " +
            "LEFT JOIN FETCH r.master m " +
            "WHERE a.id = :artistId " +
            "ORDER BY r.year DESC")
    Optional<Artist> findByIdWithRelatedInfo(@Param("artistId") Long artistId);
}
