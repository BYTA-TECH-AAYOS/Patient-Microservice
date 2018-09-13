package com.bytatech.ayoos.patient.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of MedicalRecordsSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class MedicalRecordsSearchRepositoryMockConfiguration {

    @MockBean
    private MedicalRecordsSearchRepository mockMedicalRecordsSearchRepository;

}
