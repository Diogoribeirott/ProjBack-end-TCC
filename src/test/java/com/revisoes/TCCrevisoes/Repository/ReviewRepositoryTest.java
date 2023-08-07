package com.revisoes.TCCrevisoes.Repository;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.revisoes.TCCrevisoes.dominio.Review;
import com.revisoes.TCCrevisoes.repository.ReviewRepository;

@DataJpaTest
public class ReviewRepositoryTest {

  @Autowired
  private ReviewRepository reviewRepository;

  @Test
  @DisplayName("must persist a review in the database")
  public void saveReviewTest(){
    //scenario 
    Review review = createReviewAdmin();

    //execution 
    Review reviewR = reviewRepository.save(review);

    //validation
    Assertions.assertThat(reviewR).isNotNull();
  }

  @Test
  @DisplayName("must persist a review in the database after updating ")
  public void updateReviewTest(){
    //scenario 
    Review review = createReviewAdmin();

    //execution 
    Review reviewR = reviewRepository.save(review);
    reviewR.setDone(true);
    Review subjectsUpdate = reviewRepository.save(reviewR);

    //validation
    Assertions.assertThat(subjectsUpdate).isNotNull();
    Assertions.assertThat(subjectsUpdate.isDone()).isEqualTo(true);
  }
  
  @Test
  @DisplayName("must delete a review in the database")
  public void deleteReviewTest(){
    //scenario 
    Review review = createReviewAdmin();

    //execution 
    reviewRepository.save(review);
    reviewRepository.deleteById(review.getId());
    Optional<Review> rUserOptional = reviewRepository.findById(review.getId());

    //validation
    Assertions.assertThat(rUserOptional).isEmpty();
  }

  @Test
  @DisplayName("must return a review")
  public void findByidReviewTest(){
    //scenario 
    Review review = createReviewAdmin();

    //execution 
    reviewRepository.save(review);
    Optional<Review> reviewOptional = reviewRepository.findById(review.getId());
    
    //validation
    Assertions.assertThat(reviewOptional).isNotNull();
  
  }

  @Test
  @DisplayName("must return a review")
  public void findByAllReviewTest(){
    //scenario 
    Review review = createReviewAdmin();

    //execution 
    reviewRepository.save(review);
    List<Review> listReviews = reviewRepository.findAll();
    
    //validation
    Assertions.assertThat(listReviews).isNotNull();
    Assertions.assertThat(listReviews).isNotEmpty();
  }

    private Review createReviewAdmin(){
      return Review.builder()
                  .id(1l)
                  .dayOfReview(LocalDate.of(2023, 8, 30))
                  .done(false)
                  .build();
    } 
    
}