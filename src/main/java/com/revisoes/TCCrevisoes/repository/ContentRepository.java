package com.revisoes.TCCrevisoes.repository;

import com.revisoes.TCCrevisoes.dominio.Content;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ContentRepository extends JpaRepository<Content,Long> {
    
}