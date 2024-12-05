package com.codechallenge.discogs_client.repository;

import com.codechallenge.discogs_client.entity.Master;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MasterRepository extends JpaRepository<Master, Long> {
    /**
     * Get master by title.
     * @param title title of the master.
     * @return The master.
     */
    public Optional<Master> getMasterByTitle(String title);
}
