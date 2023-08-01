package com.revisoes.TCCrevisoes.repository;

import org.springframework.stereotype.Repository;
import com.revisoes.TCCrevisoes.dominio.Subjects;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SubjectsRepository extends JpaRepository<Subjects,Long> {
    
}