package com.revisoes.TCCrevisoes.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ContentDto {

  @NotEmpty(message = "Name of content cannot be empty")
  private String name;
  
  private String description;
    
}