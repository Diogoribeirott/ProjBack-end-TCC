package com.revisoes.TCCrevisoes.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

  private LocalDate dayOfReview;
  private Boolean done;
    
}