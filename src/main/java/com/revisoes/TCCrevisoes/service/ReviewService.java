package com.revisoes.TCCrevisoes.service;

import java.util.List;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import com.revisoes.TCCrevisoes.DTO.ReviewDto;
import com.revisoes.TCCrevisoes.dominio.Review;
import com.revisoes.TCCrevisoes.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ReviewService {

  @Autowired
  private ReviewRepository reviewRepository;

  public List<Review> findAll(){
    return reviewRepository.findAll();

  }

  public Review findById(Long id){
    return reviewRepository.findById(id)
    .orElseThrow(() -> new ObjectNotFoundException("Object not found", id));

  }

  public Review saveReview(Review review){
    return reviewRepository.save(review);

  }

  public Review updateReview(Long id, ReviewDto reviewDto){
    findById(id);
    Review review = findById(id);
    review.setDayOfReview(reviewDto.getDayOfReview());
    review.setDone(reviewDto.getDone());
    return reviewRepository.save(review);

  }

  public void deleteReview(Long id){
    findById(id);
    reviewRepository.deleteById(id);

  }
   
}