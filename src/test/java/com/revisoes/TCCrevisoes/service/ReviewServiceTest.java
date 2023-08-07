package com.revisoes.TCCrevisoes.service;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

import org.mockito.Mock;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revisoes.TCCrevisoes.DTO.ReviewDto;
import com.revisoes.TCCrevisoes.dominio.Review;
import com.revisoes.TCCrevisoes.repository.ReviewRepository;
import com.revisoes.TCCrevisoes.Exceptions.ObjectNotFoundException;

@ExtendWith(SpringExtension.class)
public class ReviewServiceTest {
 @InjectMocks
  private ReviewService reviewService;
    
  @Mock
  private ReviewRepository reviewRepository;

  @Test
  @DisplayName("should return a Review and save")
  public void saveNewReviewTest(){

    //scenario
    Review review = createReview();
    System.out.println(review);
    when(reviewRepository.save(review)).thenReturn(review);

    //execution
    Review reviewR = reviewService.saveReview(review);

    //validation
    Assertions.assertThat(reviewR).isNotNull();
    Assertions.assertThat(reviewR.getId()).isNotNull();
    Assertions.assertThat(reviewR.getDayOfReview()).isNotNull();
  }

  @Test
  @DisplayName("sould return a review by id")
  public void findIdReviewTest(){
    
    //scenario
    Review review = createReview();
    BDDMockito.when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(review));

    //execution
    Review reviewR = reviewService.findById(1l);

    //validation
    Assertions.assertThat(reviewR).isNotNull();
    Assertions.assertThat(reviewR.getId()).isNotNull();
    Assertions.assertThat(reviewR.getDayOfReview()).isNotNull();
  }

  @Test
  @DisplayName("sould return a error when id invalid")
  public void findIdReviewAndReturnErrorTest(){
    
    //scenario
    BDDMockito.when(reviewRepository.findById(anyLong())).thenThrow(new ObjectNotFoundException("object not found"));

    //execution
    Assertions.assertThatThrownBy(() -> reviewService.findById(1L))
    .isInstanceOf(ObjectNotFoundException.class)
    .hasMessage("object not found");
  }

  @Test
  @DisplayName("sould return a list of Review")
  public void findAllReviewTest(){
    
    //scenario
    Review review = createReview();
    BDDMockito.when(reviewRepository.findAll()).thenReturn(List.of(review));

    //execution
     List<Review> listReview = reviewService.findAll();

    //validation
    Assertions.assertThat(listReview).isNotNull();
    Assertions.assertThat(listReview.get(0).getId()).isEqualTo(review.getId());
    Assertions.assertThat(listReview.get(0).getDayOfReview()).isEqualTo(review.getDayOfReview());
  }

  @Test
  @DisplayName("should update name and description - review")
  public void updateReviewTest() {
      // Scenario
      Review review = createReview();
      ReviewDto reviewDto = createReviewDto();

      when(reviewRepository.findById(1l)).thenReturn(Optional.of(review));
      when(reviewRepository.save(any(Review.class))).thenReturn(review);

      // Execution
      Review updateReview = reviewService.updateReview(1l, reviewDto);

      // Validation
      Assertions.assertThat(updateReview).isNotNull();
      Assertions.assertThat(updateReview.getId()).isEqualTo(review.getId());
      Assertions.assertThat(updateReview.getDayOfReview()).isEqualTo(review.getDayOfReview());
    }
  
  @Test
  @DisplayName("trying to update name review with id invalid and return error")
  public void updateReviewWithReviewDtoOrReviewIdInvalidTest() {
      // Scenario
      Review review = createReview();
      ReviewDto ReviewDTO = createReviewDto();

      when(reviewRepository.findById(anyLong())).thenThrow(new ObjectNotFoundException("Object invalid"));
      when(reviewRepository.save(any(Review.class))).thenReturn(review);

      // Execution and Validation
     Assertions.assertThatThrownBy(() -> reviewService.updateReview(1l, ReviewDTO))
      .isInstanceOf(ObjectNotFoundException.class)
      .hasMessage("Object invalid");
    }

 
  @Test
  @DisplayName("delete review when successful")
  public void deleteByIdWhenSuccessful() {
      // Scenario
      when(reviewRepository.findById(1l)).thenReturn(Optional.of(new Review()));
      BDDMockito.doNothing().when(reviewRepository).deleteById(1l);

      // Execution
      reviewService.deleteReview(1l);

      // Validation
      verify(reviewRepository).findById(1l);
      verify(reviewRepository).deleteById(1l);
    }

  @Test
  @DisplayName("trying to delete review when not exist")
  public void deleteByIdWhenUnsuccessful() {
      // Scenario
      when(reviewRepository.findById(1l)).thenThrow(new ObjectNotFoundException("Object not fould"));
      BDDMockito.doNothing().when(reviewRepository).deleteById(1l);

      // Execution and Validation
      Assertions.assertThatThrownBy(()->reviewService.deleteReview(1l))
                                      .isInstanceOf(ObjectNotFoundException.class)
                                      .hasMessage("Object not fould");
    }

  public ReviewDto createReviewDto(){
    return ReviewDto.builder()
                      .done(false)
                      .dayOfReview(LocalDate.of(2023, 8, 22))
                      .build();
  }

  public Review createReview(){
   return Review.builder()
            .id(1l)
            .done(false)
            .dayOfReview(LocalDate.of(2023, 8, 22))
            .build();
  }
    
  
}