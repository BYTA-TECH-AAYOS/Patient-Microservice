package com.bytatech.ayoos.patient.web.rest;

import com.bytatech.ayoos.patient.PatientApp;

import com.bytatech.ayoos.patient.domain.MedicalRecords;
import com.bytatech.ayoos.patient.repository.MedicalRecordsRepository;
import com.bytatech.ayoos.patient.repository.search.MedicalRecordsSearchRepository;
import com.bytatech.ayoos.patient.service.MedicalRecordsService;
import com.bytatech.ayoos.patient.service.dto.MedicalRecordsDTO;
import com.bytatech.ayoos.patient.service.mapper.MedicalRecordsMapper;
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
 * Test class for the MedicalRecordsResource REST controller.
 *
 * @see MedicalRecordsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PatientApp.class)
public class MedicalRecordsResourceIntTest {

    private static final String DEFAULT_DISEASE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISEASE_NAME = "BBBBBBBBBB";

    @Autowired
    private MedicalRecordsRepository medicalRecordsRepository;

    @Autowired
    private MedicalRecordsMapper medicalRecordsMapper;
    
    @Autowired
    private MedicalRecordsService medicalRecordsService;

    /**
     * This repository is mocked in the com.bytatech.ayoos.patient.repository.search test package.
     *
     * @see com.bytatech.ayoos.patient.repository.search.MedicalRecordsSearchRepositoryMockConfiguration
     */
    @Autowired
    private MedicalRecordsSearchRepository mockMedicalRecordsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMedicalRecordsMockMvc;

    private MedicalRecords medicalRecords;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MedicalRecordsResource medicalRecordsResource = new MedicalRecordsResource(medicalRecordsService);
        this.restMedicalRecordsMockMvc = MockMvcBuilders.standaloneSetup(medicalRecordsResource)
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
    public static MedicalRecords createEntity(EntityManager em) {
        MedicalRecords medicalRecords = new MedicalRecords()
            .diseaseName(DEFAULT_DISEASE_NAME);
        return medicalRecords;
    }

