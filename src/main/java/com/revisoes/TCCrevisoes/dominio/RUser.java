package com.revisoes.TCCrevisoes.dominio;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import java.time.LocalDate;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
public class RUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @NotEmpty(message = "Name cannot be empty")
  private String name;

  @Email
  @NotEmpty(message = "email cannot be empty")
  private String email;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate dateOfBirth;

  @OneToMany
  private List<Subjects> subjects ;

  @NotEmpty
  @Size(min = 5, max= 70)
  private String login;

  @NotEmpty
  @Size(min = 5, max= 70)
  private String password;

    
}