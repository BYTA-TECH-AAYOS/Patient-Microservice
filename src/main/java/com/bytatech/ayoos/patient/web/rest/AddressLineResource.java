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

import com.bytatech.ayoos.patient.service.AddressLineService;
import com.bytatech.ayoos.patient.service.dto.AddressLineDTO;
import com.bytatech.ayoos.patient.web.rest.errors.BadRequestAlertException;
import com.bytatech.ayoos.patient.web.rest.util.HeaderUtil;
import com.bytatech.ayoos.patient.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing AddressLine.
 */
@RestController
@RequestMapping("/api")
public class AddressLineResource {

    private final Logger log = LoggerFactory.getLogger(AddressLineResource.class);

    private static final String ENTITY_NAME = "patientAddressLine";

    private final AddressLineService addressLineService;

    public AddressLineResource(AddressLineService addressLineService) {
        this.addressLineService = addressLineService;
    }

    /**
     * POST  /address-lines : Create a new addressLine.
     *
     * @param addressLineDTO the addressLineDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new addressLineDTO, or with status 400 (Bad Request) if the addressLine has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/address-lines")
    @Timed
    public ResponseEntity<AddressLineDTO> createAddressLine(@RequestBody AddressLineDTO addressLineDTO) throws URISyntaxException {
        log.debug("REST request to save AddressLine : {}", addressLineDTO);
        if (addressLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new addressLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AddressLineDTO result = addressLineService.save(addressLineDTO);
        return ResponseEntity.created(new URI("/api/address-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /address-lines : Updates an existing addressLine.
     *
     * @param addressLineDTO the addressLineDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated addressLineDTO,
     * or with status 400 (Bad Request) if the addressLineDTO is not valid,
     * or with status 500 (Internal Server Error) if the addressLineDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/address-lines")
    @Timed
    public ResponseEntity<AddressLineDTO> updateAddressLine(@RequestBody AddressLineDTO addressLineDTO) throws URISyntaxException {
        log.debug("REST request to update AddressLine : {}", addressLineDTO);
        if (addressLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AddressLineDTO result = addressLineService.save(addressLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, addressLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /address-lines : get all the addressLines.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of addressLines in body
     */
    @GetMapping("/address-lines")
    @Timed
    public ResponseEntity<List<AddressLineDTO>> getAllAddressLines(Pageable pageable) {
        log.debug("REST request to get a page of AddressLines");
        Page<AddressLineDTO> page = addressLineService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/address-lines");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /address-lines/:id : get the "id" addressLine.
     *
     * @param id the id of the addressLineDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the addressLineDTO, or with status 404 (Not Found)
     */
    @GetMapping("/address-lines/{id}")
    @Timed
    public ResponseEntity<AddressLineDTO> getAddressLine(@PathVariable Long id) {
        log.debug("REST request to get AddressLine : {}", id);
        Optional<AddressLineDTO> addressLineDTO = addressLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(addressLineDTO);
    }

    /**
     * DELETE  /address-lines/:id : delete the "id" addressLine.
     *
     * @param id the id of the addressLineDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/address-lines/{id}")
    @Timed
    public ResponseEntity<Void> deleteAddressLine(@PathVariable Long id) {
        log.debug("REST request to delete AddressLine : {}", id);
        addressLineService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/address-lines?query=:query : search for the addressLine corresponding
     * to the query.
     *
     * @param query the query of the addressLine search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/address-lines")
    @Timed
    public ResponseEntity<List<AddressLineDTO>> searchAddressLines(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AddressLines for query {}", query);
        Page<AddressLineDTO> page = addressLineService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/address-lines");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
