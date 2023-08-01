package com.revisoes.TCCrevisoes.DTO;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ReviewDto {

  private LocalDate dayOfReview;
  private Boolean done;
    
}