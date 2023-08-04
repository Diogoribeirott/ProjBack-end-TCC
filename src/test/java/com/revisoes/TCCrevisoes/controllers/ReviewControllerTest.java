package com.revisoes.TCCrevisoes.controllers;

import java.util.List;
import org.mockito.Mock;
import java.time.LocalDate;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.HttpStatus;
import com.revisoes.TCCrevisoes.DTO.ReviewDto;
import org.springframework.http.ResponseEntity;
import com.revisoes.TCCrevisoes.dominio.Review;
import org.junit.jupiter.api.extension.ExtendWith;
import com.revisoes.TCCrevisoes.service.ReviewService;
import com.revisoes.TCCrevisoes.controller.ReviewController;
import com.revisoes.TCCrevisoes.Exceptions.ObjectNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
public class ReviewControllerTest {

  @InjectMocks
  private ReviewController reviewController;

  @Mock
  private ReviewService reviewService;

  @Test
  @DisplayName("save new review when sucessfull")
  void saveNewReviewtWithSucessfulAndReturnStatusCreatedTest(){

    //scenario
    Review review = creatReview();
    BDDMockito.when(reviewService.saveReview(review)).thenReturn(review);

    //execution
     ResponseEntity<Review> responseEntityReview = reviewController.saveReview(review);

    //validation
    Assertions.assertThat(responseEntityReview.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    Assertions.assertThat(responseEntityReview).isNotNull();
    Assertions.assertThat(responseEntityReview.getBody()).isNotNull();
    Assertions.assertThat(responseEntityReview.getBody()).isEqualTo(review);
  }

  @Test
  @DisplayName("trying to save new empty content and return Error")
  void tryingToSaveNewEmptyReviewtAndReturnStatusCreatedWithErrorTest(){

    //scenario
    BDDMockito.when(reviewService.saveReview(any())).thenThrow(new IllegalArgumentException("error in object declaration"));

    //execution and validation
    Assertions.assertThatThrownBy(()-> reviewController.saveReview(new Review()))
                                .isInstanceOf(IllegalArgumentException.class)
                                .hasMessage("error in object declaration");
  }


  
    @Test
    @DisplayName("should return a list of Review")
    public void findAllReviewTest(){

      //scenario
      Review Review = creatReview();
      List<Review> listReview = List.of(Review);

      BDDMockito.when(reviewService.findAll()).thenReturn(listReview);

      //execution 
       ResponseEntity<List<Review>> responseEntityListReview = reviewController.findAll();

      //validation
      List<Review> listWithReview = responseEntityListReview.getBody();
      Assertions.assertThat(responseEntityListReview.getStatusCode()).isEqualTo(HttpStatus.OK);
      Assertions.assertThat(listWithReview.get(0)).isNotNull();
      Assertions.assertThat(listWithReview.get(0)).isEqualTo(Review);
    }

    @Test
    @DisplayName("should return a  Review")
    public void findByIdReviewTest(){

      //scenario
      Review Review = creatReview();
      BDDMockito.when(reviewService.findById(anyLong())).thenReturn(Review);

      //execution 
       ResponseEntity<Review> responseEntityReview = reviewController.findById(1l);

      //validation
      Review reviewR = responseEntityReview.getBody();
      Assertions.assertThat(responseEntityReview.getStatusCode()).isEqualTo(HttpStatus.OK);
      Assertions.assertThat(reviewR).isNotNull();
      Assertions.assertThat(reviewR).isEqualTo(Review);
    }

     @Test
    @DisplayName("should return ObjectNotFoundException when it doesn't exist Review")
    public void findByIdWhenReviewDoesNotExistTest(){

      //scenario
      BDDMockito.when(reviewService.findById(100L)).thenThrow(new ObjectNotFoundException("Object not found"));

      //execution
      ObjectNotFoundException assertThrows = assertThrows(ObjectNotFoundException.class, () -> reviewController.findById(100L));

      //validation
      Assertions.assertThat(assertThrows.getMessage()).isEqualTo("Object not found");
    }

    @Test
    @DisplayName("should delete a Review")
    public void deleteByIdReviewWhenSucessful(){

      //scenario
      BDDMockito.doNothing().when(reviewService).deleteReview(anyLong());

      //execution
      ResponseEntity<Void> responseEntityVoid = reviewController.deleteReview(1l);

      //validation
      Assertions.assertThat(responseEntityVoid).isNotNull();
      Assertions.assertThat(responseEntityVoid.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("trying to delete a review that doesn't exist and return Object not found exception")
    public void deleteByIdReviewWhenNofound(){

      //scenario
     when(reviewController.deleteReview(anyLong())).thenThrow(new ObjectNotFoundException("object not found"));

      //execution
      ObjectNotFoundException assertThrows = assertThrows(ObjectNotFoundException.class, () -> reviewController.deleteReview(1l));

      //validation
      Assertions.assertThat(assertThrows.getMessage()).isEqualTo("object not found");
    }

    @Test
    @DisplayName("must update review name")
    public void updateReviewWhenSucessfull(){
      
      //scenario
      Review review = creatReview();
      ReviewDto reviewDto = createReviewDto();
      BDDMockito.when(reviewService.updateReview(anyLong(),any(ReviewDto.class))).thenReturn(review);

      //execution
      ResponseEntity<Review> responseEntityReview = reviewController.updateReview(1l,reviewDto);

      //validation
      Review reviewR = responseEntityReview.getBody();
      Assertions.assertThat(responseEntityReview.getStatusCode()).isEqualTo(HttpStatus.OK);
      Assertions.assertThat(reviewR).isNotNull();
      Assertions.assertThat(reviewR.getDayOfReview()).isEqualTo(reviewDto.getDayOfReview());
    }

    @Test
    @DisplayName("trying to update a review that does not exist and returning Object not found exception")
    public void updateByIdReviewWhenNofound(){

      //scenario
     when(reviewService.updateReview(1L ,new ReviewDto() )).thenThrow(new ObjectNotFoundException("object not found"));

      //execution
      ObjectNotFoundException assertThrows = assertThrows(ObjectNotFoundException.class, () -> reviewController.updateReview(1l,new ReviewDto()));

      //validation
      Assertions.assertThat(assertThrows.getMessage()).isEqualTo("object not found");
    }

    public ReviewDto createReviewDto(){
      return ReviewDto.builder()
                      .dayOfReview(LocalDate.of(2023, 8, 10))
                      .done(false)
                      .build();
    }

    public Review creatReview(){
      return Review.builder()
                    .id(1l)
                    .dayOfReview(LocalDate.of(2023, 8, 10))
                    .done(false)
                    .build();
    }
}