package com.bytatech.ayoos.patient.web.rest;

import com.bytatech.ayoos.patient.PatientApp;

import com.bytatech.ayoos.patient.domain.MedicalReference;
import com.bytatech.ayoos.patient.repository.MedicalReferenceRepository;
import com.bytatech.ayoos.patient.repository.search.MedicalReferenceSearchRepository;
import com.bytatech.ayoos.patient.service.MedicalReferenceService;
import com.bytatech.ayoos.patient.service.dto.MedicalReferenceDTO;
import com.bytatech.ayoos.patient.service.mapper.MedicalReferenceMapper;
import com.bytatech.ayoos.patient.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static com.bytatech.ayoos.patient.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MedicalReferenceResource REST controller.
 *
 * @see MedicalReferenceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PatientApp.class)
public class MedicalReferenceResourceIntTest {

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    @Autowired
    private MedicalReferenceRepository medicalReferenceRepository;

    @Autowired
    private MedicalReferenceMapper medicalReferenceMapper;
    
    @Autowired
    private MedicalReferenceService medicalReferenceService;

    /**
     * This repository is mocked in the com.bytatech.ayoos.patient.repository.search test package.
     *
     * @see com.bytatech.ayoos.patient.repository.search.MedicalReferenceSearchRepositoryMockConfiguration
     */
    @Autowired
    private MedicalReferenceSearchRepository mockMedicalReferenceSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMedicalReferenceMockMvc;

