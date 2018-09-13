package com.bytatech.ayoos.patient.service.impl;

import com.bytatech.ayoos.patient.service.MedicalRecordsService;
import com.bytatech.ayoos.patient.domain.MedicalRecords;
import com.bytatech.ayoos.patient.repository.MedicalRecordsRepository;
import com.bytatech.ayoos.patient.repository.search.MedicalRecordsSearchRepository;
import com.bytatech.ayoos.patient.service.dto.MedicalRecordsDTO;
import com.bytatech.ayoos.patient.service.mapper.MedicalRecordsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing MedicalRecords.
 */
@Service
@Transactional
public class MedicalRecordsServiceImpl implements MedicalRecordsService {

    private final Logger log = LoggerFactory.getLogger(MedicalRecordsServiceImpl.class);

    private final MedicalRecordsRepository medicalRecordsRepository;

    private final MedicalRecordsMapper medicalRecordsMapper;

    private final MedicalRecordsSearchRepository medicalRecordsSearchRepository;

    public MedicalRecordsServiceImpl(MedicalRecordsRepository medicalRecordsRepository, MedicalRecordsMapper medicalRecordsMapper, MedicalRecordsSearchRepository medicalRecordsSearchRepository) {
        this.medicalRecordsRepository = medicalRecordsRepository;
        this.medicalRecordsMapper = medicalRecordsMapper;
        this.medicalRecordsSearchRepository = medicalRecordsSearchRepository;
    }

    /**
     * Save a medicalRecords.
     *
     * @param medicalRecordsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MedicalRecordsDTO save(MedicalRecordsDTO medicalRecordsDTO) {
        log.debug("Request to save MedicalRecords : {}", medicalRecordsDTO);
        MedicalRecords medicalRecords = medicalRecordsMapper.toEntity(medicalRecordsDTO);
        medicalRecords = medicalRecordsRepository.save(medicalRecords);
        MedicalRecordsDTO result = medicalRecordsMapper.toDto(medicalRecords);
        medicalRecordsSearchRepository.save(medicalRecords);
        return result;
    }

    /**
     * Get all the medicalRecords.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MedicalRecordsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MedicalRecords");
        return medicalRecordsRepository.findAll(pageable)
            .map(medicalRecordsMapper::toDto);
    }


    /**
     * Get one medicalRecords by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MedicalRecordsDTO> findOne(Long id) {
        log.debug("Request to get MedicalRecords : {}", id);
        return medicalRecordsRepository.findById(id)
            .map(medicalRecordsMapper::toDto);
    }

    /**
     * Delete the medicalRecords by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MedicalRecords : {}", id);
        medicalRecordsRepository.deleteById(id);
        medicalRecordsSearchRepository.deleteById(id);
    }

    /**
     * Search for the medicalRecords corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MedicalRecordsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MedicalRecords for query {}", query);
        return medicalRecordsSearchRepository.search(queryStringQuery(query), pageable)
            .map(medicalRecordsMapper::toDto);
    }
}
