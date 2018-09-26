package com.bytatech.ayoos.patient.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bytatech.ayoos.patient.domain.State;
import com.bytatech.ayoos.patient.repository.StateRepository;
import com.bytatech.ayoos.patient.repository.search.StateSearchRepository;
import com.bytatech.ayoos.patient.service.StateService;
import com.bytatech.ayoos.patient.service.dto.StateDTO;
import com.bytatech.ayoos.patient.service.mapper.StateMapper;

/**
 * Service Implementation for managing State.
 */
@Service
@Transactional
public class StateServiceImpl implements StateService {

    private final Logger log = LoggerFactory.getLogger(StateServiceImpl.class);

    private final StateRepository stateRepository;

    private final StateMapper stateMapper;

    private final StateSearchRepository stateSearchRepository;

    public StateServiceImpl(StateRepository stateRepository, StateMapper stateMapper, StateSearchRepository stateSearchRepository) {
        this.stateRepository = stateRepository;
        this.stateMapper = stateMapper;
        this.stateSearchRepository = stateSearchRepository;
    }

    /**
     * Save a state.
     *
     * @param stateDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StateDTO save(StateDTO stateDTO) {
        log.debug("Request to save State : {}", stateDTO);
        State state = stateMapper.toEntity(stateDTO);
        state = stateRepository.save(state);
        StateDTO result = stateMapper.toDto(state);
        stateSearchRepository.save(state);
        return result;
    }

    /**
     * Get all the states.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all States");
        return stateRepository.findAll(pageable)
            .map(stateMapper::toDto);
    }


    /**
     * Get one state by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StateDTO> findOne(Long id) {
        log.debug("Request to get State : {}", id);
        return stateRepository.findById(id)
            .map(stateMapper::toDto);
    }

    /**
     * Delete the state by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete State : {}", id);
        stateRepository.deleteById(id);
        stateSearchRepository.deleteById(id);
    }

    /**
     * Search for the state corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StateDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of States for query {}", query);
        return stateSearchRepository.search(queryStringQuery(query), pageable)
            .map(stateMapper::toDto);
    }

	@Override
	public Page<StateDTO> findAllByCountry_name(String name, Pageable pageable) {
		log.debug("Request to get all States by by country name");
        return stateRepository.findAllByCountry_name(name, pageable).map(stateMapper::toDto);
	}
}