    private MedicalReference medicalReference;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MedicalReferenceResource medicalReferenceResource = new MedicalReferenceResource(medicalReferenceService);
        this.restMedicalReferenceMockMvc = MockMvcBuilders.standaloneSetup(medicalReferenceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MedicalReference createEntity(EntityManager em) {
        MedicalReference medicalReference = new MedicalReference()
            .reference(DEFAULT_REFERENCE);
        return medicalReference;
    }

    @Before
    public void initTest() {
        medicalReference = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicalReference() throws Exception {
        int databaseSizeBeforeCreate = medicalReferenceRepository.findAll().size();

        // Create the MedicalReference
        MedicalReferenceDTO medicalReferenceDTO = medicalReferenceMapper.toDto(medicalReference);
        restMedicalReferenceMockMvc.perform(post("/api/medical-references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalReferenceDTO)))
            .andExpect(status().isCreated());

        // Validate the MedicalReference in the database
        List<MedicalReference> medicalReferenceList = medicalReferenceRepository.findAll();
        assertThat(medicalReferenceList).hasSize(databaseSizeBeforeCreate + 1);
        MedicalReference testMedicalReference = medicalReferenceList.get(medicalReferenceList.size() - 1);
        assertThat(testMedicalReference.getReference()).isEqualTo(DEFAULT_REFERENCE);

        // Validate the MedicalReference in Elasticsearch
        verify(mockMedicalReferenceSearchRepository, times(1)).save(testMedicalReference);
    }

    @Test
    @Transactional
    public void createMedicalReferenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicalReferenceRepository.findAll().size();

        // Create the MedicalReference with an existing ID
        medicalReference.setId(1L);
        MedicalReferenceDTO medicalReferenceDTO = medicalReferenceMapper.toDto(medicalReference);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicalReferenceMockMvc.perform(post("/api/medical-references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalReferenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MedicalReference in the database
        List<MedicalReference> medicalReferenceList = medicalReferenceRepository.findAll();
        assertThat(medicalReferenceList).hasSize(databaseSizeBeforeCreate);

        // Validate the MedicalReference in Elasticsearch
        verify(mockMedicalReferenceSearchRepository, times(0)).save(medicalReference);
    }

    @Test
    @Transactional
    public void getAllMedicalReferences() throws Exception {
        // Initialize the database
        medicalReferenceRepository.saveAndFlush(medicalReference);

        // Get all the medicalReferenceList
        restMedicalReferenceMockMvc.perform(get("/api/medical-references?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicalReference.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())));
    }
    
    @Test
    @Transactional
    public void getMedicalReference() throws Exception {
        // Initialize the database
        medicalReferenceRepository.saveAndFlush(medicalReference);

        // Get the medicalReference
        restMedicalReferenceMockMvc.perform(get("/api/medical-references/{id}", medicalReference.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medicalReference.getId().intValue()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMedicalReference() throws Exception {
        // Get the medicalReference
        restMedicalReferenceMockMvc.perform(get("/api/medical-references/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicalReference() throws Exception {
        // Initialize the database
        medicalReferenceRepository.saveAndFlush(medicalReference);

        int databaseSizeBeforeUpdate = medicalReferenceRepository.findAll().size();

        // Update the medicalReference
        MedicalReference updatedMedicalReference = medicalReferenceRepository.findById(medicalReference.getId()).get();
        // Disconnect from session so that the updates on updatedMedicalReference are not directly saved in db
        em.detach(updatedMedicalReference);
        updatedMedicalReference
            .reference(UPDATED_REFERENCE);
        MedicalReferenceDTO medicalReferenceDTO = medicalReferenceMapper.toDto(updatedMedicalReference);

        restMedicalReferenceMockMvc.perform(put("/api/medical-references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalReferenceDTO)))
            .andExpect(status().isOk());

        // Validate the MedicalReference in the database
        List<MedicalReference> medicalReferenceList = medicalReferenceRepository.findAll();
        assertThat(medicalReferenceList).hasSize(databaseSizeBeforeUpdate);
        MedicalReference testMedicalReference = medicalReferenceList.get(medicalReferenceList.size() - 1);
        assertThat(testMedicalReference.getReference()).isEqualTo(UPDATED_REFERENCE);

        // Validate the MedicalReference in Elasticsearch
        verify(mockMedicalReferenceSearchRepository, times(1)).save(testMedicalReference);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicalReference() throws Exception {
        int databaseSizeBeforeUpdate = medicalReferenceRepository.findAll().size();

        // Create the MedicalReference
        MedicalReferenceDTO medicalReferenceDTO = medicalReferenceMapper.toDto(medicalReference);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicalReferenceMockMvc.perform(put("/api/medical-references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalReferenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MedicalReference in the database
        List<MedicalReference> medicalReferenceList = medicalReferenceRepository.findAll();
        assertThat(medicalReferenceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MedicalReference in Elasticsearch
        verify(mockMedicalReferenceSearchRepository, times(0)).save(medicalReference);
    }

    @Test
    @Transactional
    public void deleteMedicalReference() throws Exception {
        // Initialize the database
        medicalReferenceRepository.saveAndFlush(medicalReference);

        int databaseSizeBeforeDelete = medicalReferenceRepository.findAll().size();

        // Get the medicalReference
        restMedicalReferenceMockMvc.perform(delete("/api/medical-references/{id}", medicalReference.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MedicalReference> medicalReferenceList = medicalReferenceRepository.findAll();
        assertThat(medicalReferenceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MedicalReference in Elasticsearch
        verify(mockMedicalReferenceSearchRepository, times(1)).deleteById(medicalReference.getId());
    }

    @Test
    @Transactional
    public void searchMedicalReference() throws Exception {
        // Initialize the database
        medicalReferenceRepository.saveAndFlush(medicalReference);
        when(mockMedicalReferenceSearchRepository.search(queryStringQuery("id:" + medicalReference.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(medicalReference), PageRequest.of(0, 1), 1));
        // Search the medicalReference
        restMedicalReferenceMockMvc.perform(get("/api/_search/medical-references?query=id:" + medicalReference.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicalReference.getId().intValue())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicalReference.class);
        MedicalReference medicalReference1 = new MedicalReference();
        medicalReference1.setId(1L);
        MedicalReference medicalReference2 = new MedicalReference();
        medicalReference2.setId(medicalReference1.getId());
        assertThat(medicalReference1).isEqualTo(medicalReference2);
        medicalReference2.setId(2L);
        assertThat(medicalReference1).isNotEqualTo(medicalReference2);
        medicalReference1.setId(null);
        assertThat(medicalReference1).isNotEqualTo(medicalReference2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicalReferenceDTO.class);
        MedicalReferenceDTO medicalReferenceDTO1 = new MedicalReferenceDTO();
        medicalReferenceDTO1.setId(1L);
        MedicalReferenceDTO medicalReferenceDTO2 = new MedicalReferenceDTO();
        assertThat(medicalReferenceDTO1).isNotEqualTo(medicalReferenceDTO2);
        medicalReferenceDTO2.setId(medicalReferenceDTO1.getId());
        assertThat(medicalReferenceDTO1).isEqualTo(medicalReferenceDTO2);
        medicalReferenceDTO2.setId(2L);
        assertThat(medicalReferenceDTO1).isNotEqualTo(medicalReferenceDTO2);
        medicalReferenceDTO1.setId(null);
        assertThat(medicalReferenceDTO1).isNotEqualTo(medicalReferenceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(medicalReferenceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(medicalReferenceMapper.fromId(null)).isNull();
    }
}
