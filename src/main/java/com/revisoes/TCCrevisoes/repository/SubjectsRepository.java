package com.revisoes.TCCrevisoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revisoes.TCCrevisoes.dominio.Subjects;

@Repository
public interface SubjectsRepository extends JpaRepository<Subjects,Long> {
    
}