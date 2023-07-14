package com.revisoes.TCCrevisoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.revisoes.TCCrevisoes.dominio.Content;

@Repository
public interface ContentRepository extends JpaRepository<Content,Long> {
    
}