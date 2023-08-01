package com.revisoes.TCCrevisoes.repository;

import com.revisoes.TCCrevisoes.dominio.Review;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ReviewRepository  extends JpaRepository<Review,Long>{
    
}