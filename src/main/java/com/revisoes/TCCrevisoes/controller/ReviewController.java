package com.revisoes.TCCrevisoes.controller;

import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import com.revisoes.TCCrevisoes.DTO.ReviewDto;
import com.revisoes.TCCrevisoes.dominio.Review;
import org.springframework.http.ResponseEntity;
import com.revisoes.TCCrevisoes.service.ReviewService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping(value = "/review")
public class ReviewController {

  @Autowired
  private ReviewService reviewService;

  @GetMapping(path = "/all")
  public ResponseEntity< List<Review>> findAll(){
    return status(HttpStatus.OK).body(reviewService.findAll());

  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<Review> findById(@PathVariable Long id){
    try {
      return status(HttpStatus.OK).body(reviewService.findById(id));
    } catch (ObjectNotFoundException e) {
      return notFound().build();
    }
   
  }

  @PostMapping
  public ResponseEntity<Review> saveReview(@Valid @RequestBody  Review review){
    Review saveReview = reviewService.saveReview(review);
    return status(HttpStatus.CREATED).body(saveReview);

  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> deleteReview(@PathVariable Long id){
    try {
      reviewService.deleteReview(id);
      return noContent().build();
    } catch (ObjectNotFoundException e) {
      return notFound().build();
    }

  }
 
  @PutMapping(value = "/{id}")
  public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody @Valid ReviewDto reviewDto){
    try {
      return ok().body(reviewService.updateReview(id, reviewDto));
    } catch (ObjectNotFoundException e) {
      return notFound().build();
    }

  }

}