package com.revisoes.TCCrevisoes.controllers;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.revisoes.TCCrevisoes.controller.ReviewController;
import com.revisoes.TCCrevisoes.dominio.Review;

import ch.qos.logback.core.status.Status;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ReviewController.class)
public class ReviewControlerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ReviewController reviewController;
  

  @Test
  public void findAllTest() throws Exception{
    Review review = Review.builder().dayOfReview(null).build();
    List<Review> listReview = List.of(review);
    BDDMockito.given(reviewController.findAll()).willReturn(ResponseEntity.ok(listReview));
    //Execucao
    MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/review/all").accept(MediaType.APPLICATION_JSON);


    //validation
    mockMvc.perform(request).andExpect();

  }



    
}