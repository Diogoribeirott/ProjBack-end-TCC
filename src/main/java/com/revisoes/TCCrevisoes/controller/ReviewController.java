package com.revisoes.TCCrevisoes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.revisoes.TCCrevisoes.DTO.ReviewDto;
import com.revisoes.TCCrevisoes.dominio.Review;
import com.revisoes.TCCrevisoes.service.ReviewService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/review")
public class ReviewController {

  @Autowired
  private ReviewService reviewService;

  @GetMapping(path = "/all")
  public ResponseEntity< List<Review>> findAll(){
    return ResponseEntity.status(HttpStatus.OK).body(reviewService.findAll());

  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<Review> findById(@PathVariable Long id){
   return ResponseEntity.status(HttpStatus.OK).body(reviewService.findById(id));

  }

  @PostMapping
  public ResponseEntity<Review> sabeReview(@Valid @RequestBody  Review review){
    return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.saveReview(review));

  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> deleteReview(@PathVariable Long id){
    reviewService.deleteReview(id);
    return ResponseEntity.noContent().build();

  }
 
  @PutMapping(value = "/{id}")
  public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody @Valid ReviewDto reviewDto){
  return ResponseEntity.ok().body(reviewService.updateReview(id, reviewDto));

}
    
    
}