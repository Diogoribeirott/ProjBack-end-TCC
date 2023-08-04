package com.revisoes.TCCrevisoes.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectsDto {
  @NotEmpty(message = "Name cannot be empty")
    private String name;
    
    
}