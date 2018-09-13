package com.bytatech.ayoos.patient.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bytatech.ayoos.patient.service.NoteService;
import com.bytatech.ayoos.patient.web.rest.errors.BadRequestAlertException;
import com.bytatech.ayoos.patient.web.rest.util.HeaderUtil;
import com.bytatech.ayoos.patient.web.rest.util.PaginationUtil;
import com.bytatech.ayoos.patient.service.dto.NoteDTO;
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
 * REST controller for managing Note.
 */
@RestController
@RequestMapping("/api")
public class NoteResource {

    private final Logger log = LoggerFactory.getLogger(NoteResource.class);

    private static final String ENTITY_NAME = "patientNote";

    private final NoteService noteService;

    public NoteResource(NoteService noteService) {
        this.noteService = noteService;
    }

    /**
     * POST  /notes : Create a new note.
     *
     * @param noteDTO the noteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new noteDTO, or with status 400 (Bad Request) if the note has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/notes")
    @Timed
    public ResponseEntity<NoteDTO> createNote(@RequestBody NoteDTO noteDTO) throws URISyntaxException {
        log.debug("REST request to save Note : {}", noteDTO);
        if (noteDTO.getId() != null) {
            throw new BadRequestAlertException("A new note cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NoteDTO result = noteService.save(noteDTO);
        return ResponseEntity.created(new URI("/api/notes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /notes : Updates an existing note.
     *
     * @param noteDTO the noteDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated noteDTO,
     * or with status 400 (Bad Request) if the noteDTO is not valid,
     * or with status 500 (Internal Server Error) if the noteDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/notes")
    @Timed
    public ResponseEntity<NoteDTO> updateNote(@RequestBody NoteDTO noteDTO) throws URISyntaxException {
        log.debug("REST request to update Note : {}", noteDTO);
        if (noteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NoteDTO result = noteService.save(noteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, noteDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /notes : get all the notes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of notes in body
     */
    @GetMapping("/notes")
    @Timed
    public ResponseEntity<List<NoteDTO>> getAllNotes(Pageable pageable) {
        log.debug("REST request to get a page of Notes");
        Page<NoteDTO> page = noteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/notes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /notes/:id : get the "id" note.
     *
     * @param id the id of the noteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the noteDTO, or with status 404 (Not Found)
     */
    @GetMapping("/notes/{id}")
    @Timed
    public ResponseEntity<NoteDTO> getNote(@PathVariable Long id) {
        log.debug("REST request to get Note : {}", id);
        Optional<NoteDTO> noteDTO = noteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(noteDTO);
    }

    /**
     * DELETE  /notes/:id : delete the "id" note.
     *
     * @param id the id of the noteDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/notes/{id}")
    @Timed
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        log.debug("REST request to delete Note : {}", id);
        noteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/notes?query=:query : search for the note corresponding
     * to the query.
     *
     * @param query the query of the note search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/notes")
    @Timed
    public ResponseEntity<List<NoteDTO>> searchNotes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Notes for query {}", query);
        Page<NoteDTO> page = noteService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/notes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
