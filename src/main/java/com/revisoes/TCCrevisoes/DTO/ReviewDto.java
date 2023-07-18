package com.revisoes.TCCrevisoes.DTO;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ReviewDto {

  private LocalDate dayOfReview;

  private Boolean done;
    
}