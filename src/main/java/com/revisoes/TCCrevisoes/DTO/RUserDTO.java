package com.revisoes.TCCrevisoes.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RUserDTO {

  @NotEmpty
  @Size(min = 5, max= 70)
  private String password;
}