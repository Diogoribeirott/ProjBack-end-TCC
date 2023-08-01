package com.revisoes.TCCrevisoes.DTO;

import lombok.Data;
import jakarta.validation.constraints.NotEmpty;

@Data
public class SubjectsDto {
  @NotEmpty(message = "Name cannot be empty")
    private String name;
    
}