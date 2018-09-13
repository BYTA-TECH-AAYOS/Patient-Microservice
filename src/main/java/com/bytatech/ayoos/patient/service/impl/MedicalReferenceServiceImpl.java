package com.bytatech.ayoos.patient.service.impl;

import com.bytatech.ayoos.patient.service.MedicalReferenceService;
import com.bytatech.ayoos.patient.domain.MedicalReference;
import com.bytatech.ayoos.patient.repository.MedicalReferenceRepository;
import com.bytatech.ayoos.patient.repository.search.MedicalReferenceSearchRepository;
import com.bytatech.ayoos.patient.service.dto.MedicalReferenceDTO;
import com.bytatech.ayoos.patient.service.mapper.MedicalReferenceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing MedicalReference.
 */
@Service
@Transactional
public class MedicalReferenceServiceImpl implements MedicalReferenceService {

    private final Logger log = LoggerFactory.getLogger(MedicalReferenceServiceImpl.class);

    private final MedicalReferenceRepository medicalReferenceRepository;

    private final MedicalReferenceMapper medicalReferenceMapper;

    private final MedicalReferenceSearchRepository medicalReferenceSearchRepository;

    public MedicalReferenceServiceImpl(MedicalReferenceRepository medicalReferenceRepository, MedicalReferenceMapper medicalReferenceMapper, MedicalReferenceSearchRepository medicalReferenceSearchRepository) {
        this.medicalReferenceRepository = medicalReferenceRepository;
        this.medicalReferenceMapper = medicalReferenceMapper;
        this.medicalReferenceSearchRepository = medicalReferenceSearchRepository;
    }

    /**
     * Save a medicalReference.
     *
     * @param medicalReferenceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MedicalReferenceDTO save(MedicalReferenceDTO medicalReferenceDTO) {
        log.debug("Request to save MedicalReference : {}", medicalReferenceDTO);
        MedicalReference medicalReference = medicalReferenceMapper.toEntity(medicalReferenceDTO);
        medicalReference = medicalReferenceRepository.save(medicalReference);
        MedicalReferenceDTO result = medicalReferenceMapper.toDto(medicalReference);
        medicalReferenceSearchRepository.save(medicalReference);
        return result;
    }

    /**
     * Get all the medicalReferences.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MedicalReferenceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MedicalReferences");
        return medicalReferenceRepository.findAll(pageable)
            .map(medicalReferenceMapper::toDto);
    }


    /**
     * Get one medicalReference by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MedicalReferenceDTO> findOne(Long id) {
        log.debug("Request to get MedicalReference : {}", id);
        return medicalReferenceRepository.findById(id)
            .map(medicalReferenceMapper::toDto);
    }

    /**
     * Delete the medicalReference by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MedicalReference : {}", id);
        medicalReferenceRepository.deleteById(id);
        medicalReferenceSearchRepository.deleteById(id);
    }

    /**
     * Search for the medicalReference corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MedicalReferenceDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MedicalReferences for query {}", query);
        return medicalReferenceSearchRepository.search(queryStringQuery(query), pageable)
            .map(medicalReferenceMapper::toDto);
    }
}
