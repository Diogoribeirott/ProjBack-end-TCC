package com.revisoes.TCCrevisoes.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RUserDTO {

  @NotEmpty
  @Size(min = 5, max= 70)
  private String password;
}