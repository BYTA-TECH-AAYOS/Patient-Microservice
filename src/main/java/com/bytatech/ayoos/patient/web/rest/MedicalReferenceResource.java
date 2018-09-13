package com.bytatech.ayoos.patient.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bytatech.ayoos.patient.service.MedicalReferenceService;
import com.bytatech.ayoos.patient.web.rest.errors.BadRequestAlertException;
import com.bytatech.ayoos.patient.web.rest.util.HeaderUtil;
import com.bytatech.ayoos.patient.web.rest.util.PaginationUtil;
import com.bytatech.ayoos.patient.service.dto.MedicalReferenceDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing MedicalReference.
 */
@RestController
@RequestMapping("/api")
public class MedicalReferenceResource {

    private final Logger log = LoggerFactory.getLogger(MedicalReferenceResource.class);

    private static final String ENTITY_NAME = "patientMedicalReference";

    private final MedicalReferenceService medicalReferenceService;

    public MedicalReferenceResource(MedicalReferenceService medicalReferenceService) {
        this.medicalReferenceService = medicalReferenceService;
    }

    /**
     * POST  /medical-references : Create a new medicalReference.
     *
     * @param medicalReferenceDTO the medicalReferenceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new medicalReferenceDTO, or with status 400 (Bad Request) if the medicalReference has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/medical-references")
    @Timed
    public ResponseEntity<MedicalReferenceDTO> createMedicalReference(@RequestBody MedicalReferenceDTO medicalReferenceDTO) throws URISyntaxException {
        log.debug("REST request to save MedicalReference : {}", medicalReferenceDTO);
        if (medicalReferenceDTO.getId() != null) {
            throw new BadRequestAlertException("A new medicalReference cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicalReferenceDTO result = medicalReferenceService.save(medicalReferenceDTO);
        return ResponseEntity.created(new URI("/api/medical-references/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /medical-references : Updates an existing medicalReference.
     *
     * @param medicalReferenceDTO the medicalReferenceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated medicalReferenceDTO,
     * or with status 400 (Bad Request) if the medicalReferenceDTO is not valid,
     * or with status 500 (Internal Server Error) if the medicalReferenceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/medical-references")
    @Timed
    public ResponseEntity<MedicalReferenceDTO> updateMedicalReference(@RequestBody MedicalReferenceDTO medicalReferenceDTO) throws URISyntaxException {
        log.debug("REST request to update MedicalReference : {}", medicalReferenceDTO);
        if (medicalReferenceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MedicalReferenceDTO result = medicalReferenceService.save(medicalReferenceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, medicalReferenceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /medical-references : get all the medicalReferences.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of medicalReferences in body
     */
    @GetMapping("/medical-references")
    @Timed
    public ResponseEntity<List<MedicalReferenceDTO>> getAllMedicalReferences(Pageable pageable) {
        log.debug("REST request to get a page of MedicalReferences");
        Page<MedicalReferenceDTO> page = medicalReferenceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/medical-references");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /medical-references/:id : get the "id" medicalReference.
     *
     * @param id the id of the medicalReferenceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the medicalReferenceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/medical-references/{id}")
    @Timed
    public ResponseEntity<MedicalReferenceDTO> getMedicalReference(@PathVariable Long id) {
        log.debug("REST request to get MedicalReference : {}", id);
        Optional<MedicalReferenceDTO> medicalReferenceDTO = medicalReferenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicalReferenceDTO);
    }

    /**
     * DELETE  /medical-references/:id : delete the "id" medicalReference.
     *
     * @param id the id of the medicalReferenceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/medical-references/{id}")
    @Timed
    public ResponseEntity<Void> deleteMedicalReference(@PathVariable Long id) {
        log.debug("REST request to delete MedicalReference : {}", id);
        medicalReferenceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/medical-references?query=:query : search for the medicalReference corresponding
     * to the query.
     *
     * @param query the query of the medicalReference search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/medical-references")
    @Timed
    public ResponseEntity<List<MedicalReferenceDTO>> searchMedicalReferences(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MedicalReferences for query {}", query);
        Page<MedicalReferenceDTO> page = medicalReferenceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/medical-references");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