    @Before
    public void initTest() {
        medicalRecords = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicalRecords() throws Exception {
        int databaseSizeBeforeCreate = medicalRecordsRepository.findAll().size();

        // Create the MedicalRecords
        MedicalRecordsDTO medicalRecordsDTO = medicalRecordsMapper.toDto(medicalRecords);
        restMedicalRecordsMockMvc.perform(post("/api/medical-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalRecordsDTO)))
            .andExpect(status().isCreated());

        // Validate the MedicalRecords in the database
        List<MedicalRecords> medicalRecordsList = medicalRecordsRepository.findAll();
        assertThat(medicalRecordsList).hasSize(databaseSizeBeforeCreate + 1);
        MedicalRecords testMedicalRecords = medicalRecordsList.get(medicalRecordsList.size() - 1);
        assertThat(testMedicalRecords.getDiseaseName()).isEqualTo(DEFAULT_DISEASE_NAME);

        // Validate the MedicalRecords in Elasticsearch
        verify(mockMedicalRecordsSearchRepository, times(1)).save(testMedicalRecords);
    }

    @Test
    @Transactional
    public void createMedicalRecordsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicalRecordsRepository.findAll().size();

        // Create the MedicalRecords with an existing ID
        medicalRecords.setId(1L);
        MedicalRecordsDTO medicalRecordsDTO = medicalRecordsMapper.toDto(medicalRecords);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicalRecordsMockMvc.perform(post("/api/medical-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalRecordsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MedicalRecords in the database
        List<MedicalRecords> medicalRecordsList = medicalRecordsRepository.findAll();
        assertThat(medicalRecordsList).hasSize(databaseSizeBeforeCreate);

        // Validate the MedicalRecords in Elasticsearch
        verify(mockMedicalRecordsSearchRepository, times(0)).save(medicalRecords);
    }

    @Test
    @Transactional
    public void getAllMedicalRecords() throws Exception {
        // Initialize the database
        medicalRecordsRepository.saveAndFlush(medicalRecords);

        // Get all the medicalRecordsList
        restMedicalRecordsMockMvc.perform(get("/api/medical-records?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicalRecords.getId().intValue())))
            .andExpect(jsonPath("$.[*].diseaseName").value(hasItem(DEFAULT_DISEASE_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getMedicalRecords() throws Exception {
        // Initialize the database
        medicalRecordsRepository.saveAndFlush(medicalRecords);

        // Get the medicalRecords
        restMedicalRecordsMockMvc.perform(get("/api/medical-records/{id}", medicalRecords.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medicalRecords.getId().intValue()))
            .andExpect(jsonPath("$.diseaseName").value(DEFAULT_DISEASE_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMedicalRecords() throws Exception {
        // Get the medicalRecords
        restMedicalRecordsMockMvc.perform(get("/api/medical-records/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicalRecords() throws Exception {
        // Initialize the database
        medicalRecordsRepository.saveAndFlush(medicalRecords);

        int databaseSizeBeforeUpdate = medicalRecordsRepository.findAll().size();

        // Update the medicalRecords
        MedicalRecords updatedMedicalRecords = medicalRecordsRepository.findById(medicalRecords.getId()).get();
        // Disconnect from session so that the updates on updatedMedicalRecords are not directly saved in db
        em.detach(updatedMedicalRecords);
        updatedMedicalRecords
            .diseaseName(UPDATED_DISEASE_NAME);
        MedicalRecordsDTO medicalRecordsDTO = medicalRecordsMapper.toDto(updatedMedicalRecords);

        restMedicalRecordsMockMvc.perform(put("/api/medical-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalRecordsDTO)))
            .andExpect(status().isOk());

        // Validate the MedicalRecords in the database
        List<MedicalRecords> medicalRecordsList = medicalRecordsRepository.findAll();
        assertThat(medicalRecordsList).hasSize(databaseSizeBeforeUpdate);
        MedicalRecords testMedicalRecords = medicalRecordsList.get(medicalRecordsList.size() - 1);
        assertThat(testMedicalRecords.getDiseaseName()).isEqualTo(UPDATED_DISEASE_NAME);

        // Validate the MedicalRecords in Elasticsearch
        verify(mockMedicalRecordsSearchRepository, times(1)).save(testMedicalRecords);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicalRecords() throws Exception {
        int databaseSizeBeforeUpdate = medicalRecordsRepository.findAll().size();

        // Create the MedicalRecords
        MedicalRecordsDTO medicalRecordsDTO = medicalRecordsMapper.toDto(medicalRecords);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicalRecordsMockMvc.perform(put("/api/medical-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalRecordsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MedicalRecords in the database
        List<MedicalRecords> medicalRecordsList = medicalRecordsRepository.findAll();
        assertThat(medicalRecordsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MedicalRecords in Elasticsearch
        verify(mockMedicalRecordsSearchRepository, times(0)).save(medicalRecords);
    }

    @Test
    @Transactional
    public void deleteMedicalRecords() throws Exception {
        // Initialize the database
        medicalRecordsRepository.saveAndFlush(medicalRecords);

        int databaseSizeBeforeDelete = medicalRecordsRepository.findAll().size();

        // Get the medicalRecords
        restMedicalRecordsMockMvc.perform(delete("/api/medical-records/{id}", medicalRecords.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MedicalRecords> medicalRecordsList = medicalRecordsRepository.findAll();
        assertThat(medicalRecordsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MedicalRecords in Elasticsearch
        verify(mockMedicalRecordsSearchRepository, times(1)).deleteById(medicalRecords.getId());
    }

    @Test
    @Transactional
    public void searchMedicalRecords() throws Exception {
        // Initialize the database
        medicalRecordsRepository.saveAndFlush(medicalRecords);
        when(mockMedicalRecordsSearchRepository.search(queryStringQuery("id:" + medicalRecords.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(medicalRecords), PageRequest.of(0, 1), 1));
        // Search the medicalRecords
        restMedicalRecordsMockMvc.perform(get("/api/_search/medical-records?query=id:" + medicalRecords.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicalRecords.getId().intValue())))
            .andExpect(jsonPath("$.[*].diseaseName").value(hasItem(DEFAULT_DISEASE_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicalRecords.class);
        MedicalRecords medicalRecords1 = new MedicalRecords();
        medicalRecords1.setId(1L);
        MedicalRecords medicalRecords2 = new MedicalRecords();
        medicalRecords2.setId(medicalRecords1.getId());
        assertThat(medicalRecords1).isEqualTo(medicalRecords2);
        medicalRecords2.setId(2L);
        assertThat(medicalRecords1).isNotEqualTo(medicalRecords2);
        medicalRecords1.setId(null);
        assertThat(medicalRecords1).isNotEqualTo(medicalRecords2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicalRecordsDTO.class);
        MedicalRecordsDTO medicalRecordsDTO1 = new MedicalRecordsDTO();
        medicalRecordsDTO1.setId(1L);
        MedicalRecordsDTO medicalRecordsDTO2 = new MedicalRecordsDTO();
        assertThat(medicalRecordsDTO1).isNotEqualTo(medicalRecordsDTO2);
        medicalRecordsDTO2.setId(medicalRecordsDTO1.getId());
        assertThat(medicalRecordsDTO1).isEqualTo(medicalRecordsDTO2);
        medicalRecordsDTO2.setId(2L);
        assertThat(medicalRecordsDTO1).isNotEqualTo(medicalRecordsDTO2);
        medicalRecordsDTO1.setId(null);
        assertThat(medicalRecordsDTO1).isNotEqualTo(medicalRecordsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(medicalRecordsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(medicalRecordsMapper.fromId(null)).isNull();
    }
}
