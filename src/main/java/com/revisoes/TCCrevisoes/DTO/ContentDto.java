package com.revisoes.TCCrevisoes.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentDto {

  @NotEmpty(message = "Name of content cannot be empty")
  private String name;
  
  private String description;
    
}