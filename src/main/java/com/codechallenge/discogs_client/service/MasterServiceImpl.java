package com.codechallenge.discogs_client.service;

import com.codechallenge.discogs_client.entity.Master;
import com.codechallenge.discogs_client.repository.MasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MasterServiceImpl implements MasterService{

    private final MasterRepository masterRepository;

    @Autowired
    public MasterServiceImpl(final MasterRepository masterRepository) {
        this.masterRepository = masterRepository;
    }

    @Override
    public List<Master> getAllMasters() {
        return this.masterRepository.findAll();
    }

    @Override
    public Optional<Master> getMasterById(final Long id) {
        return this.masterRepository.findById(id);
    }

    @Override
    public Master saveMaster(final Master master) {
        return this.masterRepository.save(master);
    }

    @Override
    public Optional<Master> getMasterByTitle(String title) {
        return this.masterRepository.getMasterByTitle(title);
    }
}
