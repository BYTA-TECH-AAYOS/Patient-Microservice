package com.bytatech.ayoos.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bytatech.ayoos.patient.domain.Note;


/**
 * Spring Data  repository for the Note entity.
 */
@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

}
