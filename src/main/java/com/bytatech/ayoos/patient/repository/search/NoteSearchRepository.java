package com.bytatech.ayoos.patient.repository.search;

import com.bytatech.ayoos.patient.domain.Note;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Note entity.
 */
public interface NoteSearchRepository extends ElasticsearchRepository<Note, Long> {
}
