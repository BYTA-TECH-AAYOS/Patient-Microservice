package com.bytatech.ayoos.patient.repository.search;

import com.bytatech.ayoos.patient.domain.MedicalRecords;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MedicalRecords entity.
 */
public interface MedicalRecordsSearchRepository extends ElasticsearchRepository<MedicalRecords, Long> {
}
