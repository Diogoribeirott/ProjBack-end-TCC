package com.revisoes.TCCrevisoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revisoes.TCCrevisoes.dominio.Review;

@Repository
public interface ReviewRepository  extends JpaRepository<Review,Long>{
    
}