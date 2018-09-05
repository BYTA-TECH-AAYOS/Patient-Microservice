package com.bytatech.ayoos.patient.repository.search;

import com.bytatech.ayoos.patient.domain.ContactInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ContactInfo entity.
 */
public interface ContactInfoSearchRepository extends ElasticsearchRepository<ContactInfo, Long> {
}
