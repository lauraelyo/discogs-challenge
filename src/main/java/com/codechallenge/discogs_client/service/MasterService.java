package com.codechallenge.discogs_client.service;

import com.codechallenge.discogs_client.entity.Master;

import java.util.List;
import java.util.Optional;

public interface MasterService {
    /**
     * Get all masters List.
     * @return List of Masters.
     */
    List<Master> getAllMasters();

    /**
     * Get a master by id.
     *
     * @param id Master id.
     * @return the Master.
     */
    Optional<Master> getMasterById(Long id);

    /**
     * Save the master.
     *
     * @param master the master to save.
     * @return saved master.
     */
    Master saveMaster(Master master);

    /**
     * Get a master by title.
     *
     * @param title Master title.
     * @return the master.
     */
    Optional<Master> getMasterByTitle(String title);

}
