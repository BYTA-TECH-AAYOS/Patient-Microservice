package com.bytatech.ayoos.patient.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bytatech.ayoos.patient.service.MedicalRecordsService;
import com.bytatech.ayoos.patient.service.dto.MedicalRecordsDTO;
import com.bytatech.ayoos.patient.web.rest.errors.BadRequestAlertException;
import com.bytatech.ayoos.patient.web.rest.util.HeaderUtil;
import com.bytatech.ayoos.patient.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing MedicalRecords.
 */
@RestController
@RequestMapping("/api")
public class MedicalRecordsResource {

    private final Logger log = LoggerFactory.getLogger(MedicalRecordsResource.class);

    private static final String ENTITY_NAME = "patientMedicalRecords";

    private final MedicalRecordsService medicalRecordsService;

    public MedicalRecordsResource(MedicalRecordsService medicalRecordsService) {
        this.medicalRecordsService = medicalRecordsService;
    }

    /**
     * POST  /medical-records : Create a new medicalRecords.
     *
     * @param medicalRecordsDTO the medicalRecordsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new medicalRecordsDTO, or with status 400 (Bad Request) if the medicalRecords has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/medical-records")
    @Timed
    public ResponseEntity<MedicalRecordsDTO> createMedicalRecords(@RequestBody MedicalRecordsDTO medicalRecordsDTO) throws URISyntaxException {
        log.debug("REST request to save MedicalRecords : {}", medicalRecordsDTO);
        if (medicalRecordsDTO.getId() != null) {
            throw new BadRequestAlertException("A new medicalRecords cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicalRecordsDTO result = medicalRecordsService.save(medicalRecordsDTO);
        return ResponseEntity.created(new URI("/api/medical-records/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /medical-records : Updates an existing medicalRecords.
     *
     * @param medicalRecordsDTO the medicalRecordsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated medicalRecordsDTO,
     * or with status 400 (Bad Request) if the medicalRecordsDTO is not valid,
     * or with status 500 (Internal Server Error) if the medicalRecordsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/medical-records")
    @Timed
    public ResponseEntity<MedicalRecordsDTO> updateMedicalRecords(@RequestBody MedicalRecordsDTO medicalRecordsDTO) throws URISyntaxException {
        log.debug("REST request to update MedicalRecords : {}", medicalRecordsDTO);
        if (medicalRecordsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MedicalRecordsDTO result = medicalRecordsService.save(medicalRecordsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, medicalRecordsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /medical-records : get all the medicalRecords.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of medicalRecords in body
     */
    @GetMapping("/medical-records")
    @Timed
    public ResponseEntity<List<MedicalRecordsDTO>> getAllMedicalRecords(Pageable pageable) {
        log.debug("REST request to get a page of MedicalRecords");
        Page<MedicalRecordsDTO> page = medicalRecordsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/medical-records");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /medical-records/:id : get the "id" medicalRecords.
     *
     * @param id the id of the medicalRecordsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the medicalRecordsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/medical-records/{id}")
    @Timed
    public ResponseEntity<MedicalRecordsDTO> getMedicalRecords(@PathVariable Long id) {
        log.debug("REST request to get MedicalRecords : {}", id);
        Optional<MedicalRecordsDTO> medicalRecordsDTO = medicalRecordsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicalRecordsDTO);
    }

    /**
     * DELETE  /medical-records/:id : delete the "id" medicalRecords.
     *
     * @param id the id of the medicalRecordsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/medical-records/{id}")
    @Timed
    public ResponseEntity<Void> deleteMedicalRecords(@PathVariable Long id) {
        log.debug("REST request to delete MedicalRecords : {}", id);
        medicalRecordsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/medical-records?query=:query : search for the medicalRecords corresponding
     * to the query.
     *
     * @param query the query of the medicalRecords search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/medical-records")
    @Timed
    public ResponseEntity<List<MedicalRecordsDTO>> searchMedicalRecords(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MedicalRecords for query {}", query);
        Page<MedicalRecordsDTO> page = medicalRecordsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/medical-records");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
