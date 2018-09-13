package com.bytatech.ayoos.patient.repository.search;

import com.bytatech.ayoos.patient.domain.MedicalReference;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MedicalReference entity.
 */
public interface MedicalReferenceSearchRepository extends ElasticsearchRepository<MedicalReference, Long> {
}
