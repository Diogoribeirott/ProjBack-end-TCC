package com.revisoes.TCCrevisoes.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SubjectsDto {
  @NotEmpty(message = "Name cannot be empty")
    private String name;
    
}